package scaler.module.types;

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
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.multiplier) ^ (Double.doubleToLongBits(this.multiplier) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.offset) ^ (Double.doubleToLongBits(this.offset) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageDataScaler other = (ImageDataScaler) obj;
        if (Double.doubleToLongBits(this.multiplier) != Double.doubleToLongBits(other.multiplier)) {
            return false;
        }
        if (Double.doubleToLongBits(this.offset) != Double.doubleToLongBits(other.offset)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImageDataScaler{" + "multiplier=" + multiplier + ", offset=" + offset + '}';
    }

}
