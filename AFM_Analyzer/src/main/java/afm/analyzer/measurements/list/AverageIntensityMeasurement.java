package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.scalings.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.awt.Rectangle;

/**
 *
 * @author Drimal
 */
public class AverageIntensityMeasurement extends AbstractMeasurement {

    public AverageIntensityMeasurement() {
        label = "Average Intensity Measurement";
        description = "Compute average intensity of structure area in nanometer.";
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            ScalerModule scalerModule) {
        double intensitySum = 0;
        int count = 0;

        Rectangle bounds = roi.getBounds();
        ImageProcessor ip = origImage.getProcessor();
        for (int i = (int) bounds.getX(); i < (int) (bounds.getX() + bounds.getWidth()); i++) {
            for (int j = (int) bounds.getY(); j < (int) (bounds.getY() + bounds.getHeight()); j++) {
                if (binary.get(i, j) == 255) {
                    intensitySum += ip.getPixelValue(i, j);
                    count++;
                }
            }
        }

        //TODO nanometer unit hardcoded
        double averageIntensityInNanoMeter = ((double) intensitySum / count) * Math.pow(10, 9);
        return averageIntensityInNanoMeter;
    }
}
