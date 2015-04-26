package afm.analyzer.measurements;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface MeasurementComputationAbstract {

    public String getLabel();

    public void compute(ImagePlus origImage, ImageProcessor binary);
}
