package interactive.analyzer.exporter;

import ij.IJ;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.TableColorSelectionManager;
import interactive.analyzer.selection.Tag;
import interactive.analyzer.selection.TagManager;
import java.awt.Color;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Drimal
 */
public class TextTableExporter implements ITableExporter {

    private static final char COLUMN_DELIMETR = ',';
    private static final char NEW_LINE = '\n';

    @Override
    public void export(String imageName, JTable table, TagManager tagManager,
            TableColorSelectionManager colorSelectionManager) {
        StringBuilder sb = new StringBuilder("Results for " + imageName + " image:");
        sb = exportTableHeader(sb, table);
        sb.append('\n');
        sb = exportTableData(sb, table, colorSelectionManager);
        //sb = exportSelectionTag(sb, tagManager);

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

    protected StringBuilder exportSelectionTag(StringBuilder sb,
            TagManager manager) {
        List<Tag> tags = manager.getTags();
        sb.append(NEW_LINE).append("Selection tags:\n");
        sb.append("id")
                .append(COLUMN_DELIMETR).append("color")
                .append(COLUMN_DELIMETR).append("name")
                .append(COLUMN_DELIMETR).append("description").append(NEW_LINE);

        for (Tag tag : tags) {
            Color c = tag.getColor();
            sb.append(tag.getId()).append(COLUMN_DELIMETR).append(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()))
                    .
                    append(COLUMN_DELIMETR).append(tag.getName()).append(COLUMN_DELIMETR)
                    .append(tag.getDescription()).append(NEW_LINE);
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
