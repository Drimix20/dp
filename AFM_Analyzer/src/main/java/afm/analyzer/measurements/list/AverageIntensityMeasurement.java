package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import scaler.module.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AverageIntensityMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(AverageIntensityMeasurement.class);

    public AverageIntensityMeasurement() {
        super("Average Intensity Measurement", "");
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

        double averageIntensity = intensitySum / count;
        return averageIntensity;
    }
}
