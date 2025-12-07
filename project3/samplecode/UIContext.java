import java.awt.*;
public interface UIContext {
  public abstract void drawCircle(Point center, int radius);
  public abstract void drawLine(Point point1, Point point2 );
  public abstract void drawLabel(String s, Point p);
}
