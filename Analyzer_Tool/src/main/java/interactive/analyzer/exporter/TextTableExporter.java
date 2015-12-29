package interactive.analyzer.exporter;

import ij.IJ;
import interactive.analyzer.result.table.TableColorSelectionManager;
import interactive.analyzer.selection.TagManager;
import javax.swing.JTable;

/**
 *
 * @author Drimal
 */
public class TextTableExporter {

    public void export(JTable table, TagManager tagManager,
            TableColorSelectionManager colorSelectionManager) {
        StringBuilder sb = exportTableHeader(table);
        sb.append('\n');
        sb = exportTableData(sb, table);

        IJ.saveString(sb.toString(), null);
    }

    protected StringBuilder exportTableData(StringBuilder sb,
            JTable table) {
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                sb.append(table.getModel().getValueAt(row, col));
                if (col < columnCount - 1) {
                    sb.append(',');
                }
            }
            sb.append('\n');
        }

        return sb;
    }

    private StringBuilder exportTableHeader(JTable table) {
        StringBuilder sb = new StringBuilder();
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
