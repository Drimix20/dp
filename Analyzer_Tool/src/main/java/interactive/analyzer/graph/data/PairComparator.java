package interactive.analyzer.graph.data;

import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public class PairComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        return Double.compare(o1.getValue(), o2.getValue());
    }

}
