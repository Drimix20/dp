package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class TableColorSelectionManager {

    private static final Logger logger = Logger.getLogger(TableColorSelectionManager.class);
    private static TableColorSelectionManager instance = null;
    private volatile Set<Integer> objectIdSet;
    private volatile Color currentSelectionColor = Color.red;

    private TableColorSelectionManager() {
        objectIdSet = new HashSet<Integer>();
    }

    /**
     * Return current instance of selection manager
     * @return instance
     */
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

    public synchronized Set<Integer> getObjectsInSelection() {
        Set<Integer> set = new HashSet<Integer>();
        for (Integer i : objectIdSet) {
            set.add(new Integer(i));
        }
        return set;
    }

    public synchronized Color getCurrentSelectionColor() {
        return currentSelectionColor;
    }

    public boolean isObjectInSelection(int objectId) {
        logger.trace(objectId);
        return objectIdSet.contains(objectId);
    }

    public synchronized void setCurrentSelectionColor(
            Color currentSelectionColor) {
        this.currentSelectionColor = currentSelectionColor;
    }

    public synchronized boolean addObjectToSelection(Color color, int objectId) {
        logger.trace("Color " + color + ", id " + objectId);
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
        logger.trace("Color " + color + ", objects " + objectIds.length);
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
        logger.trace(objectId);
        objectIdSet.remove(objectId);
    }

    /**
     * Remove all rows from current selection
     */
    public synchronized void clearAllSelections() {
        logger.trace("");
        objectIdSet.clear();
    }

}
