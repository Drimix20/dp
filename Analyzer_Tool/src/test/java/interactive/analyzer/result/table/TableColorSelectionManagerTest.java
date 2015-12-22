package interactive.analyzer.result.table;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class TableColorSelectionManagerTest {

    Map<Color, ColorizedTableSelection> colorSelectionMap;
    private TableColorSelectionManager manager;

    @Test
    public void testGetSelectionFromMap() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        ColorizedTableSelection selectionFromMap = manager.getSelectionFromMap(3);
        assertEquals(cts, selectionFromMap);
    }

    @Test
    public void testAddRowToColorSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.addRowToColorSelection(Color.red, 2);
        assertTrue("Map should contains selection with row 2", manager.getColorSelectionMap().get(Color.red).containsRow(2));
    }

    @Test
    public void testAddRowsToColorSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.addRowsToColorSelection(Color.red, 2, 4);
        assertTrue("Map should contains selection with row 2", manager.getColorSelectionMap().get(Color.red).containsRow(2));
        assertTrue("Map should contains selection with row 4", manager.getColorSelectionMap().get(Color.red).containsRow(4));
    }

    @Test
    public void testRemoveRowFromSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.removeRowFromSelection(2);
        assertTrue("Map should contains selection with row 1", manager.getColorSelectionMap().get(Color.red).containsRow(1));
        assertFalse("Map should not contains selection with row 2", manager.getColorSelectionMap().get(Color.red).containsRow(2));
        assertTrue("Map should contains selection with row 3", manager.getColorSelectionMap().get(Color.red).containsRow(3));
    }

    @Test
    public void testRemoveNonetExistsRowFromSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.removeRowFromSelection(2);
        assertTrue("Map should contains selection with row 1", manager.getColorSelectionMap().get(Color.red).containsRow(1));
        assertTrue("Map should contains selection with row 3", manager.getColorSelectionMap().get(Color.red).containsRow(3));
    }

    @Test
    public void testRemoveRowFromColorSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.removeRowFromColorSelection(Color.red, 2);
        assertTrue("Map should contains selection with row 1", manager.getColorSelectionMap().get(Color.red).containsRow(1));
        assertFalse("Map should not contains selection with row 2", manager.getColorSelectionMap().get(Color.red).containsRow(2));
        assertTrue("Map should contains selection with row 3", manager.getColorSelectionMap().get(Color.red).containsRow(3));
    }

    @Test
    public void testRemoveRowsFromColorSelection() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.removeRowsFromColorSelection(Color.red, 2, 3);
        assertTrue("Map should contains selection with row 1", manager.getColorSelectionMap().get(Color.red).containsRow(1));
        assertFalse("Map should not contains selection with row 2", manager.getColorSelectionMap().get(Color.red).containsRow(2));
        assertFalse("Map should contains selection with row 3", manager.getColorSelectionMap().get(Color.red).containsRow(3));
    }

    @Test
    public void testDeleteAllSelections() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        manager.deleteAllSelections();
        assertEquals("Map should be empty", 0, manager.getColorSelectionMap().size());
    }

    @Test
    public void testGetColorForRow() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(1);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        Color colorForRow = manager.getColorForRow(3);
        assertEquals(Color.red, colorForRow);
    }

    @Test
    public void testGetNonExistsColorForRow() {
        colorSelectionMap = new HashMap<>();
        ColorizedTableSelection cts = new ColorizedTableSelection(Color.red);
        cts.addRow(2);
        cts.addRow(3);
        colorSelectionMap.put(Color.red, cts);
        manager = new TableColorSelectionManager();
        manager.setColorSelectionMap(colorSelectionMap);

        Color colorForRow = manager.getColorForRow(1);
        assertEquals(null, colorForRow);
    }
}
