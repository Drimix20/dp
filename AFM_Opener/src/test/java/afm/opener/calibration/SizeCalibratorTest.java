package afm.opener.calibration;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class SizeCalibratorTest {

    @Test
    public void testCalibrateImageWidthInMeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.METER);
        assertEquals(0.0000009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInDecimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.DECIMETER);
        assertEquals(0.000009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInCentimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.CENTIMETER);
        assertEquals(0.00009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInMilimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.MILIMETER);
        assertEquals(0.0009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInMicrometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.MICROMETER);
        assertEquals(0.9765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInNanometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.001, LengthUnit.NANOMETER);
        assertEquals(976.5625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.METER);
        assertEquals(0.0000009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInDecimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.DECIMETER);
        assertEquals(0.000009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInCentimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.CENTIMETER);
        assertEquals(0.00009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMilimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.MILIMETER);
        assertEquals(0.0009765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMicrometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.MICROMETER);
        assertEquals(0.9765625, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInNanometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.NANOMETER);
        assertEquals(976.5625, calibrateHeightWidth, 0.0);
    }
}
