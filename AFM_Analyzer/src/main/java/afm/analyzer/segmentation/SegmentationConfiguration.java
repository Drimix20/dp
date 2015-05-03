package afm.analyzer.segmentation;

/**
 *
 * @author Drimal
 */
public class SegmentationConfiguration {

    public static final double DEFAULT_MIN_SIZE = 0.0;
    public static final double DEFAULT_MAX_SIZE = Double.POSITIVE_INFINITY;
    public static final double DEFAULT_MIN_CIRCULARITY = 0.0;
    public static final double DEFAULT_MAX_CIRCULARITY = 1.0;
    private static int thresholdValue;
    private static boolean excludeOnEdges;
    private static double minPixelSize = DEFAULT_MIN_SIZE;
    private static double maxPixelSize = DEFAULT_MAX_SIZE;
    private static double minCircularity = DEFAULT_MIN_CIRCULARITY;
    private static double maxCircularity = DEFAULT_MAX_CIRCULARITY;

    public static int getThresholdValue() {
        return thresholdValue;
    }

    public static void setThresholdValue(int thresholdValue) {
        SegmentationConfiguration.thresholdValue = thresholdValue;
    }

    public static boolean isExcludeOnEdges() {
        return excludeOnEdges;
    }

    public static void setExcludeOnEdges(boolean excludeOnEdges) {
        SegmentationConfiguration.excludeOnEdges = excludeOnEdges;
    }

    public static double getMinPixelSize() {
        return minPixelSize;
    }

    public static void setMinPixelSize(double minPixelSize) {
        SegmentationConfiguration.minPixelSize = minPixelSize;
    }

    public static double getMaxPixelSize() {
        return maxPixelSize;
    }

    public static void setMaxPixelSize(double maxPixelSize) {
        SegmentationConfiguration.maxPixelSize = maxPixelSize;
    }

    public static double getMinCircularity() {
        return minCircularity;
    }

    public static void setMinCircularity(double minCircularity) {
        SegmentationConfiguration.minCircularity = minCircularity;
    }

    public static double getMaxCircularity() {
        return maxCircularity;
    }

    public static void setMaxCircularity(double maxcircularity) {
        SegmentationConfiguration.maxCircularity = maxcircularity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + SegmentationConfiguration.thresholdValue;
        hash = 53 * hash + (SegmentationConfiguration.excludeOnEdges ? 1 : 0);
        hash = 53 * hash + (int) SegmentationConfiguration.minPixelSize;
        hash = 53 * hash + (int) SegmentationConfiguration.maxPixelSize;
        hash = 53 * hash + (int) SegmentationConfiguration.minCircularity;
        hash = 53 * hash + (int) SegmentationConfiguration.maxCircularity;
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
        final SegmentationConfiguration other = (SegmentationConfiguration) obj;
        if (SegmentationConfiguration.thresholdValue != other.thresholdValue) {
            return false;
        }
        if (SegmentationConfiguration.excludeOnEdges != other.excludeOnEdges) {
            return false;
        }
        if (SegmentationConfiguration.minPixelSize != other.minPixelSize) {
            return false;
        }
        if (SegmentationConfiguration.maxPixelSize != other.maxPixelSize) {
            return false;
        }
        if (SegmentationConfiguration.minCircularity != other.minCircularity) {
            return false;
        }
        if (SegmentationConfiguration.maxCircularity != other.maxCircularity) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SegmentationOptions[threshold=" + thresholdValue + ", excludeOnEdges=" + excludeOnEdges
                + ", minPixelSize=" + minPixelSize + ", maxPixelSize=" + maxPixelSize + ", minCircularity=" + minCircularity
                + ", maxCircularity=" + maxCircularity + "]";
    }
}
