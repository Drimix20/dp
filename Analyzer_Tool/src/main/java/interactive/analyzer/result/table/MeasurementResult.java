package interactive.analyzer.result.table;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Drimal
 */
public class MeasurementResult extends AbstractMeasurementResult {

    private SortedMap<Integer, Object> resultsMap;
    private String measurement;

    public MeasurementResult() {
        resultsMap = new TreeMap<Integer, Object>();
    }

    @Override
    public void addResult(Integer key, Object value) {
        resultsMap.put(key, value);
    }

    @Override
    public Object getResultForRoiKey(Integer key) {
        return resultsMap.get(key);
    }

    @Override
    public List<Integer> getRoiKeys() {
        return new ArrayList<Integer>(resultsMap.keySet());
    }

    @Override
    public String toString() {
        return "MeasurementResult{" + "resultsMapSize=" + resultsMap.size() + ", measurement=" + measurement + '}';
    }

}
