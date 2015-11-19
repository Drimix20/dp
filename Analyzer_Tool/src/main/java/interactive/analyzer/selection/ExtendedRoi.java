package interactive.analyzer.selection;


import ij.gui.PolygonRoi;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

/**
 *
 * @author Drimal
 */
public class ExtendedRoi extends PolygonRoi {

    private int label;

    public ExtendedRoi(int[] xPoints, int[] yPoints, int nPoints, int type) {
        super(xPoints, yPoints, nPoints, type);
    }

    public ExtendedRoi(Polygon p, int type) {
        super(p, type);
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        System.out.print(super.toString());
    }

    @Override
    protected void handleMouseDown(int sx, int sy) {
        super.handleMouseDown(sx, sy);
        System.out.println("Selected roi with label=" + label);
    }

}
