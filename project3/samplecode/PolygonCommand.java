import java.awt.*;

public class PolygonCommand extends Command {
    private Polygon polygon;
    
    public PolygonCommand() {
        polygon = new Polygon();
    }
    
    public void addPoint(Point point) {
        polygon.addPoint(point);
        // Update the model immediately to show the current state
        model.setChanged();
    }
    
    public void completePolygon() {
        polygon.complete();
    }
    
    public Polygon getPolygon() {
        return polygon;
    }
    
    public int getPointCount() {
        return polygon.getPointCount();
    }
    
    public void execute() {
        // Add polygon to model for rendering during construction
        model.addItem(polygon);
    }
    
    public boolean undo() {
        if (polygon != null) {
            model.removeItem(polygon);
            return true;
        }
        return false;
    }
    
    public boolean redo() {
        execute();
        return true;
    }
    
    public boolean end() {
        if (polygon.getPointCount() < 3) {
            // Remove incomplete polygon from model if it was added during construction
            return false;
        }
        polygon.complete();
        return true;
    }
}