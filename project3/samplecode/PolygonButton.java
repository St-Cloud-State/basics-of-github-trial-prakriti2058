import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PolygonButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private PolygonCommand polygonCommand;
    private UndoManager undoManager;
    
    public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }
    
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        polygonCommand = new PolygonCommand();
        drawingPanel.addMouseListener(mouseHandler);
        undoManager.beginCommand(polygonCommand);
    }
    
    private class MouseHandler extends MouseAdapter {
        
        public void mouseClicked(MouseEvent event) {
            Point clickPoint = View.mapPoint(event.getPoint());
            
            // Left mouse button - add point
            if (SwingUtilities.isLeftMouseButton(event)) {
                // Add point first
                polygonCommand.addPoint(clickPoint);
                
                // Execute command to add to model after first point for rendering
                if (polygonCommand.getPointCount() == 1) {
                    polygonCommand.execute();
                }
                
                view.refresh();
            }
            // Right mouse button - complete polygon
            else if (SwingUtilities.isRightMouseButton(event)) {
                if (polygonCommand.getPointCount() >= 3) {
                    // Complete the polygon by connecting last point to first point
                    polygonCommand.completePolygon();
                    
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    undoManager.endCommand(polygonCommand);
                } else {
                    // Not enough points, cancel the command
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    undoManager.cancelCommand();
                }
            }
        }
    }
}