package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import interactive.analyzer.listeners.RoiSelectedListener;
import interactive.analyzer.listeners.RowSelectedListener;
import interactive.analyzer.listeners.StackSliceChangedListener;
import interactive.analyzer.selection.ExtendedRoi;
import interactive.analyzer.selection.ImageSegments;
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
public class InteractiveImageWindow implements ImageWindowI, RowSelectedListener, StackSliceChangedListener {

    private String stackTitle = "AFM Analyzer Images";
    private static Logger logger = Logger.getLogger(InteractiveImageWindow.class);
    private ExtendedImageStackWindow imageStackWindow;
    private List<ImageSegments> imagesSegments;
    private static List<RoiSelectedListener> roiSelectedListeners;
    private ImagePlus showingImg;
    private static RoiManager roiManager;

    public InteractiveImageWindow() {
        imageStackWindow = new ExtendedImageStackWindow(new ImagePlus());
        imageStackWindow.setVisible(false);
        imagesSegments = new ArrayList<>();
        roiSelectedListeners = new ArrayList<>();
        roiManager = RoiManager.getInstance();
        if (roiManager == null) {
            roiManager = new RoiManager(true);
        }
        registerMouseListenerToImageCanvas(imageStackWindow.getCanvas());
    }

    @Override
    public void setImagesSegments(List<ImageSegments> imagesSegments) {
        this.imagesSegments = imagesSegments;
        //TODO functionality just for one image in window
        if (showingImg != null) {
            int currentSlice = showingImg.getCurrentSlice();
            ImageSegments segments = imagesSegments.get(currentSlice - 1);
            for (Roi roi : segments.getRois()) {
                roiManager.addRoi(roi);
                //roiManager.add(showingImg, roi, ((ExtendedRoi) roi).getLabel());
            }
            showingImg.updateImage();
        }
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

    public boolean isVisible() {
        return imageStackWindow.isVisible();
    }

    /**
     * Set images which will be shown saved in special object for AFM analyzer
     * @param channelContainer container contains images to show
     */
    @Override
    public void setImagesToShow(List<ChannelContainer> channelContainer) {
        ImagePlus tmp = channelContainer.get(0).getImagePlus();
        ImageStack imgStack = new ImageStack(tmp.getWidth(), tmp.getHeight());
        for (ChannelContainer channelContainer1 : channelContainer) {
            imgStack.addSlice(channelContainer1.getImagePlus().getProcessor());
        }

        showingImg = new ImagePlus(stackTitle, imgStack);
        configureImageStackWindow();
    }

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    @Override
    public void setImagesToShow(ImagePlus images) {
        showingImg = images;
        configureImageStackWindow();
    }

    private void configureImageStackWindow() {
        imageStackWindow = new ExtendedImageStackWindow(showingImg);
        imageStackWindow.addStackSliceChangedListener(this);
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
        int labelToSelect = rowIndex + 1;
        Roi selectedRoi = showingImg.getRoi();
        if (selectedRoi != null) {
            logger.debug("Old selected roi is " + selectedRoi.getName());
        }

        Roi[] roisAsArray = roiManager.getRoisAsArray();
        for (Roi roiToSelect : roisAsArray) {
            if (parseRoiLabel(roiToSelect.getName()) == labelToSelect) {
                String lbl = roiToSelect instanceof ExtendedRoi ? ((ExtendedRoi) roiToSelect).getLabel() + "" : roiToSelect.getName();
                logger.debug("Selecting new roi: " + lbl + "with index in manager " + rowIndex);
                roiManager.selectAndMakeVisible(showingImg, rowIndex);
            }
        }
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
