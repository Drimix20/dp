package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface TableSelectionListener {

    public void selectedSingleRow(int rowIndex, double value,
            Color color);

    public void selectedMultipleRows(int rowIndex, double value,
            Color color);

    public void deselectedRow(int rowIndex);

    public void clearAllSelections();
}
