
import ij.gui.PolygonRoi;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

/**
 *
 * @author Drimal
 */
public class ExtendedRoi extends PolygonRoi {

    public ExtendedRoi(int[] xPoints, int[] yPoints, int nPoints, int type) {
        super(xPoints, yPoints, nPoints, type);
    }

    public ExtendedRoi(Polygon p, int type) {
        super(p, type);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        System.out.print(super.toString());
    }

    @Override
    protected void handleMouseDown(int sx, int sy) {
        super.handleMouseDown(sx, sy);
        System.out.println("Selected roi");
    }

}
