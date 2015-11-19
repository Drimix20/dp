package interactive.analyzer.selection;

import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class ImageSegments {

    private ImageProcessor thresholdedIp;
    //List<Segment> segments;
    List<Roi> rois;

    public ImageProcessor getThresholdedImageProcessor() {
        return thresholdedIp;
    }

    public void setThresholdedIp(ImageProcessor thresholdedIp) {
        this.thresholdedIp = thresholdedIp;
    }

    public List<Roi> getRois() {
        return rois;
    }

    public void setRois(List<Roi> rois) {
        this.rois = rois;
    }

}
