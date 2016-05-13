package interactive.analyzer.exporter;

import interactive.analyzer.result.table.TableColorSelectionManager;
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
     * @param colorSelectionManager selections in table
     */
    void export(String imageName, JTable table,
            TableColorSelectionManager colorSelectionManager);

}
