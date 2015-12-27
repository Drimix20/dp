package interactive.analyzer.presenter;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public class ImageWindowConfiguration {

    private static int strokeWidth = 1;
    private static Color strokeColor = Color.YELLOW;
    private static int fontSize = 12;
    private static Color fontColor = Color.WHITE;
    private static Color fontBackgroundColor = Color.DARK_GRAY;

    private ImageWindowConfiguration() {
    }

    public static int getStrokeWidth() {
        return strokeWidth;
    }

    public static void setStrokeWidth(int strokeWidth) {
        ImageWindowConfiguration.strokeWidth = strokeWidth;
    }

    public static Color getStrokeColor() {
        return strokeColor;
    }

    public static void setStrokeColor(Color strokeColor) {
        ImageWindowConfiguration.strokeColor = strokeColor;
    }

    public static int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        ImageWindowConfiguration.fontSize = fontSize;
    }

    public static Color getFontColor() {
        return fontColor;
    }

    public static void setFontColor(Color fontColor) {
        ImageWindowConfiguration.fontColor = fontColor;
    }

    public static Color getFontBackgroundColor() {
        return fontBackgroundColor;
    }

    public static void setFontBackgroundColor(Color fontBackgroundColor) {
        ImageWindowConfiguration.fontBackgroundColor = fontBackgroundColor;
    }

}
