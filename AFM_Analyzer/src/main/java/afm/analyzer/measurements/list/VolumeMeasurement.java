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
public class VolumeMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(VolumeMeasurement.class);

    public VolumeMeasurement() {
        label = "Volume measure";
        description = "Compute volume of protein in nanometer^3";
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            ScalerModule scalerModule) {
        double intensitySum = 0;
        double count = 0;

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
        //TODO nanometer is hardcoded
        double scaledAverageIntensityInNanometer = scalerModule.scalePixelIntensityToObtainRealHeight(intensitySum / count) * Math.pow(10, 9);
        double scaledAreaInNanometer = count * scalerModule.getPixelXSizeInMeter() * scalerModule.getPixelYSizeInMeter() * Math.pow(10, 9) * Math.pow(10, 9);
        double volumeInNanometer = scaledAreaInNanometer * scaledAverageIntensityInNanometer;
        logger.trace("saceldAverageInensityInMeter: " + scaledAverageIntensityInNanometer + ", areaInMeter: " + scaledAreaInNanometer + ", volumeInNanometer: " + volumeInNanometer);
        return volumeInNanometer;
    }

}
