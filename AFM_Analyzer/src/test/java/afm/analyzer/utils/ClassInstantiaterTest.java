package afm.analyzer.utils;

import afm.analyzer.measurements.list.AreaMeasurement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class ClassInstantiaterTest {

    @Test
    public void testInstantiateClassesWithoutArgument() throws Exception {
        try {
            Class<?> clazz = Class.forName("afm.analyzer.measurements.list.AreaMeasurement");
            List<Class> classes = new ArrayList<Class>();
            classes.add(clazz);
            List<Object> instances = ClassInstantiater.instantiateClassesWithoutArgument(classes);
            assertEquals(1, instances.size());
            assertTrue("Instance should be instance of AreaMeasurement", instances.get(0) instanceof AreaMeasurement);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassInstantiaterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
