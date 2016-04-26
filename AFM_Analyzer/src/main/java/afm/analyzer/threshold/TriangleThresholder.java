package afm.analyzer.threshold;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.process.ImageProcessor;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class TriangleThresholder implements ImageThresholdStrategy {

    private Logger logger = Logger.getLogger(TriangleThresholder.class);
    private double upperThreshold;
    private double lowerThreshold;

    @Override
    public ImageProcessor makeBinary(ImagePlus originalImg) {
        IJ.setAutoThreshold(originalImg, "Triangle");
        Prefs.blackBackground = false;

        lowerThreshold = originalImg.getProcessor().getMinThreshold();
        upperThreshold = originalImg.getProcessor().getMaxThreshold();
        IJ.run(originalImg, "Convert to Mask", "");

        logger.info("lower=" + lowerThreshold + ", upper=" + upperThreshold);
        return originalImg.getProcessor();
    }

    @Override
    public double getUpperThreshold() {
        return upperThreshold;
    }

    @Override
    public double getLowerThreshold() {
        return lowerThreshold;
    }

}
