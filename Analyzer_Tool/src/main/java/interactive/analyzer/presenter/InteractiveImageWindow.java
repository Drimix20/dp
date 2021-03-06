package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.result.table.TableColorSelectionManager;
import java.awt.Color;
import java.awt.Point;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Interactive Image window which allow add ImageSelectionListener to opened image
 * @author Drimal
 */
public class InteractiveImageWindow implements ImageWindowI, TableSelectionListener {

    private static Logger logger = Logger.getLogger(InteractiveImageWindow.class);

    public static final Color DEFAULT_STROKE_ROI_COLOR = ImageWindowConfiguration.getStrokeColor();

    private String frameTitle = "Interactive Analyzer - Image window";

    private static List<ImageSelectionListener> roiSelectedListeners;
    private ImagePlus duplicatedImp;
    private String imageName;
    private IOverlayManager overlayManager;

    public InteractiveImageWindow(ImagePlus imp, List<Roi> rois) {
        validate(imp, rois);
        roiSelectedListeners = new ArrayList<ImageSelectionListener>();

        //create custom image window implementation
        duplicatedImp = imp.duplicate();
        imageName = imp.getTitle();
        duplicatedImp.setTitle(frameTitle + ":" + imageName);
        duplicatedImp.setOverlay(null);

        overlayManager = new OverlayManager(rois, duplicatedImp);
        overlayManager.drawAllRois();

        duplicatedImp.show();
        registerMouseListenerToImageCanvas(duplicatedImp.getCanvas());
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
        frameTitle = title;
    }

    @Override
    public String getImageTitle() {
        return imageName;
    }

    @Override
    public boolean isVisible() {
        return duplicatedImp.isVisible();
    }

    @Override
    public String getImagePath() {
        return duplicatedImp.getFileInfo().directory + File.separator + duplicatedImp.getFileInfo().fileName;
    }

    private void registerMouseListenerToImageCanvas(ImageCanvas canvas) {
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                ij.gui.Roi selectionRoi = duplicatedImp.getRoi();

                if (selectionRoi != null) {
                    logger.trace("Selection done by other roi");
                    overlayManager.deselectAll();
                    notifyClearAllSelections();
                    List<Roi> multipleSelection = overlayManager.selectRoisInSelection(selectionRoi.getPolygon(), TableColorSelectionManager.getInstance().getCurrentSelectionColor());
                    printSelectedRois(multipleSelection);
                    notifyMultipleSelectionRoi(multipleSelection);
                }
            }

