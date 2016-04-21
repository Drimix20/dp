package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface TableSelectionListener {

    public void singleRowSelectedEvent(int roiId, double value,
            Color color);

    public void multipleRowsSelectedEvent(int roiId, double value,
            Color color);

    public void rowDeselectedEvent(int roiId);

    public void redrawAllEvent();

    public void clearAllSelectionsEvent();
}
