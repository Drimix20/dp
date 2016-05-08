package interactive.analyzer.exporter;

import interactive.analyzer.result.table.TableColorSelectionManager;
import interactive.analyzer.selection.TagManager;
import javax.swing.JTable;

/**
 *
 * @author Drimal
 */
public interface ITableExporter {

    /**
     * Export current table
     * @param imageName name of image
     * @param table table data
     * @param tagManager tags in table
     * @param colorSelectionManager selections in table
     */
    void export(String imageName, JTable table, TagManager tagManager,
            TableColorSelectionManager colorSelectionManager);

}
