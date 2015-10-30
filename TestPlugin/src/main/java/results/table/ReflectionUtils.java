package results.table;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ReflectionUtils {

    private static Logger logger = Logger.getLogger(ReflectionUtils.class);

    /**
     * Returns specified field from specified class (even private).
     * @param className The complete name of the class (ex. java.lang.String)
     * @param fieldName The name of a static field in the class
     * @return field specified field from class
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.NoSuchFieldException
     */
    public static Field getDeclaratedField(final String className,
            final String fieldName) throws ClassNotFoundException, NoSuchFieldException {
        return Class.forName(className).getDeclaredField(fieldName);
    }

    /**
     * Returns an object containing the value of field from class (even private).
     * @param className The complete name of the class (ex. java.lang.String)
     * @param fieldName The name of a static field in the class
     * @return An Object containing the static field value.
     * @throws SecurityException .
     * @throws NoSuchFieldException .
     * @throws ClassNotFoundException .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException .
     */
    public static Object getValueOfDeclaratedField(final String className,
            final String fieldName) throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getDeclaratedField(className, fieldName);
        field.setAccessible(true);
        return field.get(Class.forName(className));
    }

    /**
     * Use reflection to change value of field from class.
     * @param className
     * @param fieldName
     * @param instance
     * @param value
     * @return
     */
    public static boolean setValueToDeclaratedField(final String className,
            final String fieldName, Object instance, Object value) {
        try {
            // Get the private field
            final Field field = getDeclaratedField(className, fieldName);
            // Allow modification on the field
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception ex) {
            logger.error("Exception while setting object " + value + "to " + fieldName + " field from " + className + " class", ex);
            return false;
        }
        return true;
    }

    /**
     * Returns an object containing the value of any field of an object instance (even private).
     * @param classInstance An Object instance.
     * @param fieldName The name of a field in the class instantiated by classInstance
     * @return An Object containing the field value.
     * @throws SecurityException .
     * @throws NoSuchFieldException .
     * @throws ClassNotFoundException .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException .
     */
    public static Object getInstanceValue(final Object classInstance,
            final String fieldName) throws SecurityException, NoSuchFieldException,
            ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        // Get the private field
        final Field field = classInstance.getClass().getDeclaredField(fieldName);
        // Allow modification on the field
        field.setAccessible(true);
        // Return the Obect corresponding to the field
        return field.get(classInstance);
    }

    /**
     * Use reflection to change value of any instance field.
     * @param classInstance An Object instance.
     * @param fieldName The name of a field in the class instantiated by classInstancee
     * @param newValue The value you want the field to be set to.
     * @throws SecurityException .
     * @throws NoSuchFieldException .
     * @throws ClassNotFoundException .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException .
     */
    public static void setInstanceValue(final Object classInstance,
            final String fieldName, final Object newValue) throws SecurityException,
            NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        // Get the private field
        final Field field = classInstance.getClass().getDeclaredField(fieldName);
        // Allow modification on the field
        field.setAccessible(true);
        // Sets the field to the new value for this instance
        field.set(classInstance, newValue);
    }
}
