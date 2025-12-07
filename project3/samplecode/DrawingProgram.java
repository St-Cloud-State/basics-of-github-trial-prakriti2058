import javax.swing.*;
public class DrawingProgram {
  public static void main(String[] args){
    System.out.println("Starting Drawing Program...");
    JFrame.setDefaultLookAndFeelDecorated(true);
    Model model = new Model();
    UndoManager undoManager = new UndoManager();
    UndoManager.setModel(model);
    View.setUndoManager(undoManager);
    View.setModel(model);
    View view = new View();
    Model.setView(view);
    Command.setUndoManager(undoManager);
    Command.setModel(model);
    System.out.println("Showing view...");
    view.setVisible(true);
    view.toFront();
    view.requestFocus();
    view.setAlwaysOnTop(true);
    System.out.println("Drawing Program window should now be visible and on top!");
  }
}