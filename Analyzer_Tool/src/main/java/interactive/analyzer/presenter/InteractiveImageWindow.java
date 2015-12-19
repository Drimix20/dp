package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import interactive.analyzer.listeners.RoiSelectedListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.selection.ImageSegments;
import java.awt.Color;
import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 * TODO not valid desc of class
 * Extended implementation of StackWindow for AFM analyzer.
 * @author Drimal
 */
public class InteractiveImageWindow implements ImageWindowI, TableSelectionListener {

    private static Logger logger = Logger.getLogger(InteractiveImageWindow.class);

    public static final Color DEFAULT_STROKE_ROI_COLOR = Color.YELLOW;
    public static final Color DEFAULT_ROI_SELECTION_COLOR = Color.CYAN;

    private static final int CTRL_WITH_LMB_DOWN = CTRL_DOWN_MASK | BUTTON1_DOWN_MASK;
    private static final int LMB_DOWN = BUTTON1_DOWN_MASK;

    private String stackTitle = "Interactive Analyzer window";

    private static List<RoiSelectedListener> roiSelectedListeners;
    private ExtendedImageStackWindow imageStackWindow;
    private OverlayManager overlayManager;
    private List<Roi> rois;
    private ImagePlus showingImg;

    public InteractiveImageWindow(ImagePlus imp, List<Roi> rois) {
        validate(imp, rois);
        roiSelectedListeners = new ArrayList<>();

        //create use custom image window implementation
        ImagePlus duplicatedImp = imp.duplicate();
        duplicatedImp.setOverlay(null);
        imageStackWindow = new ExtendedImageStackWindow(duplicatedImp);
        imageStackWindow.setTitle(stackTitle + "-" + imp.getTitle());
        imageStackWindow.setVisible(false);
        showingImg = duplicatedImp;

        this.rois = rois;
        overlayManager = new OverlayManager(this.rois, showingImg);
        overlayManager.drawRois();

        registerMouseListenerToImageCanvas(imageStackWindow.getCanvas());
    }

    private void validate(ImagePlus img, List<Roi> rois) {
        if (img == null) {
            throw new IllegalArgumentException("Image is null");
        }
        if (rois == null) {
            throw new IllegalArgumentException("Rois is null");
        }
        if (rois.isEmpty()) {
            throw new IllegalArgumentException("Rois is null");
        }
    }

    @Override
    public void setImagesSegments(List<ImageSegments> imagesSegments) {
//        this.imagesSegments = imagesSegments;
//        //TODO functionality just for one image in window
//        if (showingImg != null) {
//            int currentSlice = showingImg.getCurrentSlice();
//            ImageSegments segments = imagesSegments.get(currentSlice - 1);
//            for (Roi roi : segments.getRois()) {
//                roiManager.addRoi(roi);
//                //roiManager.add(showingImg, roi, ((ExtendedRoi) roi).getLabel());
//            }
//            showingImg.updateImage();
//        }
        throw new UnsupportedOperationException();
    }

    // <editor-fold defaultstate="collapsed" desc="Roi Selection Listener add, remove, removeAll">
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
    //</editor-fold>

    /**
     * Configure visible title to image window
     * @param title image window title
     */
    @Override
    public void setTitle(String title) {
        stackTitle = title;
    }

    @Override
    public boolean isVisible() {
        return imageStackWindow.isVisible();
    }

    /**
     * Set images which will be shown saved in special object for AFM analyzer
     * @param channelContainer container contains images to show
     */
    @Override
    public void setImagesToShow(List<ChannelContainer> channelContainer) {
//        ImagePlus tmp = channelContainer.get(0).getImagePlus();
//        ImageStack imgStack = new ImageStack(tmp.getWidth(), tmp.getHeight());
//        for (ChannelContainer channelContainer1 : channelContainer) {
//            imgStack.addSlice(channelContainer1.getImagePlus().getProcessor());
//        }
//
//        showingImg = new ImagePlus(stackTitle, imgStack);
//        configureImageStackWindow();
        throw new UnsupportedOperationException();
    }

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    @Override
    public void setImagesToShow(ImagePlus images) {
//        showingImg = images;
//        configureImageStackWindow();
        throw new UnsupportedOperationException();

    }

    //Not used
    private void configureImageStackWindow() {
        imageStackWindow = new ExtendedImageStackWindow(showingImg);
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
            public void mouseReleased(MouseEvent e) {
                logger.trace("Released on image: " + e.getX() + ", " + e.getY());
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
//                List<Roi> selectedRois = new ArrayList<>();
//                //Roi roi = imageStackWindow.getImagePlus().getRoi();
//                if (roi != null) {
//
//                    if (checkIfSelectionIsDoneByRoi(roi)) {
//                        //check if is new selection created
//
//                    } else {
//                        //direct selection is performed (ctrl, shift)
//                        switch (e.getModifiersEx()) {
//                            case LMB_DOWN:
//                                //single click selection
//                                overlayManager.selectRoi(roi, DEFAULT_ROI_SELECTION_COLOR);
//                                selectedRois.add(roi);
//                                break;
//                            case CTRL_WITH_LMB_DOWN:
//                                //multiple selection with ctrl down
//                                overlayManager.selectRoi(roi, DEFAULT_ROI_SELECTION_COLOR);
//                                selectedRois.add(roi);
//                                break;
//                        }
//                    }
//
//                    //notify listeners
//                    for (Roi r : selectedRois) {
//                        int parsedRoiLabel = parseRoiLabel(r.getName());
//                        logger.debug("Clicked on image: " + e.getX() + ", " + e.getY() + ", roi label: " + parsedRoiLabel);
//                        for (RoiSelectedListener listener : roiSelectedListeners) {
//                            listener.notifySelectedRoi(parsedRoiLabel);
//                        }
//                    }
//                } else {
//                    logger.warn("No roi is selected");
//                }
            }
        });
    }

    private boolean checkIfSelectionIsDoneByRoi(Roi roi) {
        return false;
    }

    private int parseRoiLabel(String roiName) {
        logger.trace("Parse roi name: " + roiName);
        String roiLabeString = roiName.split("-")[0];
        return Integer.parseInt(roiLabeString);
    }

    @Override
    public void setImageVisible(boolean visible) {
        int size = imageStackWindow.getStackSize();
        if (size == 0) {
            logger.debug("Numb of images is 0. No images will be shown");
            imageStackWindow.setVisible(false);
        } else {
            logger.debug("Images will be displayed");
            imageStackWindow.setVisible(visible);
        }
    }

    // TableSelectionListener implementation
    @Override
    public void selectedRowIndexIsChanged(int rowIndex, double value,
            Color color) {
        logger.trace(rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.selectRoi(labelToSelect, color);
    }

    @Override
    public void selectedMultipleRows(int rowIndex, double value, Color color) {
        logger.trace(rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.addRoiToSelection(labelToSelect, color);
    }

    @Override
    public void deselectedRow(int rowIndex) {
        logger.trace(rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.deselectRoi(labelToSelect, DEFAULT_STROKE_ROI_COLOR);
    }

    @Override
    public void clearAllSelections() {
        overlayManager.deselectAll();
    }
    // TableSelectionListener end of implementation

    @Override
    public void notifyBarSelected(double downRangeValue, double upperRangeValue,
            Color color) {
        //not used
    }

    @Override
    public void notifyBarDeselected(double downRangeValue,
            double upperRangeValue) {
        //not used
    }

    @Override
    public void notifyClearAllSelections() {
        overlayManager.deselectAll();
    }

}
