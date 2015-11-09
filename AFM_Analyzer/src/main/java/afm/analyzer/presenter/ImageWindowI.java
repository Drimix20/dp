package afm.analyzer.presenter;

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
     */
    public void setImagesToShow(List<ChannelContainer> channelContainer);

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

    void setVisible(boolean visible);

    public boolean addRoiSelectedListener(RoiSelectedListener listener);

    public boolean removeRoiSelectedListener(RoiSelectedListener listener);

    public void removeAllRoiSelectedListeners();

}
