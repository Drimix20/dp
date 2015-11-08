package afm.analyzer.result.module;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerTableModel extends AbstractAfmTableModel {

    private Logger logger = Logger.getLogger(AfmAnalyzerTableModel.class);
    private List<String> columnNames;
    private List<AbstractMeasurementResult> data = new ArrayList<>();
    private int currentNumberOfRows;

    public AfmAnalyzerTableModel(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public AfmAnalyzerTableModel() {
        columnNames = new ArrayList<>();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public void setValues(List<AbstractMeasurementResult> values) {
        if (values.size() > 0) {
            this.data = values;
            currentNumberOfRows = values.get(0).getRoiKeys().size();
            fireTableDataChanged();
        } else {
            logger.debug("No data to insert into table");
        }
    }

    @Override
    public int getRowCount() {
        logger.trace("Row count " + currentNumberOfRows);
        return currentNumberOfRows;
    }

    @Override
    public int getColumnCount() {
        logger.trace("Column count " + columnNames.size());
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        logger.trace("Get value from row " + rowIndex + " and column " + columnIndex);
        //TODO check bounds
        if (columnIndex == 0) {
            //row id
            return data.get(columnIndex).getRoiKeys().get(rowIndex);
        }
        return data.get(columnIndex - 1).getResultForRoiKey(rowIndex + 1);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        logger.trace("Set value " + value + " to row " + row + " and col " + col);
        //TODO check bounds
        if (col == 0) {
            logger.trace("Value of row id cannot be changed");
            return;
        }
        data.get(col).addResult(row, (double) value);
        fireTableCellUpdated(row, col);
    }

}
