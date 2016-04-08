package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import org.apache.log4j.Logger;
import scaler.module.ScalerModule;

/**
 *
 * @author Drimal
 */
public class AreaMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(AreaMeasurement.class);

    public AreaMeasurement() {
        super("Area measurement", "Compute structure area in nanometer");
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            ScalerModule scalerModule) {
        int count = 0;

        Rectangle bounds = roi.getBounds();
        ImageProcessor ip = origImage.getProcessor();
        for (int i = (int) bounds.getX(); i < (int) (bounds.getX() + bounds.getWidth()); i++) {
            for (int j = (int) bounds.getY(); j < (int) (bounds.getY() + bounds.getHeight()); j++) {
                if (binary.get(i, j) == 255) {
                    count++;
                }
            }
        }
        logger.trace("Count: " + count + " * pixelYsize=" + scalerModule.getPixelXSizeInMeter() + " * pixelYsize=" + scalerModule.getPixelYSizeInMeter() + "* " + Math.pow(10, 9));
        //TODO nanometer unit hardcoded
        return count * scalerModule.getPixelXSizeInMeter() * scalerModule.getPixelYSizeInMeter() * Math.pow(10, 9) * Math.pow(10, 9);
    }

}
