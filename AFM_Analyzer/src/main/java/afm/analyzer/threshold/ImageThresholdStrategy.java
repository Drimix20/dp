package afm.analyzer.threshold;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface ImageThresholdStrategy {

    //TODO in normal threshold show size of thresholded molekul in nm

    double getUpperThreshold();

    double getLowerThreshold();

    ImageProcessor makeBinary(ImagePlus originalImage);
}
