package interactive.analyzer.options;

/**
 *
 * @author Drimal
 */
public class ResultTableConfiguration {

    public static int MIN_PLACE = 0;
    public static int MAX_PLACE = 30;
    public static int INIT_PLACE = 3;

    private int decimalPlaces = INIT_PLACE;

    private ResultTableConfiguration() {
    }

    public static ResultTableConfiguration getInstance() {
        return InteractiveAnalyzerConfigurationHolder.INSTANCE;
    }

    private static class InteractiveAnalyzerConfigurationHolder {

        private static final ResultTableConfiguration INSTANCE = new ResultTableConfiguration();
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

}
