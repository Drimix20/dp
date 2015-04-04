package afm.analyzer.threshold;

import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface ImageThresholdStrategy {

    ImageProcessor makeBinary(ImageProcessor originalImage);
}
