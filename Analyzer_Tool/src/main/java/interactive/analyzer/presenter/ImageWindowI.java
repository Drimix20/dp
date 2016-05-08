package interactive.analyzer.presenter;

import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.ImageWindowObjectListener;
import java.util.Collection;

/**
 * Defined methods for image presenter
 * @author Drimal
 */
public interface ImageWindowI extends ChartSelectionListener, ImageWindowObjectListener {

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

    /**
     * Return all ROIs in image as unmodifiable collection
     * @return rois
     */
    public Collection<Roi> getAllRoisInImage();

    /**
     * Return width of opened image
     * @return
     */
    public int getImageWidth();

    /**
     * Return height of opened image
     * @return
     */
    public int getImageHeight();

}
