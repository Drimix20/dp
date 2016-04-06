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
        assertEquals(0.001, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInDecimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 15, LengthUnit.DECIMETER);
        assertEquals(150, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInCentimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 15, LengthUnit.CENTIMETER);
        assertEquals(1500, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInMilimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 1, LengthUnit.MILIMETER);
        assertEquals(1000, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInMicrometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 2, LengthUnit.MICROMETER);
        assertEquals(2000000, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageWidthInNanometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageWidth(1024, 0.5, LengthUnit.NANOMETER);
        assertEquals(500000000, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.METER);
        assertEquals(0.001, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInDecimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.DECIMETER);
        assertEquals(0.01, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInCentimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.CENTIMETER);
        assertEquals(0.1, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMilimeter() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.MILIMETER);
        assertEquals(1, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInMicrometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.MICROMETER);
        assertEquals(1000, calibrateHeightWidth, 0.0);
    }

    @Test
    public void testCalibrateImageHeightInNanometer() {
        SizeCalibrator calibrator = new SizeCalibrator();
        double calibrateHeightWidth = calibrator.calibrateImageHeight(1024, 0.001, LengthUnit.NANOMETER);
        assertEquals(1000000, calibrateHeightWidth, 0.0);
    }
}
