package interactive.analyzer.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class Bar extends Shape {

    Logger logger = Logger.getLogger(Bar.class);
    private Rectangle2D rectangle;
    private Graphics2D graphics;

    public Bar(int ID, double value, int count) {
        super(ID, value, count);
    }

    @Override
    public void setLocationAndSize(int x, int y, int width, int height) {
        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void setLocationAndSize(double x, double y, double width,
            double height) {
        rectangle = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g) {
        if (rectangle == null) {
            return;
        }

        graphics = g;
        if (isSelected()) {
            draw(g, super.getSelectionColor(), super.getBorderColor());
        } else {
            draw(g, super.getBgColor(), super.getBorderColor());
        }
    }

    @Override
    public void draw(Graphics2D g, Color bg, Color border) {
        if (rectangle == null) {
            return;
        }

        graphics = g;
        g.setColor(bg);
        g.fill(rectangle);
        g.setColor(border);
        g.draw(rectangle);
    }

    @Override
    public boolean contains(double x, double y) {
        if (rectangle == null) {
            return false;
        }
        return rectangle.contains(x, y);
    }

}
