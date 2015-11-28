package interactive.analyzer.graph.data;

/**
 *
 * @author Drimal
 */
public class Pair {

    private int ID;
    private int count;
    private double value;

    public Pair(int count, double value, int id) {
        this.count = count;
        this.value = value;
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public int getCount() {
        return count;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pair[" + "count=" + count + ", value=" + value + ']';
    }

}
