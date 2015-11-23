package interactive.analyzer.result.table;

import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerTableModel extends AbstractAfmTableModel {

    private Logger logger = Logger.getLogger(AfmAnalyzerTableModel.class);
    private String[] columnNames;
    private Object[][] data;

    public AfmAnalyzerTableModel(List<String> columnNames) {
        this.columnNames = columnNames.toArray(new String[columnNames.size()]);
        data = new Object[0][0];
    }

    public AfmAnalyzerTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        data = new Object[0][0];
    }

    public AfmAnalyzerTableModel() {
        columnNames = new String[0];
        data = new Object[0][0];
    }

    @Override
    public String getColumnName(int col) throws IllegalArgumentException {
        logger.debug("Get column name from " + col + " index.");
        if (col < 0 || col > columnNames.length - 1) {
            throw new IllegalArgumentException("Column index is out of range.");
        }
        return columnNames[col];
    }

    /**
     * List item is equal to measurement.
     * AbstractmeasurementResult contains measurement's result for each region of interest
     * @param values values to set into table
     */
    @Override
    public void setValues(List<AbstractMeasurementResult> values) {
        logger.debug("Set " + values.size() + " of rows");
        if (values.isEmpty()) {
            logger.trace("Data to set are empty");
            data = new Object[0][0];
            return;
        }

        //number of results per measurement + 1 for id column
        int columnNumber = values.size() + 1;
        List<Integer> roiKeys = values.get(0).getRoiKeys();
        int rowNumber = roiKeys.size();

        Object[][] dataTmp = new Object[rowNumber][columnNumber];
        for (int row = 0; row < rowNumber; row++) {
            Integer roiKey = roiKeys.get(row);
            for (int col = 0; col < columnNumber; col++) {
                logger.trace("Set value to " + row + " row index and " + col + " column index for " + roiKey + " roi key");
                if (col == 0) {
                    //set roi id
                    dataTmp[row][col] = roiKey;
                } else {
                    //set measurement's result
                    dataTmp[row][col] = values.get(col - 1).getResultForRoiKey(roiKey);
                }
            }
        }

        data = dataTmp;
        fireTableDataChanged();
    }

    @Override
    public void setValues(Object[][] values) {
        logger.debug("Set table values with rows " + values.length);
        this.data = values;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        logger.debug("Row count " + data.length);
        return data.length;
    }

    @Override
    public int getColumnCount() {
        logger.debug("Column count " + columnNames.length);
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        logger.trace("Get value from row " + rowIndex + " and column " + columnIndex);

        if (rowIndex < 0 || rowIndex > data.length || columnIndex < 0 || columnIndex > columnNames.length) {
            throw new IllegalArgumentException("Row index or column index is out of range.");
        }

        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        logger.trace("Set value " + value + " to row " + row + " and col " + column);

        if (row < 0 || row > data.length || column < 0 || column > columnNames.length) {
            throw new IllegalArgumentException("Row index or column index is out of range.");
        }
        data[row][column] = value;
        fireTableCellUpdated(row, column);
    }

}