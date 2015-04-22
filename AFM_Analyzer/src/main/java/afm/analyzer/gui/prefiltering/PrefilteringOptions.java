package afm.analyzer.gui.prefiltering;

/**
 *
 * @author Drimal
 */
public class PrefilteringOptions {

    private static boolean subtractBackground = false;
    private static boolean invert = false;
    private static int blurSigma = 0;

    public static boolean isSubtractBackground() {
        return subtractBackground;
    }

    public static void setSubtractBackground(boolean subtractBackground) {
        PrefilteringOptions.subtractBackground = subtractBackground;
    }

    public static boolean makeInvert() {
        return invert;
    }

    public static void setInvert(boolean invert) {
        PrefilteringOptions.invert = invert;
    }

    public static int getBlurSigma() {
        return blurSigma;
    }

    public static void setBlurSigma(int blurSigma) {
        PrefilteringOptions.blurSigma = blurSigma;
    }
}
