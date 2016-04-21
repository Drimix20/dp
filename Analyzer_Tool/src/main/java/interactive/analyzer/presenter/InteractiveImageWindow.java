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
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Interactive Image window which allow add ImageSelectionListener to opened image
 * @author Drimal
 */
public class InteractiveImageWindow implements ImageWindowI, TableSelectionListener {

    private static Logger logger = Logger.getLogger(InteractiveImageWindow.class);

    public static final Color DEFAULT_STROKE_ROI_COLOR = ImageWindowConfiguration.getStrokeColor();
    public static final Color DEFAULT_ROI_SELECTION_COLOR = Color.CYAN;

    private String frameTitle = "Interactive Analyzer - Image window";

    private static List<ImageSelectionListener> roiSelectedListeners;
    private ImagePlus duplicatedImp;
    private String imageName;
    private OverlayManager overlayManager;
    private List<Roi> rois;

    public InteractiveImageWindow(ImagePlus imp, List<Roi> rois) {
        validate(imp, rois);
        roiSelectedListeners = new ArrayList<ImageSelectionListener>();

        //create custom image window implementation
        duplicatedImp = imp.duplicate();
        imageName = imp.getTitle();
        duplicatedImp.setTitle(frameTitle + ":" + imageName);
        duplicatedImp.setOverlay(null);

        this.rois = rois;
        overlayManager = new OverlayManager(this.rois, duplicatedImp);
        overlayManager.drawRois();

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
                logger.trace("Released on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
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
            public void mouseClicked(MouseEvent e) {
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
        int size = duplicatedImp.getStackSize();
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
        overlayManager.drawRois();
    }

    @Override
    public void multipleRowsSelectedEvent(int roiId, double value,
            Color color) {
        logger.trace("rowIndex: " + roiId);
        overlayManager.addRoiToSelection(roiId, color);
        overlayManager.drawRois();
    }

    @Override
    public void rowDeselectedEvent(int roiId) {
        logger.trace("rowIndex: " + roiId);
        overlayManager.deselectRoi(roiId, DEFAULT_STROKE_ROI_COLOR);
        overlayManager.drawRois();
    }

    @Override
    public void redrawAllEvent() {
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
