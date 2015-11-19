package afm.analyzer.measurements;

import afm.analyzer.result.module.AbstractMeasurementResult;
import afm.analyzer.result.module.MeasurementResult;
import afm.analyzer.scalings.ScalerModule;
import interactive.analyzer.selection.ImageSegments;
import interactive.analyzer.selection.ExtendedRoi;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import selector.ChannelContainer;

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
        AbstractMeasurementResult results = new MeasurementResult();

        for (Roi roi : segmentedImage.getRois()) {
            int labelIndex = ((ExtendedRoi) roi).getLabel();
            double computedResult = measure.compute(roi, container.getImagePlus(), thresholded, scalerModule);
            results.addResult(labelIndex, computedResult);
        }

        return results;
    }
}
