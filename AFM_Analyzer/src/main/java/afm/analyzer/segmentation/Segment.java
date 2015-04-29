package afm.analyzer.segmentation;

/**
 *
 * @author Drimal
 */
public class Segment {

    private int label;
    private int bx;
    private int by;
    private int width;
    private int height;

    public Segment(int label, int bx, int by, int width, int height) {
        this.label = label;
        this.bx = bx;
        this.by = by;
        this.width = width;
        this.height = height;
    }

    public int getLabel() {
        return label;
    }

    public int getBx() {
        return bx;
    }

    public int getBy() {
        return by;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Segment{" + "label=" + label + ", bx=" + bx + ", by=" + by + ", width=" + width + ", height=" + height + '}';
    }

}
