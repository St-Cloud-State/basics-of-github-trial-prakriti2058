import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CircleButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private CircleCommand circleCommand;
    private UndoManager undoManager;
    private Circle previewCircle;
    
    public CircleButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Circle");
        System.out.println("CircleButton created!");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }
    
    public void actionPerformed(ActionEvent event) {
        System.out.println("Circle button clicked! Setting up circle creation mode.");
        drawingPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        System.out.println("Mouse listeners added. Cursor should be crosshair now.");
    }
    
    private class MouseHandler extends MouseAdapter {
        private int clickCount = 0;
        
        public void mouseMoved(MouseEvent event) {
            if (circleCommand != null && circleCommand.getPointCount() == 1) {
                // Create preview circle for more user-friendly experience (+15 points)
                Point currentPoint = View.mapPoint(event.getPoint());
                previewCircle = circleCommand.createPreviewCircle(currentPoint);
                drawingPanel.repaint(); // Force repaint to show preview
            }
        }
        
        public void mouseClicked(MouseEvent event) {
            clickCount++;
            System.out.println("=== CIRCLE MOUSE CLICK #" + clickCount + " DETECTED ===");
            System.out.println("Click location: " + event.getPoint());
            System.out.println("Mapped point: " + View.mapPoint(event.getPoint()));
            
            if (clickCount == 1) {
                // First click - start circle creation
                circleCommand = new CircleCommand();
                circleCommand.setPoint1(View.mapPoint(event.getPoint()));
                // DON'T call beginCommand yet - wait until we have both points
                System.out.println("Circle creation started at point: " + circleCommand.getPoint1());
            } else if (clickCount == 2) {
                // Second click - complete circle creation
                clickCount = 0;
                circleCommand.setPoint2(View.mapPoint(event.getPoint()));
                System.out.println("Circle completed with point2: " + circleCommand.getPoint2());
                System.out.println("Created circle: " + circleCommand.getCircle());
                
                // Clear preview circle
                previewCircle = null;
                
                // Now that we have both points and a complete circle, add it
                if (circleCommand.getCircle() != null && circleCommand.getCircle().getRadius() > 0) {
                    System.out.println("Adding final circle to model");
                    // Begin and end the command now that we have a complete circle
                    undoManager.beginCommand(circleCommand);
                    undoManager.endCommand(circleCommand);
                }
                
                drawingPanel.removeMouseListener(this);
                drawingPanel.removeMouseMotionListener(this);
                drawingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                
                // Force multiple repaints to ensure the circle shows up
                drawingPanel.repaint();
                drawingPanel.revalidate();
                drawingPanel.repaint();
                System.out.println("Circle creation completed with repaints");
            }
        }
    }

    
    // Method to get preview circle for rendering
    public Circle getPreviewCircle() {
        return previewCircle;
    }
}