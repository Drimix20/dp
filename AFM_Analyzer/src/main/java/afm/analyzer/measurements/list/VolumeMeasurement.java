package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
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

    private static final Logger logger = Logger.getLogger(VolumeMeasurement.class);

    public VolumeMeasurement() {
        label = "Volume measure";
        description = "Compute volume of protein";
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary) {
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
        double averageIntensity = ((double) intensitySum / count);
        double volume = count * averageIntensity;
        //logger.info(segment.getLabel() + " area: <" + count + ">, mean: <" + averageIntensity + ">, volume:<" + result + ">");
        System.out.println(count + ";" + averageIntensity + ";" + volume);
        //TODO just temporary solution
        return 1;
    }

}
