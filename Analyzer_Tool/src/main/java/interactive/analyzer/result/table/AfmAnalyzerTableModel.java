package interactive.analyzer.result.table;

import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerTableModel extends AbstractAfmTableModel {

    private Logger logger = Logger.getLogger(AfmAnalyzerTableModel.class);

    public AfmAnalyzerTableModel(List<String> columnNames) {
        super();
        this.columnNames = columnNames.toArray(new String[columnNames.size()]);
    }

    public AfmAnalyzerTableModel(String[] columnNames) {
        super();
        this.columnNames = columnNames;
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
        logger.trace("Row count " + data.length);
        return data.length;
    }

    @Override
    public int getColumnCount() {
        logger.trace("Column count " + columnNames.length);
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        logger.trace("Get value from row " + rowIndex + " and column " + columnIndex);

        if (rowIndex < 0 || rowIndex > data.length - 1 || columnIndex < 0 || columnIndex > columnNames.length - 1) {
            throw new IllegalArgumentException("Row index or column index is out of range: row=" + rowIndex + ", col=" + columnIndex);
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

    @Override
    public Object[] getColumnData(String columnName) {
        if (data == null || data.length == 0) {
            return null;
        }
        int size = data.length;
        int[] rows = new int[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            rows[rowIndex] = rowIndex;
        }

        return getColumnData(columnName, rows);
    }

    @Override
    public int getColumnIndexByName(String columnName) {
        if (data == null || data.length == 0) {
            return -1;
        }
        int columnIndex = -1;
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(columnName)) {
                columnIndex = i;
                break;
            }
        }
        return columnIndex;
    }

    @Override
    public Object[] getColumnData(String columnName, int... rows) {
        if (columnName.isEmpty()) {
            throw new IllegalArgumentException("Column name is empty");
        }

        if (rows == null || rows.length == 0) {
            throw new IllegalArgumentException("Rows indexes is null or empty");
        }

        int columnIndex = getColumnIndexByName(columnName);
        if (columnIndex == -1) {
            return null;
        }

        int size = rows.length;
        Object[] columnData = new Object[size];
        int i = 0;
        for (int rowIndex : rows) {
            columnData[i] = getValueAt(rowIndex, columnIndex);
            i++;
        }

        return columnData;
    }

    @Override
    public Object[] getColumnData(int columnIndex, int... rows) {
        String columnName = getColumnName(columnIndex);
        return getColumnData(columnName, rows);
    }
}
