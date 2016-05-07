package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public class TableColorSelectionManager {

    private static TableColorSelectionManager instance = null;
    private volatile Set<Integer> rows;
    private volatile Color currentSelectionColor = Color.red;

    private TableColorSelectionManager() {
        rows = new HashSet<Integer>();
    }

    public synchronized static TableColorSelectionManager getInstance() {
        if (instance == null) {
            synchronized (TableColorSelectionManager.class) {
                if (instance == null) {
                    instance = new TableColorSelectionManager();
                }
            }
        }
        return instance;
    }

    public Set<Integer> getRowIndexesInSelection() {
        return rows;
    }

    public Color getCurrentSelectionColor() {
        return currentSelectionColor;
    }

    public boolean isRowInSelection(int row) {
        return rows.contains(row);
    }

    public synchronized void setCurrentSelectionColor(
            Color currentSelectionColor) {
        this.currentSelectionColor = currentSelectionColor;
    }

    public synchronized boolean addRowToSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        currentSelectionColor = color;
        return rows.add(row);
    }

    /**
     * Add row to current selection
     * @param color
     * @param row
     * @return
     */
    public synchronized boolean addRowsToSelection(Color color, int... row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        currentSelectionColor = color;
        for (int r : row) {
            rows.add(r);
        }

        return true;
    }

    /**
     * Remove row from current selection
     @param row index of removing row
     */
    public synchronized void removeRowFromSelection(int row) {
        rows.remove(row);
    }

    /**
     * Remove all rows from current selection
     */
    public synchronized void clearAllSelections() {
        rows.clear();
    }

}
