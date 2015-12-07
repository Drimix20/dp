package interactive.analyzer.graph.data;

/**
 *
 * @author Drimal
 */
public class HistogramBin {

    private int ID;
    private double lowerBound;
    private double upperBound;
    private int occurence;

    public HistogramBin(int id, double lowerBound, double upperBound,
            int occurence) {
        this.ID = id;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.occurence = occurence;
    }

    public int getID() {
        return ID;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public int getOccurence() {
        return occurence;
    }

    public boolean isValueInRange(double value) {
        return lowerBound <= value && value < upperBound;
    }

    @Override
    public String toString() {
        return "Pair[ id=" + ID + ", lowBound=" + lowerBound + ", upperBound="
                + upperBound + ", occurence=" + occurence + ']';
    }

}
