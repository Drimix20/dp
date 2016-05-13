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
    public static int ID_COLUMN_INDEX = 0;
    public static int SELECTION_COLUMN_INDEX = 1;

    private List<String> headerTooltips = new ArrayList<String>();
    private TableColorSelectionManager selectionManager;
    private static final Color DEFAULT_BACKGROUND_ROW_COLOR = Color.WHITE;

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

    /**
     *
     * @param color
     * @param objectId
     * @return
     */
    public boolean addObjectToColorSelection(Color color, int objectId) {
        logger.info("row: " + objectId);
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
        return selectionManager.addObjectToSelection(color, objectId);
    }

    /**
     * Remove specified object from current selection
     * @param objectId
     */
    public void removeObjectFromSelection(int objectId) {
        selectionManager.removeObjectFromSelection(objectId);
    }

    @Override
    public void clearSelection() {
        logger.trace("");
        if (selectionManager != null) {
            selectionManager.clearAllSelections();
        }
        super.clearSelection();
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {
        //Get component for current row
        Component comp = super.prepareRenderer(renderer, row, column);

        if (ResultTableConfiguration.colorEntireRow()) {
            colorComponent(row, comp);
        } else if (column == SELECTION_COLUMN_INDEX) {
            colorComponent(row, comp);
        } else {
            //Draw all others component as background color
            comp.setBackground(Color.white);
        }

        return comp;
    }

    private void colorComponent(int row, Component comp) {
        Color selectionColor;
        //Check if row is set of selected rows
        int objectID = (Integer) getValueAt(row, AfmAnalyzerResultTable.ID_COLUMN_INDEX);
        if (isRowSelected(row) && selectionManager.isObjectInSelection(objectID)) {
            //selection performed by click on table row
            logger.trace("Selected row - coloring row " + row);
            selectionColor = selectionManager.getCurrentSelectionColor();
        } else {
            //row is not selecteds
            selectionColor = DEFAULT_BACKGROUND_ROW_COLOR;
        }
        comp.setBackground(selectionColor);
    }
}
