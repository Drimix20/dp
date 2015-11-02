package afm.analyzer.measurements;

import afm.analyzer.scalings.ScalerModule;
import afm.analyzer.segmentation.SegmentedImage;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class MeasurementComputation {

    private static final Logger logger = Logger.getLogger(MeasurementComputation.class);

    public MeasurementResults compute(ChannelContainer container,
            SegmentedImage segmentedImage, AbstractMeasurement measure) {
        ImageProcessor thresholded = segmentedImage.getThresholdedImageProcessor();

        //Module for scaling
        ScalerModule scalerModule = new ScalerModule(container.getGeneralMetadata(), container.getChannelMetadata());
        MeasurementResults results = new MeasurementResults();
        //TODO roi nahradit RoiWithLabel
        int labelIndex = 1;
        for (Roi roi : segmentedImage.getRois()) {
            double computedResult = measure.compute(roi, container.getImagePlus(), thresholded, scalerModule);
            results.addResult(labelIndex, computedResult);
            labelIndex++;
        }

        return results;
    }
}
