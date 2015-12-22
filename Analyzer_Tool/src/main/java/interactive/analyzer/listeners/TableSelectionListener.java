package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface TableSelectionListener {

    public void singleRowSelectedEvent(int rowIndex, double value,
            Color color);

    public void multipleRowsSelectedEvent(int rowIndex, double value,
            Color color);

    public void rowDeselectedEvent(int rowIndex);

    public void clearAllSelectionsEvent();
}
