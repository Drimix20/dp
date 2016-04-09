package afm.analyzer.measurements;

import afm.analyzer.segmentation.ImageSegments;
import scaler.module.ScalerModule;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import afm.opener.selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class MeasurementComputation {

    //TODO instance of MeasurementResult is there hardcoded
    public AbstractMeasurementResult compute(ChannelContainer container,
            ImageSegments segmentedImage, AbstractMeasurement measure) {
        ImageProcessor thresholded = segmentedImage.getThresholdedImageProcessor();

        //Module for scaling
        ScalerModule scalerModule = new ScalerModule(container.getGeneralMetadata(), container.getChannelMetadata());
        AbstractMeasurementResult results = new MeasurementResult(measure.getLabel());

        for (Roi roi : segmentedImage.getRois()) {
            //TODO check roi name
            int labelIndex = Integer.parseInt(roi.getName());
            double computedResult = measure.compute(roi, container.getImagePlus(), thresholded, scalerModule);
            results.addResult(labelIndex, computedResult);
        }

        return results;
    }
}
