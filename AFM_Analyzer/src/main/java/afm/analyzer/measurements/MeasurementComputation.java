package afm.analyzer.measurements;

import afm.analyzer.segmentation.ImageSegments;
import scaler.module.ScalerModule;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import afm.opener.selector.ChannelContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Drimal
 */
public class MeasurementComputation {

    private static Logger logger = LoggerFactory.getLogger(MeasurementComputation.class);

    public AbstractMeasurementResult compute(ChannelContainer container,
            ImageSegments segmentedImage, AbstractMeasurement measure) {
        ImageProcessor thresholded = segmentedImage.getThresholdedImageProcessor();

        String calibrationUnit = container.getImagePlus().getCalibration().getUnit();
        logger.trace(String.format("Calibration unit %s", calibrationUnit));

        ScalerModule scalerModule = new ScalerModule(container.getGeneralMetadata(), container.getChannelMetadata());
        AbstractMeasurementResult results = new MeasurementResult(measure.getLabel(), calibrationUnit, measure.getUnitRegulation());

        for (Roi roi : segmentedImage.getRois()) {
            int labelIndex = Integer.parseInt(roi.getName());
            double computedResult = measure.compute(roi, container.getImagePlus(), thresholded, scalerModule);
            results.addResult(labelIndex, computedResult);
        }

        return results;
    }
}
