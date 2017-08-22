package pl.nask.crs.commons.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class CollectionUtils {

	private CollectionUtils() {
		// utility class
	}
	
	/**
	 * Converts the given collection into the string using a separator given in a parameter 
	 * 
	 * @param collection
	 * @param separator
	 * @return 
	 */
	public static String toString(Collection<?> collection, String separator) {
		return toString(collection, false, separator);
	}

	public static String toString(Collection<?> collection, boolean skipNullElements, String separator) {
		if (collection == null || collection.isEmpty()) 
			return "";
			
		StringBuilder builder = new StringBuilder();
		for (Object o: collection) {
			if (o == null) {
				if (!skipNullElements) {
					builder.append("null");
				}
			} else {
				builder.append(o.toString());
			}
			
			builder.append(separator);
		}
		if (builder.length() == 0) {
			return "";
		} else {
			return builder.substring(0, builder.length() - separator.length());
		}
	}

	public static <T> Set<T> arrayAsSet(T... elements) {
		if (elements == null || elements.length == 0)
			return Collections.emptySet();
		
		Set<T> result = new HashSet<T>();
		for (T t: elements) {
			if (t != null) {
				result.add(t);
			}
		}
		
		return result;
	}

    public static abstract class Predicate<E> {
        public abstract boolean test(E elem);
    }

    public static <E> boolean exists(Collection<E> collection, CollectionUtils.Predicate<E> pred) {
        for (E el : collection)
            if (pred.test(el))
                return true;
        return false;
    }

    public static <E> boolean forAll(Collection<E> col, CollectionUtils.Predicate<E> pred) {
        for (E el : col)
            if (!pred.test(el))
                return false;
        return true;
    }
}
