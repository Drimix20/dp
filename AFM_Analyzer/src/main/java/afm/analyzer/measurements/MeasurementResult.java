package afm.analyzer.measurements;

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
    private String measurementName;

    public MeasurementResult(String measurementName) {
        resultsMap = new TreeMap<Integer, Object>();
        this.measurementName = measurementName;
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
    public String getMeasurementName() {
        return measurementName;
    }

    @Override
    public List<Integer> getRoiKeys() {
        return new ArrayList<Integer>(resultsMap.keySet());
    }

    @Override
    public String toString() {
        return "MeasurementResult{" + "resultsMapSize=" + resultsMap.size() + ", measurement=" + measurementName + '}';
    }

}
