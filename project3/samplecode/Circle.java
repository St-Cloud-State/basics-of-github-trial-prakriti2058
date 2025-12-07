import java.awt.*;

public class Circle extends Item {
    private Point center;
    private int radius;
    
    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }
    
    public Circle(Point point1, Point point2) {
        // Calculate center as midpoint of diameter
        int centerX = (int)((point1.getX() + point2.getX()) / 2);
        int centerY = (int)((point1.getY() + point2.getY()) / 2);
        this.center = new Point(centerX, centerY);
        
        // Calculate radius as half the distance between the two points
        double diameter = distance(point1, point2);
        this.radius = (int)(diameter / 2);
    }
    
    public boolean includes(Point point) {
        return distance(point, center) <= radius + 10; // 10 pixel tolerance for selection
    }
    
    public void render(UIContext uiContext) {
        uiContext.drawCircle(center, radius);
    }
    
    public void translate(int deltaX, int deltaY) {
        center = new Point((int)(center.getX() + deltaX), (int)(center.getY() + deltaY));
    }
    
    public Point getCenter() {
        return center;
    }
    
    public int getRadius() {
        return radius;
    }
    
    public void setCenter(Point center) {
        this.center = center;
    }
    
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public String toString() {
        return "Circle at " + center + " with radius " + radius;
    }
}