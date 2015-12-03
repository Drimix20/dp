package interactive.analyzer.result.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Abstract class defines what user must use to create own implementation of table model
 *
 * @author Drimal
 */
public abstract class AbstractAfmTableModel extends AbstractTableModel {

    protected String[] columnNames;
    protected Object[][] data;

    public AbstractAfmTableModel() {
        columnNames = new String[0];
        data = new Object[0][0];
    }

    /**
     * Get number of row in current table
     * @return number of row
     */
    @Override
    public abstract int getRowCount();

    /**
     * Get number of column in current table
     * @return number of column
     */
    @Override
    public abstract int getColumnCount();

    /**
     * Get column name for column specified by column index
     * @param col
     * @return column name
     */
    @Override
    public abstract String getColumnName(int col);

    /**
     * Method for setting values to table. This method is also used to changing values in table.
     * For notify that table values was changed method fireTableDataChange must be called.
     * List item is equal to measurement. AbstractmeasurementResult contains measurement's result for each region of interest
     * @param values values visible in table
     */
    public abstract void setValues(List<AbstractMeasurementResult> values);

    /**
     * Method for setting values to table. This method is also used to changing values in table.
     * For notify that table values was changed method fireTableDataChange must be called.
     * @param values values visible in table
     */
    public abstract void setValues(Object[][] values);

    /**
     * Method which can edit specific table cell
     * @param value value to set
     * @param row row index
     * @param col column index
     */
    @Override
    public abstract void setValueAt(Object value, int row, int col);

    /**
     * Method return value in specific table cell
     * @param rowIndex row index
     * @param columnIndex column index
     * @return value of specified cell
     */
    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    /**
     * Return column data from table model specified by column name
     * @param column name of column
     * @return column data
     */
    public abstract Object[] getColumnData(String column);

    /**
     * Get column data for specific column and rows
     * @param columnName name of column
     * @param rows indexes of rows
     * @return
     */
    public abstract Object[] getColumnData(String columnName, int... rows);

    /**
     * Get column data for specific column and rows
     * @param columnIndex index of column
     * @param rows indexes of rows
     * @return
     */
    public abstract Object[] getColumnData(int columnIndex, int... rows);

    /**
     * Get column index specified by column name
     * @param columnName
     * @return column index for column with specified name. If no column exists then return -1.
     */
    public abstract int getColumnIndexByName(String columnName);

    /**
     * Force firing TableDataChanged event
     */
    public void forceFireDataChanged() {
        fireTableDataChanged();
    }

}
