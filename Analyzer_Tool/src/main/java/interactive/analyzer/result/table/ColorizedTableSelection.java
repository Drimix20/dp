package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class ColorizedTableSelection {

    private List<Integer> rows;
    private Color color;

    public ColorizedTableSelection(Color color) {
        rows = new ArrayList<>();
        this.color = color;
    }

    /**
     * Get color of this selection
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Add row index to this selection
     * @param row row index
     * @return true if row was added into this selection
     */
    public boolean addRow(int row) {
        return rows.add(row);
    }

    /**
     * Remove row index from this selection
     * @param row row index
     * @return true if row was removed otherwise false
     */
    public boolean removeRow(int row) {
        return rows.remove((Integer) row);
    }

    /**
     * Delete all rows in this selection
     */
    public void clearRows() {
        rows.clear();
    }

    /**
     * Check if row is in selection
     * @param row row index
     * @return true if row is in selection otherwise return false
     */
    public boolean containsRow(int row) {
        for (Integer rowIndex : rows) {
            if (rowIndex == row) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return selection color for specific row
     * @param row row index
     * @return color for selected row or null if row is not in selection
     */
    public Color getColorForRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("Row is negative");
        }

        if (containsRow(row)) {
            return color;
        }

        return null;
    }
}
