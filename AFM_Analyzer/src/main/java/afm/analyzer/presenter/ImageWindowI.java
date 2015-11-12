package afm.analyzer.presenter;

import afm.analyzer.segmentation.SegmentedImage;
import afm.analyzer.selection.module.RoiSelectedListener;
import ij.ImagePlus;
import java.util.List;
import selector.ChannelContainer;

/**
 * Defined methods for image presenter
 * @author Drimal
 */
public interface ImageWindowI {

    /**
     * Set images which will be shown saved in special object for AFM analyzer
     * @param channelContainer container contains images to show
     * @param segmentedImages rois for each image
     */
    public void setImagesToShow(List<ChannelContainer> channelContainer);

    /**
     * Set for each image segment with rois for compute measurements
     * @param imagesSegments
     */
    public void setImagesSegments(List<SegmentedImage> imagesSegments);

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    void setImagesToShow(ImagePlus images);

    /**
     * Configure visible title to image window
     * @param title image window title
     */
    void setTitle(String title);

    /**
     * Set ImageWindow visible or hidden
     * @param visible if true make ImageWindow visible else hidden
     */
    void setVisible(boolean visible);

    /**
     * Register roi selection listener
     * @param listener
     * @return
     */
    public boolean addRoiSelectedListener(RoiSelectedListener listener);

    /**
     * Unregister roi selection listener
     * @param listener
     * @return
     */
    public boolean removeRoiSelectedListener(RoiSelectedListener listener);

    /**
     * Remove all listeners
     */
    public void removeAllRoiSelectedListeners();

}
