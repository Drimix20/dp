package interactive.analyzer.presenter;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Polygon;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class OverlayManager {

    private List<Roi> rois;
    private ImagePlus img;
    private int strokeWidth = 1;

    public OverlayManager(List<Roi> rois, ImagePlus img) {
        this.rois = rois;
        this.img = img;
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    public void drawRois() {
        ImageProcessor ip = new ColorProcessor(img.getBufferedImage());
        ip.setLineWidth(strokeWidth);
        for (Roi r : rois) {
            Polygon polygon = r.getPolygon();
            drawName(r.getName() + "", polygon, ip);
            Color strokeColor = r.isSelected() ? r.getStrokeColor() : InteractiveImageWindow.DEFAULT_STROKE_ROI_COLOR;
            ip.setColor(strokeColor);
            ip.drawPolygon(polygon);
        }
        img.setProcessor(ip);
        img.updateAndDraw();
    }

    private void drawName(String name, Polygon p, ImageProcessor ip) {
        FontMetrics fontMetrics = ip.getFontMetrics();
        int centerX = (int) p.getBounds().getCenterX() - fontMetrics.stringWidth(name) / 2;
        int centerY = (int) p.getBounds().getCenterY() + fontMetrics.getHeight() / 2;

        ip.setColor(Color.white);
        ip.drawString(name, centerX, centerY, Color.DARK_GRAY);
    }

    public List<Roi> selectRoisInSelection(Roi selectionRoi, Color strokeColor) {
//        List<Roi> selectedRois = new ArrayList<>();
//        for (Roi r : rois) {
//            //Check if selectionRoi polygon intersects with someone roi in list
//            if (selectionRoi.getPolygon().intersects(r.getBounds())) {
//                r.setStrokeColor(strokeColor);
//                selectedRois.add(r);
//            }
//        }
//        return selectedRois;
        return Collections.EMPTY_LIST;
    }

    public void addRoiToSelection(Roi roi, Color strokeColor) {
        for (Roi r : rois) {
            if (r.equals(roi)) {
                r.setStrokeColor(strokeColor);
                r.setSelected(true);
            }
        }
        drawRois();
    }

    public void selectRoi(Roi roi, Color strokeColor) {
        for (Roi r : rois) {
            if (r.equals(roi)) {
                r.setStrokeColor(strokeColor);
                r.setSelected(true);
            } else {
                r.setSelected(false);
            }
        }
        drawRois();
    }

    public void addRoiToSelection(int roiName, Color strokeColor) {
        for (Roi r : rois) {
            if (r.getName() == roiName) {
                r.setStrokeColor(strokeColor);
                r.setSelected(true);
            }
        }
        drawRois();
    }

    public void selectRoi(int roiName, Color strokeColor) {
        for (Roi r : rois) {
            if (r.getName() == roiName) {
                r.setStrokeColor(strokeColor);
                r.setSelected(true);
            } else {
                r.setSelected(false);
            }
        }
        drawRois();
    }

    public void deselectRoi(int roiName, Color strokeColor) {
        for (Roi r : rois) {
            if (r.getName() == roiName) {
                r.setStrokeColor(strokeColor);
                r.setSelected(false);
            }
        }
        drawRois();
    }

    public void deselectRoi(Roi roi) {
//        for (Roi r : rois) {
//            if (r.equals(roi)) {
//                roi.setStrokeColor(InteractiveImageWindow.DEFAULT_STROKE_ROI_COLOR);
//            }
//        }
    }

    public void deselectAll() {
        for (Roi roi : rois) {
            roi.setSelected(false);
        }
        drawRois();
    }
}
