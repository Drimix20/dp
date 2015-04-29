package afm.analyzer.threshold;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface ImageThresholdStrategy {

    double getUpperThreshold();

    double getLowerThreshold();

    ImageProcessor makeBinary(ImagePlus originalImage);
}
