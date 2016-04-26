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
    private int unitExponent;
    private String unit;

    /**
     *
     * @param measurementName
     * @param unitAbbreviation abbreviation of result's unit
     * @param unitExponent
     */
    public MeasurementResult(String measurementName, String unitAbbreviation,
            int unitExponent) {
        resultsMap = new TreeMap<Integer, Object>();
        this.measurementName = measurementName;
        this.unitExponent = unitExponent;
        this.unit = unitAbbreviation;
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
    public String getUnit() {
        return unit;
    }

    @Override
    public List<Integer> getRoiKeys() {
        return new ArrayList<Integer>(resultsMap.keySet());
    }

    @Override
    public String toString() {
        return "MeasurementResult{" + "resultsMapSize=" + resultsMap.size()
                + ", measurement=" + measurementName
                + ",unitExponent=" + unitExponent + '}';
    }

    @Override
    public int getUnitExponent() {
        return unitExponent;
    }

}
