package pl.nask.crs.commons.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class Validator {

    private Validator() {}
    
    /**
	 * Throws an exception, if the value parameter is null, or an empty String
	 * after trimming.
	 * 
	 * @param value
	 *            parameter to check
	 * @param valueName
	 *            name of the parameter to be used in the exception message
	 * @throws IllegalArgumentException
	 *             if value parameter is null, or an empty String after
	 *             trimming.
	 */
	public static void assertNotEmpty(String value, String valueName) {
		if (isEmpty(value))
			throw new IllegalArgumentException(valueName + " cannot be empty");
	}

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static void assertNotEmpty(Collection<?> value, String valueName) {
        if (isEmpty(value))
            throw new IllegalArgumentException(valueName + " cannot be empty");
    }

    public static boolean isEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    /**
	 * Throws an exception, if the value parameter is null.
	 * 
	 * @param value
	 *            parameter to check
	 * @param valueName
	 *            name of the parameter to be used in the exception message
	 * @throws IllegalArgumentException
	 *             if the value parameter is null
	 */
	public static void assertNotNull(Object value, String valueName) {
		if (value == null)
			throw new IllegalArgumentException(valueName + " cannot be null");
	}

    /**
	 * Throws an exception, if the value parameter is not null.
	 *
	 * @param value
	 *            parameter to check
	 * @param valueName
	 *            name of the parameter to be used in the exception message
	 * @throws IllegalArgumentException
	 *             if the value parameter is null
	 */
	public static void assertNull(Object value, String valueName) {
		if (value != null)
			throw new IllegalArgumentException(valueName + " is not null");
	}

    /**
	 * Throws an exception, if the value parameter is false.
	 *
	 * @param value
	 *            boolean parameter to check
	 * @param valueName
	 *            name of the parameter to be used in the exception message
	 * @throws IllegalArgumentException
	 *             if the value parameter is false
	 */
	public static void assertTrue(boolean value, String valueName) {
		if (!value)
			throw new IllegalArgumentException(valueName + " cannot be false");
	}

    /**
     * If a collection contains duplicated elements, it will return one of the duplicates. 
     * @param collection to be searched
     * @return duplicated element or null if there is no duplicates
     */
    public static <T> T getDuplicates(Collection<T> list) {
        Set<T> set = new HashSet<T>();
        for(T el : list) {
            if (!set.add(el))
                return el;
        }
        return null;
    }

    /**
     * Will return true, if the collection contains duplicated elements.
     * @param collection collection to be checked.
     * @return true, if the collection contains duplicated elements.
     */
    public static boolean hasDuplicates(Collection<?> collection) {
        Object duplicated = getDuplicates(collection);
        return duplicated != null;
    }

    public static boolean isEqual(Object obj1, Object obj2) {
    	if (obj1 == obj2)
    		return true;
    	
    	return  obj1 != null && obj1.equals(obj2);
    }

}
