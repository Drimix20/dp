package afm.analyzer.threshold;

import static afm.analyzer.threshold.ThresholderExecutor.Strategies.*;
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

        Unselected("Unselected"), TriangleThresholder("Triangle");

        private String text;

        public String getText() {
            return text;
        }

        private Strategies(String text) {
            this.text = text;
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

    public static Strategies getStrategy(String strategy) {
        List<Strategies> strategiesAsList = Arrays.asList(Strategies.values());
        for (int i = 0; i < strategiesAsList.size(); i++) {
            if (strategiesAsList.get(i).getText().equals(strategy)) {
                return strategiesAsList.get(i);
            }
        }

        return Strategies.Unselected;
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
