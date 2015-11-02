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
public class AreaMeasurement extends AbstractMeasurement {

    public AreaMeasurement() {
        label = "Area measurement";
        description = "Compute structure area in nanometer";
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

        //TODO nanometer unit hardcoded
        return count * scalerModule.getPixelXSizeInMeter() * scalerModule.getPixelYSizeInMeter() * Math.pow(10, 9);
    }

}
