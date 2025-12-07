import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class View extends JFrame {
  private UIContext uiContext;
  private JPanel drawingPanel;
  private JPanel buttonPanel;
  private JPanel filePanel;
  private JButton lineButton;
  private JButton deleteButton;
  private JButton labelButton;
  private JButton selectButton;
  private JButton circleButton;
  private JButton polygonButton;
  private JButton moveButton;
  private JButton saveButton;
  private JButton openButton;
  private JButton undoButton;
  private JButton redoButton;
  private static UndoManager undoManager;
    private String fileName;
  // other buttons to be added as needed;
  private static Model model;
  public UIContext getUI() {
    return uiContext;
  }
  private void setUI(UIContext uiContext) {
    this.uiContext = uiContext;
  }
  public static void setModel(Model model) {
    View.model = model;
  }
  public static void setUndoManager(UndoManager undoManager) {
    View.undoManager = undoManager;
  }
  private class DrawingPanel extends JPanel {
    private MouseListener currentMouseListener;
    private KeyListener currentKeyListener;
    private FocusListener currentFocusListener;
    public DrawingPanel() {
      setLayout(null);
      setBackground(Color.WHITE); // Set white background for better visibility
      setFocusable(true); // Make panel focusable
      requestFocusInWindow(); // Request focus
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      (NewSwingUI.getInstance()).setGraphics(g);
      
      // Draw all regular items (lines, labels, circles, polygons) in BLACK
      g.setColor(Color.BLACK);
      Enumeration enumeration = model.getItems();
      while (enumeration.hasMoreElements()) {
        Item item = (Item) enumeration.nextElement();
        (NewSwingUI.getInstance()).setGraphics(g); // Reset graphics for each item
        item.render(uiContext);
      }
      
      // Render circle preview if available in MAGENTA
      if (circleButton != null) {
        Circle previewCircle = ((CircleButton)circleButton).getPreviewCircle();
        if (previewCircle != null) {
          g.setColor(Color.MAGENTA);
          (NewSwingUI.getInstance()).setGraphics(g);
          previewCircle.render(uiContext);
        }
      }
      
      // Draw selected items in RED
      g.setColor(Color.RED);
      enumeration = model.getSelectedItems();
      while (enumeration.hasMoreElements()) {
        Item item = (Item) enumeration.nextElement();
        (NewSwingUI.getInstance()).setGraphics(g);
        item.render(uiContext);
      }
    }
    public void addMouseListener(MouseListener newListener) {
      if (currentMouseListener != null) {
        super.removeMouseListener(currentMouseListener);
      }
      currentMouseListener = newListener;
      super.addMouseListener(newListener);
      System.out.println("Mouse listener added to drawing panel: " + newListener.getClass().getSimpleName());
    }
    public void addKeyListener(KeyListener newListener) {
      removeKeyListener(currentKeyListener);
      currentKeyListener =  newListener;
      super.addKeyListener(newListener);
    }
    public void addFocusListener(FocusListener newListener) {
      removeFocusListener(currentFocusListener);
      currentFocusListener =  newListener;
      super.addFocusListener(newListener);
    }
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
    setTitle("Drawing Program 1.1  " + fileName);
  }
  public String getFileName() {
    return fileName;
  }

  public View() {
    super("Drawing Program 1.1  Untitled");
    fileName = null;
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });
    this.setUI(NewSwingUI.getInstance());
    drawingPanel = new DrawingPanel();
    drawingPanel.setPreferredSize(new Dimension(580, 350)); // Set explicit size
    buttonPanel = new JPanel();
    Container contentpane = getContentPane();
    contentpane.add(buttonPanel, "North");
    contentpane.add(drawingPanel, "Center"); // Explicitly set center
    lineButton= new LineButton(undoManager, this, drawingPanel);
    labelButton = new LabelButton(undoManager, this, drawingPanel);
    selectButton= new SelectButton(undoManager, this, drawingPanel);
    circleButton = new CircleButton(undoManager, this, drawingPanel);
    polygonButton = new PolygonButton(undoManager, this, drawingPanel);
    moveButton = new MoveButton(undoManager, this, drawingPanel);
    System.out.println("All new buttons created successfully!");
    deleteButton= new DeleteButton(undoManager);
    saveButton= new SaveButton(undoManager, this);
    openButton= new OpenButton(undoManager, this);
    undoButton = new UndoButton(undoManager);
    redoButton = new RedoButton(undoManager);
    buttonPanel.add(lineButton);
    buttonPanel.add(labelButton);
    buttonPanel.add(selectButton);
    buttonPanel.add(circleButton);
    buttonPanel.add(polygonButton);
    buttonPanel.add(moveButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(openButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(redoButton);
    this.setSize(600, 400);
  }
  public void refresh() {
    // code to access the Model update the contents of the drawing panel.
    drawingPanel.repaint();
  }
  public static Point mapPoint(Point point){
    // maps a point on the drawing panel to a point
    // on the figure being created. Perhaps this
    // should be in drawing panel
    return point;
  }
  
  // Method to clear all mouse listeners to prevent conflicts
  public void clearAllMouseListeners() {
    MouseListener[] listeners = drawingPanel.getMouseListeners();
    for (MouseListener listener : listeners) {
      drawingPanel.removeMouseListener(listener);
    }
    MouseMotionListener[] motionListeners = drawingPanel.getMouseMotionListeners();
    for (MouseMotionListener listener : motionListeners) {
      drawingPanel.removeMouseMotionListener(listener);
    }
  }
}
