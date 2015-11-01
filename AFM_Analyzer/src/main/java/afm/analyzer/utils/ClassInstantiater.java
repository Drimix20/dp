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
            List<Class<?>> classes) {
        List<Object> instances = new ArrayList<>();
        for (Class<?> clazz : classes) {

            try {
                instances.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException ex) {
                logger.warn(String.format("%s class is not instantiated.", clazz.getName()), ex);
            }

        }

        return instances;
    }
}
