package interactive.analyzer.result.table;

import interactive.analyzer.options.ResultTableConfiguration;
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

    private static Logger logger = Logger.getLogger(AfmAnalyzerResultTable.class);
    private static int ID_COLUMN_INDEX = 0;
    private static int SELECTION_COLUMN_INDEX = 1;

    private List<String> headerTooltips = new ArrayList<>();
    private TableColorSelectionManager selectionManager;
    private static final Color DEFAULT_BACKGROUND_ROW_COLOR = Color.WHITE;
    //TODO configure default selection color
    private static final Color DEFAULT_SELECTION_COLOR = Color.red;//new Color(187, 207, 229);

    public AfmAnalyzerResultTable() {
        selectionManager = TableColorSelectionManager.getInstance();
    }

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
                if (headerTooltips.isEmpty()) {
                    return "";
                }
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
        selectionManager.addRowToColorSelection(color, row);

        return true;
    }

    public boolean addRowsToColorSelection(Color color, int... rows) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        selectionManager.addRowsToColorSelection(color, rows);

        return true;
    }

    public void removeRowFromSelection(int row) {
        rowBounds(row);
        selectionManager.removeRowFromSelection(row);
    }

    public void removeRowFromColorSelection(Color color, int row) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        rowBounds(row);
        selectionManager.removeRowFromColorSelection(color, row);
    }

    public void removeRowsFromColorSelection(Color color, int... rows) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        selectionManager.removeRowsFromColorSelection(color, rows);
    }

    @Override
    public void clearSelection() {
        logger.trace("");
        if (selectionManager != null) {
            selectionManager.deleteAllSelections();
        }
        super.clearSelection();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {
        //Get component for current row
        Component comp = super.prepareRenderer(renderer, row, column);

        if (ResultTableConfiguration.colorEntireRow()) {
            colorRow(row, comp);
        } else if (column == SELECTION_COLUMN_INDEX) {
            colorRow(row, comp);
        } else {
            comp.setBackground(Color.white);
        }

        return comp;
    }

    private void colorRow(int row, Component comp) {
        Color selectionColor = null;
        //row can be in selected range
        if (selectionManager != null) {
            selectionColor = selectionManager.getColorForRow(row);
        }
        if (selectionColor == null && isRowSelected(row)) {
            //selection performed by click on table row
            selectionColor = DEFAULT_SELECTION_COLOR;
        }
        if (selectionColor == null) {
            //row is not selected
            selectionColor = DEFAULT_BACKGROUND_ROW_COLOR;
        }
        comp.setBackground(selectionColor);
    }

    private void rowBounds(int row) throws IllegalArgumentException {
        if (row < 0 || row > (getRowCount() - 1)) {
            throw new IllegalArgumentException("Row index is out of range");
        }
    }

}
