package afm.analyzer.segmentation;

/**
 *
 * @author Drimal
 */
public class SegmentationConfiguration {

    private static int thresholdValue;
    private static boolean excludeOnEdges;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + SegmentationConfiguration.thresholdValue;
        hash = 53 * hash + (SegmentationConfiguration.excludeOnEdges ? 1 : 0);
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
        return true;
    }

    @Override
    public String toString() {
        return "SegmentationOptions[threshold=" + thresholdValue + ", excludeOnEdges=" + excludeOnEdges + "]";
    }
}
