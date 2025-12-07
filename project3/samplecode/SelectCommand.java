import java.awt.*;
import java.util.*;
public class SelectCommand extends Command {
  private Item item;
  public SelectCommand() {
  }
  public boolean setPoint(Point point) {
	boolean found = false;
    Enumeration enumeration = model.getItems();
    while (enumeration.hasMoreElements()) {
      item = (Item)(enumeration.nextElement());
      if (item.includes(point)) {
        model.markSelected(item);
		found = true;
        break;
      }
    }
    
    // If no item was found at the click point, deselect all items
    if (!found) {
        model.clearSelection();
        return true; // Return true to indicate successful deselection
    }
    
	return found;
  }
  public boolean undo() {
    model.unSelect(item);
    return true;
  }
  public boolean  redo() {
    execute();
    return true;
  }
  public void execute() {
    model.markSelected(item);
  }
}
