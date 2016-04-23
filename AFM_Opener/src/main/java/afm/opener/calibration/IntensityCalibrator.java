package afm.opener.calibration;

import afm.opener.selector.ChannelContainer;
import ij.IJ;
import ij.measure.CurveFitter;
import ij.measure.Minimizer;
import org.apache.log4j.Logger;
import scaler.module.ScalerModule;
import scaler.module.types.LengthUnit;
import scaler.module.types.UnsupportedScalingType;

/**
 *
 * @author Drimal
 */
public class IntensityCalibrator {

    private static Logger logger = Logger.getLogger(IntensityCalibrator.class);

    public double[] computeCalibrationFunction(
            ChannelContainer container, double minVal, double maxVal,
            int calibrationType, LengthUnit unit) throws UnsupportedScalingType {

        double minValFromImage = container.getImagePlus().getProcessor().getMin();
        double maxValFromImage = container.getImagePlus().getProcessor().getMax();

        ScalerModule sm = new ScalerModule(container.getGeneralMetadata(), container.getChannelMetadata());
        double calibratedMinVal = sm.scalePixelIntensityToObtainRealHeight(minVal, unit);
        double calibratedMaxVal = sm.scalePixelIntensityToObtainRealHeight(maxVal, unit);

        logger.trace("Input for CurveFitter: " + minVal + " -> " + calibratedMinVal + ", " + maxVal + " -> " + calibratedMaxVal + ", unit: " + unit.getAbbreviation());
        double[] x = new double[]{minValFromImage, maxValFromImage};
        double[] y = new double[]{calibratedMinVal, calibratedMaxVal};
        return doCurveFitting(x, y, calibrationType);
    }

    /**
     * Compute parameters for calibrating function for intensity calibration
     * @param x
     * @param y
     * @param fitType type of fit function, used are None and Straight_line form Calibration class
     * @return function's parameters
     */
    protected double[] doCurveFitting(double[] x, double[] y, int fitType) {
        if (x.length != y.length || y.length == 0) {
            logger.trace("Wrong parameters - parameters has not same length");
            return null;
        }

        CurveFitter cf = new CurveFitter(x, y);
        cf.doFit(fitType, false);
        if (cf.getStatus() == Minimizer.INITIALIZATION_FAILURE) {
            logger.trace("Initialization failure");
            return null;
        }
        if (IJ.debugMode) {
            IJ.log(cf.getResultString());
        }
        logger.trace("Curve fitter result string - " + cf.getResultString());
        int np = cf.getNumParams();
        double[] p = cf.getParams();
        double[] parameters = new double[np];
        for (int i = 0; i < np; i++) {
            parameters[i] = p[i];
        }
        return parameters;
    }
}
