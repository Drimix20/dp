package afm.analyzer.presenter;

import afm.analyzer.presenter.listeners.StackSliceChangedListener;
import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.gui.StackWindow;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ExtendedImageStackWindow extends StackWindow implements ImageListener {

    private static Logger logger = Logger.getLogger(ExtendedImageStackWindow.class);
    private List<StackSliceChangedListener> stackSliceMovedListener;
    private int currentSliceIndex;
    private int numbOfSlices;

    public ExtendedImageStackWindow(ImagePlus imp) {
        super(imp);
        numbOfSlices = imp.getImageStackSize();
        stackSliceMovedListener = new ArrayList<>();
        super.imp.addImageListener(this);
    }

    public int getStackSize() {
        return imp.getStackSize();
    }

    public boolean addStackSliceChangedListener(
            StackSliceChangedListener listener) {
        logger.info("Add stackSliceMovedListener");
        return this.stackSliceMovedListener.add(listener);
    }

    public boolean removeStackSliceChangedListener(
            StackSliceChangedListener listener) {
        logger.info("Remove stackSliceMovedListener");
        return this.stackSliceMovedListener.remove(listener);
    }

    public void removeAllStackSliceChangedListener() {
        stackSliceMovedListener.clear();
    }

    @Override
    public void imageOpened(ImagePlus imp) {
        logger.trace("ImageListener - imageOpened");
    }

    @Override
    public void imageClosed(ImagePlus imp) {
        logger.trace("ImageListener - imageClosed");
    }

    @Override
    public synchronized void imageUpdated(ImagePlus imp) {
        int curSliceIndexFromImage = imp.getCurrentSlice();
        int numbOfSliceFromImage = imp.getImageStackSize();
        logger.trace("ImageListener - imageUpdated - currentSliceVariable: " + currentSliceIndex + ", currentSliceFromImage: " + curSliceIndexFromImage + ", numbOfSlices:" + numbOfSlices + ", numbOfSlicesFromImage:" + numbOfSliceFromImage);
        if (numbOfSlices > numbOfSliceFromImage) {
            logger.trace("Slice with index " + currentSliceIndex + "was deleted");
            logger.trace("Notify listener sliceAtPositionDeleted " + currentSliceIndex);
            for (StackSliceChangedListener listener : stackSliceMovedListener) {
                listener.sliceAtPositionDeleted(currentSliceIndex);
            }
            numbOfSlices = numbOfSliceFromImage;
            currentSliceIndex = curSliceIndexFromImage - 1;
            logger.trace("ImageListener - imageUpdated-deletedSlice - currentSliceVariable: " + currentSliceIndex + ", numbOfSlices: " + numbOfSlices);
        } else {
            logger.trace("Current slice index");
            for (StackSliceChangedListener listener : stackSliceMovedListener) {
                listener.currentStackIndex(currentSliceIndex);
            }
        }
    }

    /**
     * Method observes mouse wheel event and save current slice index into local variable.
     * Slice index is increment by one cause of execution after imageUpdate from ImageListener.
     * This incrementation is needed for correct determination for current slice delete.
     *
     * @param event mouse wheel event
     */
    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent event) {
        if (imp.getStackSize() == 1) {
            logger.info("Showed image is not stack. No message will be shown.");
            for (StackSliceChangedListener listener : stackSliceMovedListener) {
                listener.currentStackIndex(currentSliceIndex);
            }
            return;
        }
        IJ.showMessage("Moving by mouse wheel is not supported!");
    }

    /**
     * Method observes adjustment event and save current slice index into local variable.
     * This incrementation is needed for correct determination for current slice delete.
     *
     * @param event adjustment event
     */
    @Override
    public synchronized void adjustmentValueChanged(AdjustmentEvent event) {
        super.adjustmentValueChanged(event);
        currentSliceIndex = super.imp.getCurrentSlice();
        logger.info("Adjustment value changed. Current slice is " + currentSliceIndex);
        logger.info("Notify listener moveToSpecificPosition " + currentSliceIndex);
        for (StackSliceChangedListener listener : stackSliceMovedListener) {
            listener.moveToSpecificPosition(currentSliceIndex);
        }
    }
}
