package interactive.analyzer.selection;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author Drimal
 */
public class CircleIcon implements Icon {

    private Color color;
    private int radius;
    private static final int DEFAULT_RADIUS = 10;

    public CircleIcon(Color color) {
        this(color, DEFAULT_RADIUS);
    }

    public CircleIcon(Color color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }

    @Override
    public int getIconWidth() {
        return radius;
    }

    @Override
    public int getIconHeight() {
        return radius;
    }

}
