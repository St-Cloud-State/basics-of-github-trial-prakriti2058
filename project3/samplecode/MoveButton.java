import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MoveButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private UndoManager undoManager;
    
    public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Move");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }
    
    public void actionPerformed(ActionEvent event) {
        System.out.println("=== MOVE BUTTON CLICKED ===");
        
        // Clear any existing mouse listeners to prevent conflicts
        view.clearAllMouseListeners();
        
        // Check if any items are selected - use a temporary MoveCommand to check
        MoveCommand tempCommand = new MoveCommand(0, 0);
        boolean hasSelected = tempCommand.hasSelectedItems();
        System.out.println("Has selected items: " + hasSelected);
        
        if (!hasSelected) {
            System.out.println("No items selected - Move button will not activate");
            JOptionPane.showMessageDialog(view, "Please select an item first using the Select button before moving.");
            return;
        }
        
        System.out.println("Move mode activated - cursor changed to move cursor");
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
    }
    
    private class MouseHandler extends MouseAdapter {
        private Point startPoint;
        private boolean dragging = false;
        
        public void mousePressed(MouseEvent event) {
            startPoint = View.mapPoint(event.getPoint());
            dragging = true;
        }
        
        public void mouseReleased(MouseEvent event) {
            if (dragging && startPoint != null) {
                Point endPoint = View.mapPoint(event.getPoint());
                
                // Calculate the translation delta
                int deltaX = (int)(endPoint.getX() - startPoint.getX());
                int deltaY = (int)(endPoint.getY() - startPoint.getY());
                
                // Only create move command if there was actual movement
                if (deltaX != 0 || deltaY != 0) {
                    MoveCommand moveCommand = new MoveCommand(deltaX, deltaY);
                    
                    // Only proceed if there are selected items to move
                    if (moveCommand.hasSelectedItems()) {
                        undoManager.beginCommand(moveCommand);
                        undoManager.endCommand(moveCommand);
                    }
                }
                
                // Reset state and cursor
                dragging = false;
                startPoint = null;
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        
        // Handle case where mouse is dragged outside and released
        public void mouseExited(MouseEvent event) {
            if (dragging) {
                // Treat as mouse released
                mouseReleased(event);
            }
        }
    }
}