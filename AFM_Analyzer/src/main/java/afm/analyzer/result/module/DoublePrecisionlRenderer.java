package afm.analyzer.result.module;

import java.math.RoundingMode;
import java.text.NumberFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Class set decimal precision with which number will be rendered.
 If column result is not number then default cell renderer is used.
 * @author Drimal
 */
public class DoublePrecisionlRenderer extends DefaultTableCellRenderer {

    private static final int DEFAULT_PRECISION = 10;
    private int precision = 0;
    private Number numberValue;
    private NumberFormat nf;

    public DoublePrecisionlRenderer(int precision) {
        super();
        configureNumberFormat(precision);
    }

    public DoublePrecisionlRenderer() {
        super();
        configureNumberFormat(DEFAULT_PRECISION);
    }

    private void configureNumberFormat(int precision1) {
        setHorizontalAlignment(SwingConstants.RIGHT);
        nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(precision1);
        nf.setMaximumFractionDigits(precision1);
        nf.setRoundingMode(RoundingMode.HALF_UP);
    }

    @Override
    protected void setValue(Object value) {
        if ((value != null) && (value instanceof Number)) {
            numberValue = (Number) value;
            value = nf.format(numberValue.doubleValue());
        }
        super.setValue(value);
    }

}
