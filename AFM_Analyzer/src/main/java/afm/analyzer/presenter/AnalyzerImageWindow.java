package afm.analyzer.presenter;

import ij.ImagePlus;
import ij.ImageStack;
import java.util.List;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 * Extended implementation of StackWindow for AFM analyzer.
 * @author Drimal
 */
public class AnalyzerImageWindow implements ImageWindowI {

    private String stackTitle = "AFM Analyzer Images";
    private static Logger logger = Logger.getLogger(AnalyzerImageWindow.class);
    private ExtendedImageStack imageStack;

    public AnalyzerImageWindow() {
        imageStack = new ExtendedImageStack(new ImagePlus());
        imageStack.setVisible(false);
    }

    /**
     * Configure visible title to image window
     * @param title image window title
     */
    @Override
    public void setTitle(String title) {
        stackTitle = title;
    }

    /**
     * Set images which will be shown saved in special object for AFM analyzer
     * @param channelContainer container contains images to show
     */
    @Override
    public void setImagesToShow(List<ChannelContainer> channelContainer) {
        ImagePlus tmp = channelContainer.get(0).getImagePlus();
        ImageStack imgStack = new ImageStack(tmp.getWidth(), tmp.getHeight());
        for (int i = 0; i < channelContainer.size(); i++) {
            imgStack.addSlice(channelContainer.get(i).getImagePlus().getProcessor());
        }

        imageStack = new ExtendedImageStack(new ImagePlus(stackTitle, imgStack));
        imageStack.setTitle(stackTitle);
    }

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    @Override
    public void setImagesToShow(ImagePlus images) {
        imageStack = new ExtendedImageStack(images);
        imageStack.setTitle(stackTitle);
    }

    @Override
    public void setVisible(boolean visible) {
        int size = imageStack.getStackSize();
        if (size == 0) {
            logger.debug("Numb of images is 0. No images will be shown");
            imageStack.setVisible(false);
        } else {
            logger.debug("Images will be displayed");
            imageStack.setVisible(visible);
        }
    }

}
