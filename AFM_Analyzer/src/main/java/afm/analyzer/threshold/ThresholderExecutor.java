package afm.analyzer.threshold;

import afm.analyzer.gui.SegmentationConfigDialog;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class ThresholderExecutor {

    /*
     * Defined types of implemented thresholders
     */
    public static enum Strategies {

        TriangleThresholder("Triangle", false);

        private String text;
        private boolean strategyWithConfigurableOptions;

        public String getText() {
            return text;
        }

        public boolean isStrategyWitchConfigurableOptions() {
            return this.strategyWithConfigurableOptions;
        }

        private Strategies(String text, boolean strategyWithConfigurableOptions) {
            this.text = text;
            this.strategyWithConfigurableOptions = strategyWithConfigurableOptions;
        }
    }

    public static ImageThresholdStrategy getThresholder(Strategies strategy) {
        ImageThresholdStrategy thresholder = null;
        switch (strategy) {
            case TriangleThresholder:
                thresholder = new TriangleThresholder();
                break;
        }
        return thresholder;
    }

    public static SegmentationConfigDialog getSegmentationConfigDialog(
            Strategies strategy) {
        SegmentationConfigDialog configDialog = new SegmentationConfigDialog(null, true);
        switch (strategy) {
            case TriangleThresholder:
                configDialog.enableThresholdValue(false);
                break;
        }

        return configDialog;
    }

    public static Strategies getStrategy(String strategy) {
        List<Strategies> strategiesAsList = Arrays.asList(Strategies.values());
        for (int i = 0; i < strategiesAsList.size(); i++) {
            if (strategiesAsList.get(i).getText().equals(strategy)) {
                return strategiesAsList.get(i);
            }
        }

        throw new IllegalArgumentException("Unsupported staregy <" + strategy + ">");
    }

    public static String[] getStrategiesName() {
        List<Strategies> strategiesAsList = Arrays.asList(Strategies.values());
        String[] names = new String[strategiesAsList.size()];
        for (int i = 0; i < strategiesAsList.size(); i++) {
            names[i] = strategiesAsList.get(i).getText();
        }
        return names;
    }
}
