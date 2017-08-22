package pl.nask.crs.commons.dictionary;

import java.util.List;

/**
 * 
 * @author Artur Gniadzik
 * @param <KEY>
 *            key of the dictionary entry
 * @param <ENTRY>
 *            dictionary entry
 */
public interface Dictionary<KEY, ENTRY> {
    /**
     * Returns dictionary entry with given key
     * 
     * @param key
     * @return entry with key given as an argument, never null
     * @throws IllegalArgumentException
     *             if no entry was found or 'key' argument is null
     */
    ENTRY getEntry(KEY key);

    /**
     * 
     * @return list of all entries from the dictionary. If no entry exist, empty
     *         list is returned.
     */
    List<ENTRY> getEntries();
}
