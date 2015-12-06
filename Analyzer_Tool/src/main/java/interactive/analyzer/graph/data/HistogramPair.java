package interactive.analyzer.graph.data;

/**
 *
 * @author Drimal
 */
public class HistogramPair {

    private int ID;
    private double value;
    private int occurence;

    public HistogramPair(int id, double value, int occurence) {
        this.value = value;
        this.occurence = occurence;
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public double getValue() {
        return value;
    }

    public int getOccurence() {
        return occurence;
    }

    @Override
    public String toString() {
        return "Pair[ id=" + ID + ", value=" + value
                + ", occurence=" + occurence + ']';
    }

}
