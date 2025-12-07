import java.util.*;

@SuppressWarnings("unchecked")
public class MoveCommand extends Command {
    private Vector selectedItems;
    private int deltaX;
    private int deltaY;
    
    public MoveCommand(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        selectedItems = new Vector();
        
        // Store the selected items at the time of command creation
        Enumeration enumeration = model.getSelectedItems();
        while (enumeration.hasMoreElements()) {
            Item item = (Item)(enumeration.nextElement());
            selectedItems.add(item);
        }
    }
    
    public void execute() {
        // Translate all selected items by the delta amounts
        Enumeration enumeration = selectedItems.elements();
        while (enumeration.hasMoreElements()) {
            Item item = (Item)(enumeration.nextElement());
            item.translate(deltaX, deltaY);
        }
        model.setChanged();
    }
    
    public boolean undo() {
        // Translate all items back by negative delta amounts
        Enumeration enumeration = selectedItems.elements();
        while (enumeration.hasMoreElements()) {
            Item item = (Item)(enumeration.nextElement());
            item.translate(-deltaX, -deltaY);
        }
        model.setChanged();
        return true;
    }
    
    public boolean redo() {
        execute();
        return true;
    }
    
    public boolean hasSelectedItems() {
        return selectedItems.size() > 0;
    }
}