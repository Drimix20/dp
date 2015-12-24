package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import java.awt.Color;
import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

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

    private static List<ImageSelectionListener> roiSelectedListeners;
    private ExtendedImageStackWindow imageStackWindow;
    private OverlayManager overlayManager;
    private List<Roi> rois;
    private ImagePlus showingImg;

    public InteractiveImageWindow(ImagePlus imp, List<Roi> rois) {
        validate(imp, rois);
        roiSelectedListeners = new ArrayList<>();

        //create custom image window implementation
        ImagePlus duplicatedImp = imp.duplicate();
        duplicatedImp.setTitle(stackTitle + "-" + imp.getTitle());
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
            throw new IllegalArgumentException("Rois is empty");
        }
    }

//    @Override
//    public void setImagesSegments(List<ImageSegments> imagesSegments) {
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
//        throw new UnsupportedOperationException();
//    }
    // <editor-fold defaultstate="collapsed" desc="Roi Selection Listener add, remove, removeAll">
    @Override
    public boolean addRoiSelectedListener(ImageSelectionListener listener) {
        return roiSelectedListeners.add(listener);
    }

    @Override
    public boolean removeRoiSelectedListener(ImageSelectionListener listener) {
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
//    @Override
//    public void setImagesToShow(List<ChannelContainer> channelContainer) {
//        ImagePlus tmp = channelContainer.get(0).getImagePlus();
//        ImageStack imgStack = new ImageStack(tmp.getWidth(), tmp.getHeight());
//        for (ChannelContainer channelContainer1 : channelContainer) {
//            imgStack.addSlice(channelContainer1.getImagePlus().getProcessor());
//        }
//
//        showingImg = new ImagePlus(stackTitle, imgStack);
//        configureImageStackWindow();
//        throw new UnsupportedOperationException();
//    }
    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
//    @Override
//    public void setImagesToShow(ImagePlus images) {
//        showingImg = images;
//        configureImageStackWindow();
//        throw new UnsupportedOperationException();
//    }
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
                ij.gui.Roi selectionRoi = showingImg.getRoi();

                if (selectionRoi != null) {
                    logger.trace("Selection done by other roi");
                    overlayManager.deselectAll();
                    notifyClearAllSelections();
                    List<Roi> multipleSelection = overlayManager.selectRoisInSelection(selectionRoi.getPolygon(), DEFAULT_ROI_SELECTION_COLOR);
                    printSelectedRois(multipleSelection);
                    notifyMultipleSelectionRoi(multipleSelection);
                }
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

            @Override
            public void mouseClicked(MouseEvent e) {
                ij.gui.Roi selectionRoi = showingImg.getRoi();

                if (selectionRoi != null) {
                    logger.trace("Selection done by other roi");
                    overlayManager.deselectAll();
                    notifyClearAllSelections();
                    List<Roi> multipleSelection = overlayManager.selectRoisInSelection(selectionRoi.getPolygon(), DEFAULT_STROKE_ROI_COLOR);
                    printSelectedRois(multipleSelection);
                    notifyMultipleSelectionRoi(multipleSelection);
                } else {
                    int modifiersEx = e.getModifiersEx();
                    Roi selectedRoi = overlayManager.getRoiFromPoint(e.getPoint());
                    if (selectedRoi == null) {
                        logger.trace("No roi is selected by click");
                        return;
                    }
                    if (modifiersEx == CTRL_DOWN_MASK) {
                        logger.trace("Selection with ctrl key down: " + selectedRoi.getName() + " roi name");
                        overlayManager.addRoiToSelection(selectedRoi, DEFAULT_STROKE_ROI_COLOR);
                        overlayManager.drawRois();
                        notifyMultipleSelectionRoi(Arrays.asList(selectedRoi));
                    } else {
                        logger.trace("Single selection: " + selectedRoi.getName() + " roi name");
                        overlayManager.deselectAll();
                        overlayManager.selectRoi(selectedRoi, DEFAULT_STROKE_ROI_COLOR);
                        overlayManager.drawRois();
                        notifySingleSelectionRoi(selectedRoi);
                    }
                }
            }

            private void printSelectedRois(List<Roi> rois) {
                String msg = "";
                for (Roi r : rois) {
                    msg += r.getName() + "; ";
                }
                logger.trace(msg);
            }
        });
    }

    public void notifyMultipleSelectionRoi(List<Roi> rois) {
        for (Roi r : rois) {
            for (ImageSelectionListener listener : roiSelectedListeners) {
                listener.selectedMultipleRoiEvent(r.getName());
            }
        }
    }

    public void notifySingleSelectionRoi(Roi roi) {
        for (ImageSelectionListener listener : roiSelectedListeners) {
            listener.selectedRoiEvent(roi.getName());
        }
    }

    public void notifyClearAllSelections() {
        for (ImageSelectionListener listener : roiSelectedListeners) {
            listener.clearAllSelectionsEvent();
        }
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

    // <editor-fold defaultstate="collapsed" desc="TableSelectionListener...">
    @Override
    public void singleRowSelectedEvent(int rowIndex, double value,
            Color color) {
        if (showingImg.getRoi() != null) {
            ij.gui.Roi imageR = null;
            showingImg.setRoi(imageR);
        }
        logger.trace("rowIndex: " + rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.deselectAll();
        overlayManager.selectRoi(labelToSelect, color);
        overlayManager.drawRois();
    }

    @Override
    public void multipleRowsSelectedEvent(int rowIndex, double value,
            Color color) {
        logger.trace("rowIndex: " + rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.addRoiToSelection(labelToSelect, color);
        overlayManager.drawRois();
    }

    @Override
    public void rowDeselectedEvent(int rowIndex) {
        logger.trace("rowIndex: " + rowIndex);
        int labelToSelect = rowIndex + 1;
        overlayManager.deselectRoi(labelToSelect, DEFAULT_STROKE_ROI_COLOR);
        overlayManager.drawRois();
    }

    @Override
    public void clearAllSelectionsEvent() {
        overlayManager.deselectAllAndRedraw();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ChartSelectionListener...">
    @Override
    public void singleBarSelectedEvent(double downRangeValue,
            double upperRangeValue, Color color) {
        logger.trace("Not Implemented");
    }

    @Override
    public void barSelectedEvent(double downRangeValue, double upperRangeValue,
            Color color) {
        logger.trace("Not Implemented");
    }

    @Override
    public void barDeselectedEvent(double downRangeValue,
            double upperRangeValue) {
        logger.trace("Not Implemented");
    }

    @Override
    public void clearBarSelectionsEvent() {
        overlayManager.deselectAllAndRedraw();
    }
    // </editor-fold>
}