            @Override
            public void mouseClicked(final MouseEvent e) {
                new Runnable() {

                    @Override
                    public void run() {
                        ij.gui.Roi selectionRoi = duplicatedImp.getRoi();

                        if (selectionRoi != null) {
                            logger.trace("Selection done by other roi");
                            overlayManager.deselectAll();
                            notifyClearAllSelections();
                            List<Roi> multipleSelection = overlayManager.selectRoisInSelection(selectionRoi.getPolygon(), DEFAULT_STROKE_ROI_COLOR);
                            printSelectedRois(multipleSelection);
                            notifyMultipleSelectionRoi(multipleSelection);
                        } else {
                            Point cursorLoc = duplicatedImp.getCanvas().getCursorLoc();
                            int modifiersEx = e.getModifiersEx();
                            Roi selectedRoi = overlayManager.getRoiFromPoint(cursorLoc);
                            if (selectedRoi == null) {
                                logger.trace("No roi is selected by click");
                                return;
                            }
                            int roiId = selectedRoi.getName();
                            if (modifiersEx == CTRL_DOWN_MASK) {
                                logger.trace("Selection with ctrl key down: " + roiId + " roi name");
                                overlayManager.addRoiToSelection(roiId, DEFAULT_STROKE_ROI_COLOR);
                                overlayManager.drawRoi(roiId);
                                notifyMultipleSelectionRoi(Arrays.asList(selectedRoi));
                            } else {
                                logger.trace("Single selection: " + roiId + " roi name");
                                overlayManager.deselectAll();
                                overlayManager.selectRoi(roiId, DEFAULT_STROKE_ROI_COLOR);
                                overlayManager.drawAllRois();
                                notifySingleSelectionRoi(selectedRoi);
                            }
                        }
                    }
                }.run();
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

    /**
     Notify all subscribers to clear all selections
     */
    public void notifyClearAllSelections() {
        for (ImageSelectionListener listener : roiSelectedListeners) {
            listener.clearAllSelectionsEvent();
        }
    }

    @Override
    public void setImageVisible(boolean visible) {
        if (visible) {
            logger.debug("Numb of images is 0. No images will be shown");
            duplicatedImp.show();
        } else {
            logger.debug("Images will be displayed");
            duplicatedImp.hide();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="TableSelectionListener...">
    @Override
    public void singleRowSelectedEvent(int roiId, double value,
            Color color) {
        //Make selected just one roi
        if (duplicatedImp.getRoi() != null) {
            ij.gui.Roi imageR = null;
            duplicatedImp.setRoi(imageR);
        }
        logger.trace("RoiId: " + roiId);
        overlayManager.deselectAll();
        overlayManager.selectRoi(roiId, color);
        overlayManager.drawAllRois();
    }

    @Override
    public void multipleRowsSelectedEvent(int roiId, double value,
            Color color) {
        logger.trace("RoiId: " + roiId);
        overlayManager.addRoiToSelection(roiId, color);
        overlayManager.drawRoi(roiId);
    }

    @Override
    public void rowDeselectedEvent(int roiId) {
        logger.trace("RoiId: " + roiId);
        overlayManager.deselectRoi(roiId, DEFAULT_STROKE_ROI_COLOR);
        overlayManager.drawRoi(roiId);
    }

    /**
     Redraw all rois
     */
    @Override
    public void repaintAllEvent() {
        logger.warn("Drawing all rois");
        overlayManager.drawAllRois();
    }

    @Override
    public void clearAllSelectionsEvent() {
        overlayManager.deselectAllAndRedraw();
    }

    @Override
    public void removeRoisEvent(final Set<Integer> roiIds) {
        logger.trace("RoisIds: " + roiIds.size());
        if (roiIds == null) {
            throw new NullPointerException("Set of rois can't be null");
        }
        for (Integer roiId : roiIds) {
            overlayManager.removeRoi(roiId);
        }
        overlayManager.drawAllRois();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ChartSelectionListener...">
    @Override
    public void singleBarSelectedEvent(double downRangeValue,
            double upperRangeValue, Color color) {
        logger.trace("Can not Implemented");
    }

    @Override
    public void multipleBarSelectedEvent(double downRangeValue,
            double upperRangeValue,
            Color color) {
        logger.trace("Can not implement");
    }

    @Override
    public void barDeselectedEvent(double downRangeValue,
            double upperRangeValue) {
        logger.trace("Can not implement");
    }

    @Override
    public void clearBarSelectionsEvent() {
        overlayManager.deselectAllAndRedraw();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ImageWindowObjectListener...">
    @Override
    public void multipleRoiSelected(int roiId, Color color) {
        logger.trace("RoiId: " + roiId);
        overlayManager.addRoiToSelection(roiId, color);
        overlayManager.drawRoi(roiId);
    }

    @Override
    public void roiDeselected(int roiId) {
        logger.trace("RoiId: " + roiId);
        overlayManager.deselectRoi(roiId, DEFAULT_STROKE_ROI_COLOR);
        overlayManager.drawRoi(roiId);
    }
    // </editor-fold>

    @Override
    public Collection<Roi> getAllRoisInImage() {
        return overlayManager.getAllRois();
    }

    @Override
    public int getImageWidth() {
        return duplicatedImp.getWidth();
    }

    @Override
    public int getImageHeight() {
        return duplicatedImp.getHeight();
    }

}
