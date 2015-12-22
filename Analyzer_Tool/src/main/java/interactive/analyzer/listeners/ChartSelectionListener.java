package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface ChartSelectionListener {

    public void notifySingleBarSelected(double downRangeValue,
            double upperRangeValue,
            Color color);

    public void notifyBarSelected(double downRangeValue, double upperRangeValue,
            Color color);

    public void notifyBarDeselected(double downRangeValue,
            double upperRangeValue);

    public void notifyClearBarSelections();
}
