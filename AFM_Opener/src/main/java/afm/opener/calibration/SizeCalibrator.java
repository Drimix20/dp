package afm.opener.calibration;

import afm.opener.selector.ChannelContainer;
import ij.ImagePlus;
import org.apache.log4j.Logger;
import scaler.module.types.LengthUnit;

/**
 *
 * @author Drimal
 */
public class SizeCalibrator {

    private static Logger logger = Logger.getLogger(SizeCalibrator.class);

    public double calibrateImageWidth(ChannelContainer container,
            LengthUnit unit) {
        logger.trace("Calibrate image width to " + unit.getAbbreviation() + " unit");
        double uLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(32834);

        ImagePlus img = container.getImagePlus();
        return calibrateImageWidth(img.getWidth(), uLengthInMeter, unit);
    }

    public double calibrateImageWidth(int imageWidth, double uLengthInMeter,
            LengthUnit unit) {
        // Compute pixel width
        double convertedPixelWidth = uLengthInMeter / imageWidth * unit.getValue();
        logger.trace("Pixel width " + convertedPixelWidth + " " + unit.getAbbreviation());

        // Convert image width to specific unit
        double convertedImageWidth = uLengthInMeter * unit.getValue();

        logger.trace("Calibrated image width: origin value in metadata " + uLengthInMeter + " in m to " + convertedImageWidth + " " + unit.getAbbreviation());
        return convertedImageWidth;
    }

    public double calibrateImageHeight(ChannelContainer container,
            LengthUnit unit) {
        double vLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(32835);

        ImagePlus img = container.getImagePlus();
        return calibrateImageHeight(img.getHeight(), vLengthInMeter, unit);
    }

    public double calibrateImageHeight(int imageHeight, double vLengthInMeter,
            LengthUnit unit) {

        // Compute pixel width
        double convertedPixelHeight = vLengthInMeter / imageHeight * unit.getValue();
        logger.trace("Pixel height " + convertedPixelHeight + " " + unit.getAbbreviation());

        // Convert image width to specific unit
        double convertedImageHeight = vLengthInMeter * unit.getValue();

        logger.trace("Calibrated image height: origin value in metadata " + vLengthInMeter + " in m to " + convertedImageHeight + " " + unit.getAbbreviation());
        return convertedImageHeight;
    }
}
