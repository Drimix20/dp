package afm.analyzer.presenter;

import afm.analyzer.presenter.listeners.StackSliceChangedListener;
import afm.analyzer.segmentation.SegmentedImage;
import afm.analyzer.selection.module.RoiSelectedListener;
import afm.analyzer.selection.module.RowSelectedListener;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 * Extended implementation of StackWindow for AFM analyzer.
 * @author Drimal
 */
public class AnalyzerImageWindow implements ImageWindowI, RowSelectedListener, StackSliceChangedListener {

    private String stackTitle = "AFM Analyzer Images";
    private static Logger logger = Logger.getLogger(AnalyzerImageWindow.class);
    private ExtendedImageStackWindow imageStackWindow;
    private List<SegmentedImage> imagesSegments;
    private static List<RoiSelectedListener> roiSelectedListeners;
    private static RoiManager roiManager;

    public AnalyzerImageWindow() {
        imageStackWindow = new ExtendedImageStackWindow(new ImagePlus());
        imageStackWindow.setVisible(false);
        imagesSegments = new ArrayList<>();
        roiSelectedListeners = new ArrayList<>();
        roiManager = new RoiManager(true);
        registerMouseListenerToImageCanvas(imageStackWindow.getCanvas());
    }

    @Override
    public void setImagesSegments(List<SegmentedImage> imagesSegments) {
        this.imagesSegments = imagesSegments;
    }

    @Override
    public boolean addRoiSelectedListener(RoiSelectedListener listener) {
        return roiSelectedListeners.add(listener);
    }

    @Override
    public boolean removeRoiSelectedListener(RoiSelectedListener listener) {
        return roiSelectedListeners.remove(listener);
    }

    @Override
    public void removeAllRoiSelectedListeners() {
        roiSelectedListeners.clear();
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

        imageStackWindow = new ExtendedImageStackWindow(new ImagePlus(stackTitle, imgStack));
        imageStackWindow.addStackSliceChangedListener(this);
        registerMouseListenerToImageCanvas(imageStackWindow.getCanvas());
        imageStackWindow.setTitle(stackTitle);
    }

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    @Override //TODO need Segmented image for show rois
    public void setImagesToShow(ImagePlus images) {
        imageStackWindow = new ExtendedImageStackWindow(images);
        registerMouseListenerToImageCanvas(imageStackWindow.getCanvas());
        imageStackWindow.setTitle(stackTitle);
    }

    private void registerMouseListenerToImageCanvas(ImageCanvas canvas) {
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                logger.trace("Pressed on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                logger.trace("Moved mouse on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.trace("Dragged mouse on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
            }

            //TODO implement multiple roi selection
            @Override
            public void mouseClicked(MouseEvent e) {
//                logger.debug("Clicked on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
                Roi roi = imageStackWindow.getImagePlus().getRoi();
                if (roi != null) {
                    int parsedRoiLabel = parseRoiLabel(roi.getName());
                    logger.debug("Clicked on image: " + e.getX() + ", " + e.getY() + ", roi label: " + parsedRoiLabel);
                    for (RoiSelectedListener listener : roiSelectedListeners) {
                        listener.notifySelectedRoi(parsedRoiLabel);
                    }
                } else {
                    logger.warn("No roi is selected");
                }
            }

        });
    }

    private int parseRoiLabel(String roiName) {
        logger.trace("Parse roi name: " + roiName);
        String roiLabeString = roiName.split("-")[0];
        return Integer.parseInt(roiLabeString);
    }

    @Override
    public void setVisible(boolean visible) {
        int size = imageStackWindow.getStackSize();
        if (size == 0) {
            logger.debug("Numb of images is 0. No images will be shown");
            imageStackWindow.setVisible(false);
        } else {
            logger.debug("Images will be displayed");
            imageStackWindow.setVisible(visible);
        }
    }

    @Override
    public void selectedRowIndexIsChanged(int rowIndex) {
        logger.trace("Event processed: selected row is " + rowIndex);
        roiManager.select(rowIndex + 1);
    }

    // StackSliceChangedListener implementation
    @Override
    public void movingSliceAboutAmount(int amount) {
        logger.trace("Moving slice about amount" + amount);
    }

    @Override
    public void moveToSpecificPosition(int currentPosition) {
        logger.trace("Move to specific position" + currentPosition);
    }

    @Override
    public void sliceAtPositionDeleted(int position) {
        logger.trace("Slice at position deleted " + position);
    }

    @Override
    public void currentStackIndex(int index) {
        logger.trace("Current stack index " + index);
    }
    // StackSliceChangedListener end of implementation
}
