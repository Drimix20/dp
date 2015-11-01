package afm.analyzer.scalings.types;

/**
 *
 * @author Drimal
 */
public abstract class ImageDataScaler {

    protected double multiplier;
    protected double offset;

    public double getMultiplier() {
        return multiplier;
    }

    public double getOffset() {
        return offset;
    }

    public double scaleValue(long pixelValue) {
        return offset + multiplier * pixelValue;
    }

    public double scaleValue(double pixelValue) {
        return offset + multiplier * pixelValue;
    }

    @Override
    public String toString() {
        return "ImageDataScaler{" + "multiplier=" + multiplier + ", offset=" + offset + '}';
    }

}
