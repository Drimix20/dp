package interactive.analyzer.options;

/**
 *
 * @author Drimal
 */
public class ResultTableConfiguration {

    public static int MIN_PLACE = 0;
    public static int MAX_PLACE = 30;
    public static int INIT_PLACE = 3;

    private static int decimalPlaces = INIT_PLACE;
    private static boolean colorEntireRow;

    private ResultTableConfiguration() {
    }

    public static int getDecimalPlaces() {
        return decimalPlaces;
    }

    public static void setDecimalPlaces(int decimalPlaces) {
        ResultTableConfiguration.decimalPlaces = decimalPlaces;
    }

    public static boolean colorEntireRow() {
        return colorEntireRow;
    }

    public static void setColorEntireRow(boolean colorizedEntireRow) {
        ResultTableConfiguration.colorEntireRow = colorizedEntireRow;
    }

}
