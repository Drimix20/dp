package afm.analyzer.presenter;

import afm.analyzer.presenter.listeners.StackSliceChangedListener;
import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.gui.StackWindow;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ExtendedImageStack extends StackWindow implements ImageListener {

    private static Logger logger = Logger.getLogger(ExtendedImageStack.class);
    private StackSliceChangedListener stackSliceMovedListener;
    private int currentSliceIndex;
    private int numbOfSlices;

    public ExtendedImageStack(ImagePlus imp) {
        super(imp);
        numbOfSlices = imp.getImageStackSize();
        super.imp.addImageListener(this);
    }

    public int getStackSize() {
        return imp.getStackSize();
    }

    public void setStackSliceChangedListener(StackSliceChangedListener listener) {
        logger.info("Set up of stackSliceMovedListener");
        this.stackSliceMovedListener = listener;
    }

    @Override
    public void imageOpened(ImagePlus imp) {
        logger.info("ImageListener - imageOpened");
    }

    @Override
    public void imageClosed(ImagePlus imp) {
        logger.info("ImageListener - imageClosed");
    }

    @Override
    public synchronized void imageUpdated(ImagePlus imp) {
        int curSliceIndexFromImage = imp.getCurrentSlice();
        int numbOfSliceFromImage = imp.getImageStackSize();
        logger.info("ImageListener - imageUpdated - currentSliceVariable: " + currentSliceIndex + ", currentSliceFromImage: " + curSliceIndexFromImage + ", numbOfSlices:" + numbOfSlices + ", numbOfSlicesFromImage:" + numbOfSliceFromImage);
        if (numbOfSlices > numbOfSliceFromImage) {
            logger.info("Slice with index " + currentSliceIndex + "was deleted");
            if (stackSliceMovedListener != null) {
                logger.info("Notify listener sliceAtPositionDeleted " + currentSliceIndex);
                stackSliceMovedListener.sliceAtPositionDeleted(currentSliceIndex);
            }
            numbOfSlices = numbOfSliceFromImage;
            currentSliceIndex = curSliceIndexFromImage - 1;
            logger.info("ImageListener - imageUpdated-deletedSlice - currentSliceVariable: " + currentSliceIndex + ", numbOfSlices: " + numbOfSlices);
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
        if (stackSliceMovedListener != null) {
            logger.info("Notify listener moveToSpecificPosition " + currentSliceIndex);
            stackSliceMovedListener.moveToSpecificPosition(currentSliceIndex);
        }
    }
}
