package interactive.analyzer.presenter;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Drimal
 */
public interface IOverlayManager {

    /**
     * Add roi to current selection. This method is used when manager is making multiple selection with specified selection color
     * @param roi roi to add to selection
     * @param strokeColor selection color
     */
    void addRoiToSelection(Roi roi, Color strokeColor);

    /**
     * Add roi to current selection. This method is used when manager is making multiple selection with specified selection color
     * @param roiName roi to add to selection
     * @param strokeColor selection color
     */
    void addRoiToSelection(int roiName, Color strokeColor);

    /**
     Set all rois to deselected state
     */
    void deselectAll();

    /**
     * Deselect all rois in image and redraw image
     */
    void deselectAllAndRedraw();

    /**
     * Deselect specified roi
     * @param roiName
     * @param strokeColor
     */
    void deselectRoi(int roiName, Color strokeColor);

    /**
     * Deselect specified roi
     * @param roi roi to deselect
     */
    void deselectRoi(Roi roi);

    /**
     * Draw all rois
     */
    void drawAllRois();

    /**
     Draw only specified roi
     @param roiId id of roi
     */
    void drawRoi(final int roiId);

    /**
     * Retrieve roi from image on mouse click. If there is no roi then return null.
     * @param p
     * @return roi or null
     */
    Roi getRoiFromPoint(Point p);

    /**
     * Make specified roi as selected and draw it with defined color
     * @param roi roi which is selected
     * @param strokeColor color of selection
     */
    void selectRoi(Roi roi, Color strokeColor);

    /**
     * Make specified roi as selected and draw it with defined color
     * @param roiName roi which is selected
     * @param strokeColor color of roi
     */
    void selectRoi(int roiName, Color strokeColor);

    /**
     * Select all rois which are located in selection polygon
     * @param polygon selection polygon
     * @param strokeColor color of selection
     * @return return all rois located in selection polygon
     */
    List<Roi> selectRoisInSelection(Polygon polygon, Color strokeColor);

    /*
     * Remove specified roi from selection.
     */
    void removeRoi(int roiName);

    /**
     * Return all ROIs in image as unmodifiable collection
     * @return rois
     */
    Collection<Roi> getAllRois();

}
