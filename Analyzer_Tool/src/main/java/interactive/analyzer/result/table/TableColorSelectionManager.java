package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class TableColorSelectionManager {

    private Map<Color, ColorizedTableSelection> colorSelectionMap = new HashMap<>();

    public TableColorSelectionManager() {
        colorSelectionMap = new HashMap<>();
    }

    public void setColorSelectionMap(
            Map<Color, ColorizedTableSelection> colorSelectionMap) {
        this.colorSelectionMap = colorSelectionMap;
    }

    public Map<Color, ColorizedTableSelection> getColorSelectionMap() {
        return colorSelectionMap;
    }

    public boolean addRowToColorSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        ColorizedTableSelection cts = colorSelectionMap.get(color);
        if (cts == null) {
            //TableSelection does not exists. New one is created and added into map
            ColorizedTableSelection tableSelection = new ColorizedTableSelection(color);
            tableSelection.addRow(row);
            colorSelectionMap.put(color, tableSelection);
        } else {
            cts.addRow(row);
        }

        return true;
    }

    public boolean addRowsToColorSelection(Color color, int... row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        ColorizedTableSelection cts = colorSelectionMap.get(color);
        if (cts == null) {
            //TableSelection does not exists. New one is created and added into map
            cts = new ColorizedTableSelection(color);
        }

        for (int r : row) {
            cts.addRow(r);
        }
        colorSelectionMap.put(color, cts);

        return true;
    }

    public void removeRowFromSelection(int row) {
        ColorizedTableSelection cts = getSelectionFromMap(row);
        if (cts != null) {
            cts.removeRow(row);
        }
    }

    public void removeRowFromColorSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        ColorizedTableSelection cts = colorSelectionMap.get(color);
        if (cts != null) {
            cts.removeRow(row);
        }
    }

    public void removeRowsFromColorSelection(Color color, int... row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }

        ColorizedTableSelection cts = colorSelectionMap.get(color);
        if (cts != null) {
            for (int r : row) {
                cts.removeRow(r);
            }
        }
    }

    public void deleteAllSelections() {
        colorSelectionMap = new HashMap<>();
    }

    public Color getColorForRow(int row) {
        ColorizedTableSelection cts = getSelectionFromMap(row);
        if (cts != null) {
            return cts.getColor();
        }
        return null;
    }

    public ColorizedTableSelection getSelectionFromMap(int row) {
        for (Color c : colorSelectionMap.keySet()) {
            ColorizedTableSelection cts = colorSelectionMap.get(c);
            if (cts != null && cts.containsRow(row)) {
                return cts;
            }
        }

        return null;
    }

}
