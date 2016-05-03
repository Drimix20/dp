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
    private Color currentSelectionColor = Color.red;

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

    public synchronized void removeRowFromSelection(int row) {
        rows.remove(row);
    }

    public synchronized void removeRowsFromSelection(int... row) {
        for (int r : row) {
            rows.remove(r);
        }
    }

    public synchronized void clearAllSelections() {
        rows.clear();
    }

}
