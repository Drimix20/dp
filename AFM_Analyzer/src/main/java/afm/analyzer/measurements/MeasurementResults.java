package afm.analyzer.measurements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class MeasurementResults {

    private Map<Integer, Double> resultsMap;
    private String measurement;

    public MeasurementResults() {
        resultsMap = new HashMap<Integer, Double>();
    }

    public void addResult(Integer key, Double value) {
        resultsMap.put(key, value);
    }

    public double getResult(Integer key) {
        return resultsMap.get(key);
    }

    public List<Integer> getKeys() {
        return new ArrayList<>(resultsMap.keySet());
    }

}
