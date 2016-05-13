package interactive.analyzer.graph;

import interactive.analyzer.graph.shape.Shape;
import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public class ShapeComparator implements Comparator<Shape> {

    @Override
    public int compare(Shape o1, Shape o2) {
        return new Integer(o1.getID()).compareTo(o2.getID());
    }

}
