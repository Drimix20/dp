package afm.analyzer.segmentation;

import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.util.List;

/**
 *
 * @author Drimal
 */
//TODO object Segment is not needed
public class SegmentedImage {

    private ImageProcessor thresholdedIp;
    //List<Segment> segments;
    List<Roi> rois;

    public ImageProcessor getThresholdedImageProcessor() {
        return thresholdedIp;
    }

    public void setThresholdedIp(ImageProcessor thresholdedIp) {
        this.thresholdedIp = thresholdedIp;
    }

//    public List<Segment> getSegments() {
//        return segments;
//    }
//    public void setSegments(List<Segment> segments) {
//        this.segments = segments;
//    }
    public List<Roi> getRois() {
        return rois;
    }

    public void setRois(List<Roi> rois) {
        this.rois = rois;
    }

}
