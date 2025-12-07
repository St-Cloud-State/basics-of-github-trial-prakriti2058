import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleTest extends JFrame {
    private JPanel drawingPanel;
    
    public SimpleTest() {
        setTitle("Simple Mouse Test");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        drawingPanel = new JPanel();
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setPreferredSize(new Dimension(380, 250));
        
        drawingPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("CLICK DETECTED at: " + e.getPoint());
                Graphics g = drawingPanel.getGraphics();
                g.setColor(Color.RED);
                g.fillOval(e.getX()-5, e.getY()-5, 10, 10);
            }
        });
        
        add(drawingPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new SimpleTest();
    }
}