package interactive.analyzer.options;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public class ObjectFilteringConfiguration {

    private static Color barBackgroundColor = new Color(0, 0, 139);

    private static Color barBorderColor = Color.BLACK;

    private static int decimalPlacesForInfoPanel = 3;

    public static Color getBarBackgroundColor() {
        return barBackgroundColor;
    }

    public static void setBarBackgroundColor(Color barBackgroundColor) {
        ObjectFilteringConfiguration.barBackgroundColor = barBackgroundColor;
    }

    public static int getDecimalPlacesForInfoPanel() {
        return decimalPlacesForInfoPanel;
    }

    public static void setDecimalPlacesForInfoPanel(
            int decimalPlacesForInfoPanel) {
        ObjectFilteringConfiguration.decimalPlacesForInfoPanel = decimalPlacesForInfoPanel;
    }

    public static Color getBarBorderColor() {
        return barBorderColor;
    }

    public static void setBarBorderColor(Color barBorderColor) {
        ObjectFilteringConfiguration.barBorderColor = barBorderColor;
    }

}
