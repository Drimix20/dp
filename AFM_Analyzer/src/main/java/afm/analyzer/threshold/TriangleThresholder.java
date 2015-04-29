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

    public ImageProcessor makeBinary(ImagePlus originalImg) {
        IJ.setAutoThreshold(originalImg, "Triangle");
        Prefs.blackBackground = false;
        //IJ.run(originalImg, "Convert to Mask", "");

        lowerThreshold = originalImg.getProcessor().getMinThreshold();
        upperThreshold = originalImg.getProcessor().getMaxThreshold();
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
