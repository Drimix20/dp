package interactive.analyzer.result.table;

import interactive.analyzer.options.InteractiveAnalyzerConfiguration;
import java.math.RoundingMode;
import java.text.NumberFormat;
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

    public DecimalPrecisionRenderer() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    protected void setValue(Object value) {
        if ((value != null) && (value instanceof Number)) {
            numberValue = (Number) value;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumFractionDigits(InteractiveAnalyzerConfiguration.getInstance().getDecimalPlaces());
            nf.setMaximumFractionDigits(InteractiveAnalyzerConfiguration.getInstance().getDecimalPlaces());
            nf.setRoundingMode(RoundingMode.HALF_UP);
            value = nf.format(numberValue.doubleValue());

            logger.trace("Decimal places " + InteractiveAnalyzerConfiguration.getInstance().getDecimalPlaces());
        }
        super.setValue(value);
    }

}
