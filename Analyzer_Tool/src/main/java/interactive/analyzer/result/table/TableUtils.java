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

        String[] headings = mergeArrays(new String[]{"id"}, resultsTable.getHeadings());
        int rowCounter = resultsTable.getCounter();
        int columnCounter = resultsTable.getLastColumn() + 2;

        Object[][] data = new Object[rowCounter][columnCounter];
        for (int row = 0; row < rowCounter; row++) {
            String rowLabel = resultsTable.getLabel(row);
            for (int col = 0; col < columnCounter; col++) {
                if (col == 0) {
                    data[row][col] = row + 1;
                } else {
                    data[row][col] = resultsTable.getValue(headings[col], row);
                }
            }
        }

        AfmAnalyzerTableModel tableModel = new AfmAnalyzerTableModel(headings);
        tableModel.setValues(data);

        return tableModel;
    }

    private static String[] mergeArrays(String[] first, String[] second) {
        int fullLength = first.length + second.length;
        String[] newArray = new String[fullLength];
        for (int i = 0; i < first.length; i++) {
            newArray[i] = first[i];
        }
        for (int i = 0; i < second.length; i++) {
            newArray[i + first.length] = second[i];
        }
        return newArray;
    }
}
