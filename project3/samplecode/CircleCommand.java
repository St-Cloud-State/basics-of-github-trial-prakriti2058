import java.awt.*;

public class CircleCommand extends Command {
    private Circle circle;
    private Point point1;
    private Point point2;
    private int pointCount;
    
    public CircleCommand() {
        pointCount = 0;
        point1 = null;
        point2 = null;
        circle = null;
    }
    
    public CircleCommand(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.circle = new Circle(point1, point2);
        pointCount = 2;
    }
    
    public void setPoint1(Point point) {
        this.point1 = point;
        pointCount = 1;
    }
    
    public void setPoint2(Point point) {
        this.point2 = point;
        pointCount = 2;
        // Create the circle when we have both points
        if (point1 != null && point2 != null) {
            circle = new Circle(point1, point2);
        }
    }
    
    public Point getPoint1() {
        return point1;
    }
    
    public Point getPoint2() {
        return point2;
    }
    
    public Circle getCircle() {
        return circle;
    }
    
    public int getPointCount() {
        return pointCount;
    }
    
    // For preview - creates a temporary circle for rendering
    public Circle createPreviewCircle(Point currentPoint) {
        if (point1 != null && currentPoint != null) {
            return new Circle(point1, currentPoint);
        }
        return null;
    }
    
    public void execute() {
        if (circle != null) {
            System.out.println("EXECUTE: Adding circle to model: " + circle);
            model.addItem(circle);
            System.out.println("EXECUTE: Circle successfully added. Model now has " + model.getItems().toString() + " items");
        } else {
            System.out.println("EXECUTE: Cannot add null circle to model!");
        }
    }
    
    public boolean undo() {
        if (circle != null) {
            model.removeItem(circle);
            return true;
        }
        return false;
    }
    
    public boolean redo() {
        execute();
        return true;
    }
    
    public boolean end() {
        if (point1 == null || point2 == null) {
            return false; // Not enough points to create a circle
        }
        return true;
    }
}