package interactive.analyzer.exporter;

import ij.IJ;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.TableColorSelectionManager;
import java.awt.Color;
import javax.swing.JTable;

/**
 *
 * @author Drimal
 */
public class TextTableExporter implements ITableExporter {

    private static final char COLUMN_DELIMETR = ',';
    private static final char NEW_LINE = '\n';

    @Override
    public void export(String imageName, JTable table,
            TableColorSelectionManager colorSelectionManager) {
        StringBuilder sb = new StringBuilder("Results for " + imageName + " image:");
        sb = exportTableHeader(sb, table);
        sb.append('\n');
        sb = exportTableData(sb, table, colorSelectionManager);

        IJ.saveString(sb.toString(), null);
    }

    /**
     * Export table data to String Builder. If rows are in current selection then add color information
     * @param sb string builder
     * @param table table to export
     * @param selectionManager current selection in table
     * @return transformed table into string
     */
    protected StringBuilder exportTableData(StringBuilder sb, JTable table,
            TableColorSelectionManager selectionManager) {
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (col == AfmAnalyzerResultTable.SELECTION_COLUMN_INDEX) {
                    if (selectionManager.isObjectInSelection(row)) {
                        Color c = selectionManager.getCurrentSelectionColor();
                        sb.append(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
                    }
                } else {
                    sb.append(table.getModel().getValueAt(row, col));
                }
                if (col < columnCount - 1) {
                    sb.append(COLUMN_DELIMETR);
                }
            }
            sb.append(NEW_LINE);
        }

        return sb;
    }

    private StringBuilder exportTableHeader(StringBuilder sb, JTable table) {
        int columnCount = table.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            sb.append(table.getColumnModel().getColumn(i).getHeaderValue());
            if (i < columnCount - 1) {
                sb.append(',');
            }
        }

        return sb;
    }
}
