import java.awt.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class Polygon extends Item {
    private Vector points;
    private boolean isComplete;
    
    public Polygon() {
        points = new Vector();
        isComplete = false;
    }
    
    public Polygon(Vector points) {
        this.points = new Vector(points);
        isComplete = true;
    }
    
    public void addPoint(Point point) {
        if (!isComplete) {
            points.add(point);
        }
    }
    
    public void complete() {
        isComplete = true;
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public Vector getPoints() {
        return points;
    }
    
    public int getPointCount() {
        return points.size();
    }
    
    public boolean includes(Point point) {
        // Check if point is close to any vertex
        for (int i = 0; i < points.size(); i++) {
            Point vertex = (Point) points.elementAt(i);
            if (distance(point, vertex) < 10.0) {
                return true;
            }
        }
        
        // Check if point is close to any edge
        for (int i = 0; i < points.size(); i++) {
            Point p1 = (Point) points.elementAt(i);
            Point p2 = (Point) points.elementAt((i + 1) % points.size());
            
            // Simple line distance check (could be improved for better accuracy)
            if (distanceToLineSegment(point, p1, p2) < 10.0) {
                return true;
            }
        }
        
        return false;
    }
    
    private double distanceToLineSegment(Point p, Point lineStart, Point lineEnd) {
        // Calculate distance from point p to line segment from lineStart to lineEnd
        double A = p.getX() - lineStart.getX();
        double B = p.getY() - lineStart.getY();
        double C = lineEnd.getX() - lineStart.getX();
        double D = lineEnd.getY() - lineStart.getY();
        
        double dot = A * C + B * D;
        double lenSq = C * C + D * D;
        
        if (lenSq == 0) {
            return distance(p, lineStart);
        }
        
        double param = dot / lenSq;
        
        double xx, yy;
        
        if (param < 0) {
            xx = lineStart.getX();
            yy = lineStart.getY();
        } else if (param > 1) {
            xx = lineEnd.getX();
            yy = lineEnd.getY();
        } else {
            xx = lineStart.getX() + param * C;
            yy = lineStart.getY() + param * D;
        }
        
        double dx = p.getX() - xx;
        double dy = p.getY() - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public void render(UIContext uiContext) {
        if (points.size() < 2) {
            // If only one point, draw a small circle to show the point
            if (points.size() == 1) {
                Point p = (Point) points.elementAt(0);
                // Draw a small circle around the point
                uiContext.drawCircle(p, 2);
            }
            return;
        }
        
        // Draw lines between consecutive points
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = (Point) points.elementAt(i);
            Point p2 = (Point) points.elementAt(i + 1);
            uiContext.drawLine(p1, p2);
        }
        
        // If polygon is complete, draw closing line
        if (isComplete && points.size() > 2) {
            Point first = (Point) points.elementAt(0);
            Point last = (Point) points.elementAt(points.size() - 1);
            uiContext.drawLine(last, first);
        }
    }
    
    public void translate(int deltaX, int deltaY) {
        for (int i = 0; i < points.size(); i++) {
            Point oldPoint = (Point) points.elementAt(i);
            Point newPoint = new Point((int)(oldPoint.getX() + deltaX), (int)(oldPoint.getY() + deltaY));
            points.setElementAt(newPoint, i);
        }
    }
    
    public String toString() {
        return "Polygon with " + points.size() + " points" + (isComplete ? " (complete)" : " (incomplete)");
    }
}