package afm.analyzer.threshold;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface ImageThresholdStrategy {

    ImageProcessor makeBinary(ImagePlus originalImage);
}
