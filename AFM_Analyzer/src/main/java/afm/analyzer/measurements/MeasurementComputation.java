package afm.analyzer.measurements;

import afm.analyzer.segmentation.SegmentedImage;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class MeasurementComputation {

    public void compute(ChannelContainer container, SegmentedImage segmentedImage, AbstractMeasurement measure) {
        ImageProcessor thresholded = segmentedImage.getThresholdedIp();
        System.out.println("Grid uLength:" + container.getGridULength() + "\n vLength:" + container.getGridVLength());
        System.out.println("count; averageIntensity; volume");
        for (Roi roi : segmentedImage.getRois()) {
            measure.compute(roi, container.getImagePlus(), thresholded);
        }
    }
}
