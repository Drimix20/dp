package interactive.analyzer.options;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class ResultTableConfiguration {

    public static final int MIN_PLACE = 0;
    public static final int MAX_PLACE = 30;
    public static final int INIT_PLACE = 3;

    private static ResultTableConfiguration instance;

    private static volatile List<Integer> decimalPlacesConfigForColumn;
    private static boolean colorEntireRow;

    private ResultTableConfiguration() {
        decimalPlacesConfigForColumn = new ArrayList<Integer>();
    }

    public static ResultTableConfiguration getInstance() {
        if (instance == null) {
            synchronized (ResultTableConfiguration.class) {
                if (instance == null) {
                    instance = new ResultTableConfiguration();
                }
            }
        }
        return instance;
    }

    public void initValues(int numberOfColumns) {
        if (numberOfColumns < 2) {
            throw new IllegalArgumentException("Number of columns must be grater or equal to 2");
        }

        decimalPlacesConfigForColumn = new ArrayList<Integer>();
        //for column and tag set decimal places equal to zero
        decimalPlacesConfigForColumn.add(0);
        decimalPlacesConfigForColumn.add(0);
        for (int i = 2; i < numberOfColumns; i++) {
            decimalPlacesConfigForColumn.add(INIT_PLACE);
        }
    }

    public int getDecimalPlaces(int columnIndex) {
        if (columnIndex < 0 || columnIndex > decimalPlacesConfigForColumn.size()) {
            throw new IllegalArgumentException("Column index is out of range");
        }
        return decimalPlacesConfigForColumn.get(columnIndex);
    }

    public void setDecimalPlaces(int columnIndex, int decimalPlaces) {
        if (columnIndex < 0 || columnIndex > decimalPlacesConfigForColumn.size()) {
            throw new IllegalArgumentException("Column index is out of range");
        }
        decimalPlacesConfigForColumn.set(columnIndex, decimalPlaces);
    }

    public static boolean colorEntireRow() {
        return colorEntireRow;
    }

    public static void setColorEntireRow(boolean colorizedEntireRow) {
        ResultTableConfiguration.colorEntireRow = colorizedEntireRow;
    }

}
