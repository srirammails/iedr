package pl.nask.crs.commons.dictionary;

import pl.nask.crs.commons.dao.GenericDAO;

import java.util.List;

/**
 * This implementation uses GenericDAO to fetch dictionary entries. DAO must
 * implement its find(SearchResult) method to interpret null argument as 'search
 * for all' and must be patametrized with
 *
 * @author Artur Gniadzik
 * @param <KEY>
 * dictionary entry key
 * @param <ENTRY>
 * dictionary entry
 */
public class GenericDAODictionary<KEY, ENTRY> implements Dictionary<KEY, ENTRY> {

    private GenericDAO<ENTRY, KEY> dao;

    public GenericDAODictionary(GenericDAO<ENTRY, KEY> dao) {
        this.dao = dao;
    }

    public List<ENTRY> getEntries() {
        return dao.find(null).getResults();
    }

    public ENTRY getEntry(KEY key) {
        return dao.get(key);
    }

}
