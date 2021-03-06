package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import interactive.analyzer.result.table.TableColorSelectionManager;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class OverlayManager implements IOverlayManager {

    private static Logger logger = Logger.getLogger(OverlayManager.class);
    //Map of rois : <roiID, roiObject>
    private ConcurrentMap<Integer, Roi> roisMap = new ConcurrentHashMap<Integer, Roi>();
    private ImagePlus img;
    private volatile ImageProcessor imageProcessor;

    public OverlayManager(List<Roi> rois, ImagePlus img) {
        if (rois == null) {
            throw new IllegalArgumentException("List of rois is null");
        }
        if (img == null) {
            throw new IllegalArgumentException("Image is null");
        }
        for (Roi r : rois) {
            roisMap.put(r.getName(), r);
        }
        this.img = img;

        imageProcessor = new ColorProcessor(img.getBufferedImage());
        imageProcessor.snapshot();
        img.setProcessor(imageProcessor);
    }

    @Override
    public Collection<Roi> getAllRois() {
        List<Roi> rois = new ArrayList<Roi>();
        for (Map.Entry<Integer, Roi> entrySet : roisMap.entrySet()) {
            rois.add(entrySet.getValue());
        }

        return Collections.unmodifiableCollection(rois);
    }

    @Override
    public synchronized void drawAllRois() {
        logger.trace("");
        new Runnable() {

            @Override
            public void run() {
                imageProcessor.reset();
                imageProcessor.setLineWidth(ImageWindowConfiguration.getStrokeWidth());
                for (Integer key : roisMap.keySet()) {
                    Roi r = roisMap.get(key);
                    if (r != null && r.stateChanged()) {
                        Polygon polygon = r.getPolygon();
                        drawName(r.getName() + "", polygon, imageProcessor);
                        Color strokeColor = r.isSelected() ? TableColorSelectionManager.getInstance().getCurrentSelectionColor() : ImageWindowConfiguration.getStrokeColor();
                        r.setStrokeColor(strokeColor);
                        imageProcessor.setColor(strokeColor);
                        imageProcessor.drawPolygon(polygon);
                    }
                }
                img.updateAndDraw();
            }

        }.run();
    }

    @Override
    public synchronized void drawRoi(final int roiId) {
        new Runnable() {

            @Override
            public void run() {
                Roi r = roisMap.get(roiId);
                if (r != null && r.stateChanged()) {
                    Polygon polygon = r.getPolygon();
                    drawName(r.getName() + "", polygon, imageProcessor);
                    Color strokeColor = r.isSelected() ? r.getStrokeColor() : ImageWindowConfiguration.getStrokeColor();
                    imageProcessor.setLineWidth(ImageWindowConfiguration.getStrokeWidth());
                    imageProcessor.setColor(strokeColor);
                    imageProcessor.drawPolygon(polygon);
                    img.updateAndDraw();
                }
            }
        }.run();
    }

    private void drawName(String name, Polygon p, ImageProcessor ip) {
        validateDrawName(name, p, ip);
        FontMetrics fontMetrics = createFont();
        int centerX = (int) p.getBounds().getCenterX() - fontMetrics.stringWidth(name) / 2;
        int centerY = (int) p.getBounds().getCenterY() + fontMetrics.getHeight() / 2;

        ip.setColor(ImageWindowConfiguration.getFontColor());
        ip.drawString(name, centerX, centerY, ImageWindowConfiguration.getFontBackgroundColor());
    }

    private FontMetrics createFont() {
        Font font = new Font(Font.SERIF, Font.PLAIN, ImageWindowConfiguration.getFontSize());
        return new Canvas().getFontMetrics(font);
    }

    private void validateDrawName(String name, Polygon p, ImageProcessor ip) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        if (p == null) {
            throw new IllegalArgumentException("Polygon is null");
        }
        if (ip == null) {
            throw new IllegalArgumentException("Image processor is null");
        }
    }

    @Override
    public synchronized List<Roi> selectRoisInSelection(Polygon polygon,
            Color strokeColor) {
        if (polygon == null) {
            throw new IllegalArgumentException("Selection polygon is null");
        }
        validateColor(strokeColor);
        logger.trace("Select all rois in selection");
        List<Roi> selectedRois = new ArrayList<Roi>();
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            //Check if selectionRoi polygon intersects with someone roi in list
            if (polygon.intersects(r.getPolygon().getBounds())) {
                r.setStrokeColor(strokeColor);
                if (!r.isSelected()) {
                    r.setSelected(true);
                }
                selectedRois.add(r);
            }
        }
        return selectedRois;
    }

    @Override
    public synchronized void addRoiToSelection(int roiName, Color strokeColor) {
        validateRoiName(roiName);
        validateColor(strokeColor);
        logger.trace("Add roi " + roiName + " to selection");
        Roi r = roisMap.get(roiName);
        if (r != null) {
            r.setStrokeColor(strokeColor);
            if (!r.isSelected()) {
                r.setSelected(true);
            }
        }
    }

    @Override
    public synchronized void selectRoi(int roiName, Color strokeColor) {
        validateRoiName(roiName);
        validateColor(strokeColor);
        logger.trace("Select roi " + roiName);
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            if (r.getName() == roiName) {
                r.setStrokeColor(strokeColor);
                if (!r.isSelected()) {
                    r.setSelected(true);
                }
            } else {
                r.setSelected(false);
            }
        }
    }

    @Override
    public synchronized void deselectRoi(int roiName, Color strokeColor) {
        validateRoiName(roiName);
        validateColor(strokeColor);
        logger.trace("Deselect roi " + roiName);
        Roi r = roisMap.get(roiName);
        if (r != null) {
            if (r.getName() == roiName) {
                r.setStrokeColor(strokeColor);
                r.setSelected(false);
            }
        }
    }

    @Override
    public void deselectAll() {
        logger.trace("Deselect all");
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            if (r != null) {
                r.setSelected(false);
            }
        }
    }

    @Override
    public synchronized void deselectAllAndRedraw() {
        logger.trace("Deselect all and redraw all rois");
        deselectAll();
        drawAllRois();
    }

    @Override
    public synchronized Roi getRoiFromPoint(Point p) {
        validatePoint(p);
        logger.trace("Get roi contains point " + p);
        for (Integer key : roisMap.keySet()) {
            Roi roi = roisMap.get(key);
            if (roi != null && roi.contains(p)) {
                return roi;
            }
        }
        return null;
    }

    @Override
    public void removeRoi(int roiName) {
        validateRoiName(roiName);
        roisMap.remove(roiName);
    }

    private void validateRoiName(int roiName) {
        if (roiName == -1) {
            throw new IllegalArgumentException("Roi's name is not set");
        }
    }

    private void validateColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
    }

    private void validateRoi(Roi roi) {
        if (roi == null) {
            throw new IllegalArgumentException("Roi is null");
        }
        if (roi.getName() == -1) {
            throw new IllegalArgumentException("Roi's name is not set");
        }
        if (roi.getPolygon() == null) {
            throw new IllegalArgumentException("Roi's polygon is null");
        }
    }

    private void validatePoint(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
    }
}
