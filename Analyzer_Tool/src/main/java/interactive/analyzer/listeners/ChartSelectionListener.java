package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface ChartSelectionListener {

    public void singleBarSelectedEvent(double downRangeValue,
            double upperRangeValue,
            Color color);

    public void barSelectedEvent(double downRangeValue, double upperRangeValue,
            Color color);

    public void barDeselectedEvent(double downRangeValue,
            double upperRangeValue);

    public void clearBarSelectionsEvent();
}
