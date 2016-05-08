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
    private volatile Set<Integer> objectIdSet;
    private volatile Color currentSelectionColor = Color.red;

    private TableColorSelectionManager() {
        objectIdSet = new HashSet<Integer>();
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

    public Set<Integer> getObjectsInSelection() {
        return objectIdSet;
    }

    public Color getCurrentSelectionColor() {
        return currentSelectionColor;
    }

    public boolean isObjectInSelection(int objectId) {
        return objectIdSet.contains(objectId);
    }

    public synchronized void setCurrentSelectionColor(
            Color currentSelectionColor) {
        this.currentSelectionColor = currentSelectionColor;
    }

    public synchronized boolean addObjectToSelection(Color color, int objectId) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        currentSelectionColor = color;
        return objectIdSet.add(objectId);
    }

    /**
     * Add row to current selection
     * @param color
     * @param objectIds
     * @return
     */
    public synchronized boolean addObjectsToSelection(Color color,
            int... objectIds) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        currentSelectionColor = color;
        for (int id : objectIds) {
            objectIdSet.add(id);
        }

        return true;
    }

    /**
     * Remove row from current selection
     @param objectId index of removing row
     */
    public synchronized void removeObjectFromSelection(int objectId) {
        objectIdSet.remove(objectId);
    }

    /**
     * Remove all rows from current selection
     */
    public synchronized void clearAllSelections() {
        objectIdSet.clear();
    }

}
