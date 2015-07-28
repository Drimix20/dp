package afm.analyzer.measurements;

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

    public void compute(ChannelContainer container, SegmentedImage segmentedImage, AbstractMeasurement measure) {
        ImageProcessor thresholded = segmentedImage.getThresholdedIp();
        logger.info("Grid uLength: " + container.getGridULength() + "\n vLength: " + container.getGridVLength());
        logger.info("count; averageIntensity; volume");
        for (Roi roi : segmentedImage.getRois()) {
            measure.compute(roi, container.getImagePlus(), thresholded);
        }
    }
}
