package afm.analyzer.measurements;

import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public interface AbstractMeasurement {

    public String getLabel();

    public void compute(Roi roi, ImagePlus origImage, ImageProcessor binary);
}
