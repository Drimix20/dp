package afm.analyzer.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ClassInstantiater {

    private static Logger logger = Logger.getLogger(ClassInstantiater.class);

    public static List<Object> instantiateClassesWithoutArgument(
            List<Class> classes) throws InstantiationException, IllegalAccessException {
        List<Object> instances = new ArrayList<Object>();
        for (int i = 0; i < classes.size(); i++) {
            Class<?> clazz = classes.get(i);
            instances.add(clazz.newInstance());
        }

        return instances;
    }
}
