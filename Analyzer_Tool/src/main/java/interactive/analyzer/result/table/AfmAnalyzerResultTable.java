package interactive.analyzer.result.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerResultTable extends JTable {

    //TODO implement funcionality to changing header text(see http://codewizpt.blogspot.cz/2011/03/change-jtable-column-header-text.html)
    //TODO implement functionality for set tooltip headers
    //TODO maybe will be needed List<MeasurementResult>
    private Logger logger = Logger.getLogger(AfmAnalyzerResultTable.class);

    private List<String> headerTooltips = new ArrayList<>();
    private List<ColorizedTableSelection> tableSelectionList = new ArrayList<>();
    private static final Color DEFAULT_BACKGROUND_ROW_COLOR = Color.WHITE;
    private static final Color DEFAULT_SELECTION_COLOR = new Color(187, 207, 229);

    public List<String> getTooltips() {
        return headerTooltips;
    }

    public void setHeaderTooltips(List<String> headerTooltips) {
        this.headerTooltips = headerTooltips;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        return super.getToolTipText(e);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                //String tip = null;
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return headerTooltips.get(realIndex);
            }
        };
    }

    public boolean addRowToColorSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        rowBounds(row);

        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.getColor().equals(color)) {
                return ts.addRow(row);
            }
        }
        //TableSelection does not exists. New one is created and added into tableSelectionList
        ColorizedTableSelection tableSelection = new ColorizedTableSelection(color);
        tableSelection.addRow(row);

        return tableSelectionList.add(tableSelection);
    }

    public boolean addRowsToColorSelection(Color color, int... row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.getColor().equals(color)) {
                for (int r : row) {
                    return ts.addRow(r);
                }
            }
        }
        //TableSelection does not exists. New one is created and added into tableSelectionList
        ColorizedTableSelection tableSelection = new ColorizedTableSelection(color);
        for (int r : row) {
            tableSelection.addRow(r);
        }

        return tableSelectionList.add(tableSelection);
    }

    public void removeRowFromSelection(int row) {
        rowBounds(row);

        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.containsRow(row)) {
                ts.removeRow(row);
            }
        }
    }

    public void removeRowFromColorSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        rowBounds(row);

        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.getColor().equals(color)) {
                ts.removeRow(row);
            }
        }
    }

    public void removeRowsFromColorSelection(Color color, int... row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.getColor().equals(color)) {
                for (int r : row) {
                    ts.removeRow(r);
                }
            }
        }
    }

    @Override
    public void clearSelection() {
        tableSelectionList = new ArrayList<>();
        super.clearSelection();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {
        //Get component for current row
        Component comp = super.prepareRenderer(renderer, row, column);
        Color background = comp.getBackground();
        logger.trace(background);

        Color selectionColor = null;
        for (ColorizedTableSelection ts : tableSelectionList) {
            if (ts.containsRow(row)) {
                selectionColor = ts.getColorForRow(row);
            }
        }
        if (selectionColor == null && isRowSelected(row)) {
            selectionColor = DEFAULT_SELECTION_COLOR;
        }
        if (selectionColor == null) {
            selectionColor = DEFAULT_BACKGROUND_ROW_COLOR;
        }
        comp.setBackground(selectionColor);
        return comp;
    }

    private void rowBounds(int row) throws IllegalArgumentException {
        if (row < 0 || row > (getRowCount() - 1)) {
            throw new IllegalArgumentException("Row index is out of range");
        }
    }

}
