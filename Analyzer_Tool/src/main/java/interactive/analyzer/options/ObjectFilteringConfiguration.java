package interactive.analyzer.options;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public class ObjectFilteringConfiguration {

    private static Color barBackgroundColor = new Color(0, 0, 139);

    private static Color barBorderColor = Color.BLACK;

    public static Color getBarBackgroundColor() {
        return barBackgroundColor;
    }

    public static void setBarBackgroundColor(Color barBackgroundColor) {
        ObjectFilteringConfiguration.barBackgroundColor = barBackgroundColor;
    }

    public static Color getBarBorderColor() {
        return barBorderColor;
    }

    public static void setBarBorderColor(Color barBorderColor) {
        ObjectFilteringConfiguration.barBorderColor = barBorderColor;
    }

}
