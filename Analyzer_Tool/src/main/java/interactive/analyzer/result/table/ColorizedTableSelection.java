package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public class ColorizedTableSelection {

    private Set<Integer> rows;
    private Color color;

    public ColorizedTableSelection(Color color) {
        rows = new HashSet<>();
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColorizedTableSelection other = (ColorizedTableSelection) obj;
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

}
