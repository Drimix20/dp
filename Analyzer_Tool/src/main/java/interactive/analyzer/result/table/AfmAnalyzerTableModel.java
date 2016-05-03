package interactive.analyzer.result.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerTableModel extends AbstractInteractiveTableModel {

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
        data = new ArrayList<Object[]>();
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
    public void setValues(Object[][] values) {
        logger.debug("Set table values with rows " + values.length);
        data = new ArrayList<Object[]>();
        for (int i = 0; i < values.length; i++) {
            data.add(values[i]);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        logger.trace("Row count " + data.size());
        return data.size();
    }

    @Override
    public int getColumnCount() {
        logger.trace("Column count " + columnNames.length);
        return columnNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < 0 && columnIndex > data.size() - 1) {
            throw new IllegalArgumentException("Column index " + columnIndex + " is out of range <0, " + (data.size() - 1));
        }
        if (data.isEmpty()) {
            return Object.class;
        }
        logger.trace("Column index " + columnIndex);
        Object columnValueObject = getValueAt(0, columnIndex);
        if (columnValueObject == null) {
            return Object.class;
        } else {
            return columnValueObject.getClass();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > data.size() - 1 || columnIndex < 0 || columnIndex > columnNames.length - 1) {
            throw new IllegalArgumentException("Row index or column index is out of range: row=" + rowIndex + ", col=" + columnIndex);
        }

        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        logger.trace("Set value " + value + " to row " + row + " and col " + column);

        if (row < 0 || row > data.size() || column < 0 || column > columnNames.length) {
            throw new IllegalArgumentException("Row index or column index is out of range.");
        }
        data.get(row)[column] = value;
        fireTableCellUpdated(row, column);
    }

    @Override
    public Object[] getColumnData(String columnName) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        int size = data.size();
        int[] rows = new int[size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            rows[rowIndex] = rowIndex;
        }

        return getColumnData(columnName, rows);
    }

    @Override
    public int getColumnIndexByName(String columnName) {
        if (data == null || data.isEmpty()) {
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
    public void removeRows(Set<Integer> roiIds) {
        for (Integer roiId : roiIds) {
            if (roiId < 0) {
                throw new IllegalArgumentException("Row id must be equal or greater than zero.");
            }
            for (int i = 0; i < data.size(); i++) {
                Object[] rowData = data.get(i);
                if (rowData[AfmAnalyzerResultTable.ID_COLUMN_INDEX] == roiId) {

                    data.remove(i);
                }
            }
        }
        fireTableDataChanged();
    }

    @Override
    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex > data.size()) {
            throw new IllegalArgumentException("Row index or column index is out of range.");
        }
        data.remove(rowIndex);
        fireTableDataChanged();
    }
}
