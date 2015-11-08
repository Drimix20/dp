package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.scalings.ScalerModule;
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
        double averageIntensity = intensitySum / count;
        double scaledAverageIntensity = scalerModule.scalePixelIntensityToObtainRealHeight(averageIntensity);
        double scaledAverageIntensityInNanoMeter = scaledAverageIntensity * Math.pow(10, 9);
        logger.trace("count: " + count + ", intensitySum: " + intensitySum + ", averageIntensity: " + averageIntensity + ", scaledAverageIntensity: " + scaledAverageIntensity + ", scaledAverageIntensityInNanometer: " + scaledAverageIntensityInNanoMeter);
        return scaledAverageIntensityInNanoMeter;
    }
}
