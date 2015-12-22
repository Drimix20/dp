package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class OverlayManager {

    private static Logger logger = Logger.getLogger(OverlayManager.class);
    private Map<Integer, Roi> roisMap = new HashMap<>();
//    private List<Roi> rois;
    private ImagePlus img;
    private int strokeWidth = 1;

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
    }

    public void setStrokeWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("Width can not be smaller then 1");
        }
        strokeWidth = width;
    }

    public void drawRois() {
        logger.trace("");
        ImageProcessor ip = new ColorProcessor(img.getBufferedImage());
        ip.setLineWidth(strokeWidth);
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            if (r != null && r.stateChanged()) {
                Polygon polygon = r.getPolygon();
                drawName(r.getName() + "", polygon, ip);
                Color strokeColor = r.isSelected() ? r.getStrokeColor() : InteractiveImageWindow.DEFAULT_STROKE_ROI_COLOR;
                ip.setColor(strokeColor);
                ip.drawPolygon(polygon);
            }
        }
        img.setProcessor(ip);
        img.updateAndDraw();
    }

    private void drawName(String name, Polygon p, ImageProcessor ip) {
        validateDrawName(name, p, ip);
        FontMetrics fontMetrics = ip.getFontMetrics();
        int centerX = (int) p.getBounds().getCenterX() - fontMetrics.stringWidth(name) / 2;
        int centerY = (int) p.getBounds().getCenterY() + fontMetrics.getHeight() / 2;

        ip.setColor(Color.white);
        ip.drawString(name, centerX, centerY, Color.DARK_GRAY);
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

    public List<Roi> selectRoisInSelection(Polygon polygon,
            Color strokeColor) {
        if (polygon == null) {
            throw new IllegalArgumentException("Selection polygon is null");
        }
        validateColor(strokeColor);
        logger.trace("Select all rois in selection");
        List<Roi> selectedRois = new ArrayList<>();
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

    public void addRoiToSelection(Roi roi, Color strokeColor) {
        validateRoi(roi);
        validateColor(strokeColor);
        logger.trace("Add roi " + roi.getName() + " to selection");
        Roi r = roisMap.get(roi.getName());
        if (r != null) {
            r.setStrokeColor(strokeColor);
            if (!r.isSelected()) {
                r.setSelected(true);
            }
        }
    }

    public void addRoiToSelection(int roiName, Color strokeColor) {
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

    public void selectRoi(Roi roi, Color strokeColor) {
        validateRoi(roi);
        validateColor(strokeColor);
        logger.trace("Select roi " + roi.getName());
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            if (r.equals(roi)) {
                r.setStrokeColor(strokeColor);
                if (!r.isSelected()) {
                    r.setSelected(true);
                }
            } else {
                r.setSelected(false);
            }
        }
    }

    public void selectRoi(int roiName, Color strokeColor) {
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

    public void deselectRoi(int roiName, Color strokeColor) {
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

    public void deselectRoi(Roi roi) {
        validateRoi(roi);
        logger.trace("Deselect roi " + roi.getName());
        Roi r = roisMap.get(roi.getName());
        if (r != null) {
            if (r.equals(roi)) {
                roi.setStrokeColor(InteractiveImageWindow.DEFAULT_STROKE_ROI_COLOR);
            }
        }
    }

    public void deselectAll() {
        logger.trace("Deselect all");
        for (Integer key : roisMap.keySet()) {
            Roi r = roisMap.get(key);
            if (r != null) {
                r.setSelected(false);
            }
        }
    }

    public void deselectAllAndRedraw() {
        logger.trace("Deselect all and redraw all rois");
        deselectAll();
        drawRois();
    }

    public Roi getRoiFromPoint(Point p) {
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
