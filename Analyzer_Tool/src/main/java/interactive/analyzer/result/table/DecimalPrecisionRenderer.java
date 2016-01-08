package interactive.analyzer.result.table;

import interactive.analyzer.options.ResultTableConfiguration;
import java.awt.Component;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.Logger;

/**
 * Class set decimal precision with which number will be rendered.
 * If column result is not number then default cell renderer is used.
 * @author Drimal
 */
public class DecimalPrecisionRenderer extends DefaultTableCellRenderer {

    private static Logger logger = Logger.getLogger(DecimalPrecisionRenderer.class);

    private Number numberValue;
    private int currentColumn;

    public DecimalPrecisionRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        currentColumn = column;
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setValue(Object value) {
        if (value != null && (currentColumn == AfmAnalyzerResultTable.SELECTION_COLUMN_INDEX || currentColumn == AfmAnalyzerResultTable.ID_COLUMN_INDEX)) {
            numberValue = (Number) value;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(0);
            nf.setRoundingMode(RoundingMode.HALF_UP);
            value = nf.format(numberValue.doubleValue());
        } else if ((value != null) && (value instanceof Number)) {
            numberValue = (Number) value;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumFractionDigits(ResultTableConfiguration.getDecimalPlaces());
            nf.setMaximumFractionDigits(ResultTableConfiguration.getDecimalPlaces());
            nf.setRoundingMode(RoundingMode.HALF_UP);
            value = nf.format(numberValue.doubleValue());
        }
        super.setValue(value);
    }

}
