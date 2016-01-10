package interactive.analyzer.presenter;

import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.listeners.ImageSelectionListener;

/**
 * Defined methods for image presenter
 * @author Drimal
 */
public interface ImageWindowI extends ChartSelectionListener {

    /**
     * Set images which will be shown saved in special object for AFM analyzer
     * @param channelContainer container contains images to show
     */
//    public void setImagesToShow(List<ChannelContainer> channelContainer);
    /**
     * Set for each image segment with rois for compute measurements
     * @param imagesSegments
     */
//    public void setImagesSegments(List<ImageSegments> imagesSegments);
    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
//    public void setImagesToShow(ImagePlus images);
    /**
     * Configure visible title to image window
     * @param title image window title
     */
    public void setTitle(String title);

    /**
     * Return name of image opened in interactive image window
     */
    public String getImageTitle();

    /**
     * Get image path
     * @return image path
     */
    public String getImagePath();

    /**
     * Set ImageWindow visible or hidden
     * @param visible if true make ImageWindow visible else hidden
     */
    public void setImageVisible(boolean visible);

    /**
     * Check if image window is visible
     * @return true if window is visible otherwise false
     */
    public boolean isVisible();

    /**
     * Register roi selection listener
     * @param listener
     * @return
     */
    public boolean addRoiSelectedListener(ImageSelectionListener listener);

    /**
     * Unregister roi selection listener
     * @param listener
     * @return
     */
    public boolean removeRoiSelectedListener(ImageSelectionListener listener);

    /**
     * Remove all listeners
     */
    public void removeAllRoiSelectedListeners();

}
