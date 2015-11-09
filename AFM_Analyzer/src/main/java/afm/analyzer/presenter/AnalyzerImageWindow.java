package afm.analyzer.presenter;

import afm.analyzer.selection.module.RoiSelectedListener;
import afm.analyzer.selection.module.RowSelectedListener;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.ImageCanvas;
import ij.gui.Roi;
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
public class AnalyzerImageWindow implements ImageWindowI, RowSelectedListener {

    private String stackTitle = "AFM Analyzer Images";
    private static Logger logger = Logger.getLogger(AnalyzerImageWindow.class);
    private ExtendedImageStack imageStack;
    private static List<RoiSelectedListener> roiSelectedListeners;//TODO implement event on roi select

    public AnalyzerImageWindow() {
        imageStack = new ExtendedImageStack(new ImagePlus());
        imageStack.setVisible(false);
        roiSelectedListeners = new ArrayList<>();
        registerMouseListenerToImageCanvas(imageStack.getCanvas());
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

            @Override
            public void mouseClicked(MouseEvent e) {
//                logger.debug("Clicked on image: " + e.getX() + ", " + e.getY());
                super.mousePressed(e);
                Roi roi = imageStack.getImagePlus().getRoi();
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
        String roiLabeString = roiName.split("-")[0];
        return Integer.parseInt(roiLabeString);
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

        imageStack = new ExtendedImageStack(new ImagePlus(stackTitle, imgStack));
        registerMouseListenerToImageCanvas(imageStack.getCanvas());
        imageStack.setTitle(stackTitle);
    }

    /**
     * Set images which will be shown in image window
     * @param images one image or stack of images to show
     */
    @Override
    public void setImagesToShow(ImagePlus images) {
        imageStack = new ExtendedImageStack(images);
        registerMouseListenerToImageCanvas(imageStack.getCanvas());
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

    @Override
    public void selectedRowIndexIsChanged(int rowIndex) {
        logger.debug("Event processed: selected row is " + rowIndex);
    }

}
