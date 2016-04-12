package afm.analyzer.measurements;

import java.util.List;

/**
 *
 * @author Drimal
 */
public abstract class AbstractMeasurementResult {

    /**
     Add new result for specified id. Id is usually id of roi for which result
     was computed
     @param key roi key
     @param value value of measurement's result
     */
    public abstract void addResult(Integer key, Object value);

    /**
     Return measurement's result for specified roi
     @param key roi id
     @return measurement's result for roi
     */
    public abstract Object getResultForRoiKey(Integer key);

    /**
     Return name of measurement what was performed to compute the stored results
     @return name of measurement
     */
    public abstract String getMeasurementName();

    /**
     Method return all id of roi's results
     @return
     */
    public abstract List<Integer> getRoiKeys();

    @Override
    public abstract String toString();

    /**
     Method return regulation for unit, i.e. ^2, ^3.
     @return unit regulation
     */
    public abstract String getUnitRegulation();

    /**
     Method return unit of result
     @return unit
     */
    public abstract String getUnit();

}
