package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import org.apache.log4j.Logger;
import scaler.module.ScalerModule;
import scaler.module.types.LengthUnit;
import scaler.module.types.UnitConvertor;

/** *
 * @author Drimal
 */
public class AreaMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(AreaMeasurement.class);

    public AreaMeasurement() {
        super("Area measurement", 2);
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            ScalerModule scalerModule, LengthUnit unit) {
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

        Calibration calibration = origImage.getCalibration();
        LengthUnit dimensionUnit = LengthUnit.parseFromAbbreviation(calibration.getUnit().trim());

        double area = count * calibration.pixelWidth * calibration.pixelHeight;
        double convertedArea = UnitConvertor.convertValueWithPowerOfExponent(area, dimensionUnit, RESULT_NANOMETER_UNIT, getUnitExponent());
        logger.trace("Roi: " + roi.getName());
        logger.trace("Area: " + area + "= " + count + " * pixelYsize=" + calibration.pixelWidth + " * pixelYsize=" + calibration.pixelHeight + "[" + dimensionUnit + "^" + getUnitExponent() + "]");
        logger.trace("Converted area " + convertedArea + "[" + LengthUnit.NANOMETER + "^" + getUnitExponent() + "]");
        logger.trace("END Roi: " + roi.getName());
        return convertedArea;
    }

}
