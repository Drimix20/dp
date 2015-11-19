package interactive.analyzer.result.table;

import ij.measure.ResultsTable;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class TableUtils {

    private static Logger logger = Logger.getLogger(TableUtils.class);

    public static AfmAnalyzerTableModel convertResultTableToInteractiveResultTable(
            ResultsTable resultsTable) {

        String[] headings = resultsTable.getHeadings();
        int rowCounter = resultsTable.getCounter();
        int columnCounter = resultsTable.getLastColumn() + 1;

        Object[][] data = new Object[rowCounter][columnCounter];
        for (int row = 0; row < rowCounter; row++) {
            String rowLabel = resultsTable.getLabel(row);
            for (int col = 0; col < columnCounter; col++) {
                data[row][col] = resultsTable.getValue(headings[col], row);
            }
        }

        AfmAnalyzerTableModel tableModel = new AfmAnalyzerTableModel(headings);
        tableModel.setValues(data);

        return tableModel;
    }
}
