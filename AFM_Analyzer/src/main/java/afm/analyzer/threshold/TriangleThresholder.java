package afm.analyzer.threshold;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public class TriangleThresholder implements ImageThresholdStrategy {

    public ImageProcessor makeBinary(ImagePlus originalImg) {
        IJ.setAutoThreshold(originalImg, "Triangle");
        Prefs.blackBackground = false;
        IJ.run(originalImg, "Convert to Mask", "");
        return originalImg.getProcessor();
    }

}
