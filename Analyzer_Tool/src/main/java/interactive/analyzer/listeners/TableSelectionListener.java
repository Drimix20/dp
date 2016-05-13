package interactive.analyzer.listeners;

import java.awt.Color;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public interface TableSelectionListener {

    /**
     * Event that only one row with specified object was selected.
     * All previous selection must be cleared and deleted.
     * @param roiId id of object in row
     * @param value value of specific object. Depends on selected column
     * @param color current selection color
     */
    public void singleRowSelectedEvent(int roiId, double value, Color color);

    /**
     * Event that this row with specified object was added to current selection.
     * Previous selection mustn't be cleared or deleted.
     * @param roiId id of object in row
     * @param value value of specific object. Depends on selected column
     * @param color current selection color
     */
    public void multipleRowsSelectedEvent(int roiId, double value, Color color);

    /**
     * Event that row with specified object was deselected.
     * @param roiId if of object in row
     */
    public void rowDeselectedEvent(int roiId);

    /**
     * Event that all selections should be repainted.
     */
    public void repaintAllEvent();

    /**
     * Event that all selections should be cleared.
     */
    public void clearAllSelectionsEvent();

    /**
     * Event that objects are deleted from container.
     * @param roiIds set of objects which are deleted
     */
    public void removeRoisEvent(Set<Integer> roiIds);
}
