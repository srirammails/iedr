package pl.nask.crs.commons.dao;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

/**
 * Generic interface for DAO.
 * 
 * @author Artur Gniadzik
 * @author Patrycja Wegrzynowicz
 * 
 * @param <DTO>
 *            Value Object type
 * @param <KEY>
 *            primary key (id) type.
 */
public interface GenericDAO<DTO, KEY> {

    /**
     * Creates new object
     * 
     * @param dto
     */
    public void create(DTO dto);

    /**
     * searches for object with id given as a parameter
     * 
     * @param id
     *            id of the searched object
     * @return
     */
    public DTO get(KEY id);

    /**
     * Locks exclusively the object identified by a given
     * identifier. Being locked the object can not be updated or deleted by any
     * other transaction. Does not return DTO object as full DTO object often
     * requires join with tables outside from the object. If DTO is required
     * it must be grabbed by separate call to {@link #get(Object) get}.
     * 
     * @param id
     *            the identifier of the object to be locked
     * 
     * @return <code>true</code> if locked successfully, <code>false</code> otherwise (e.g.
     *            object was not found)
     * 
     * @throws IllegalArgumentException
     *             thrown when the id is null
     */
    public boolean lock(KEY id);

    /**
     * Updates the object identified by the given identifier. Note that it is
     * implementation dependent which fields of the object will be updated.
     * 
     * @param dto
     *            the object to be updated
     * 
     * @throws IllegalArgumentException
     *             thrown when the dto is null
     */
    public void update(DTO dto);

    /**
     * Deletes the object identified by the given identifier.
     * 
     * @param id
     *            the identifier of the object to be deleted
     * 
     * @throws IllegalArgumentException
     *             thrown when the id is null
     */
    public void deleteById(KEY id);

    /**
     * Deletes the given object.
     *
     * @param dto
     *            the object to be deleted
     *
     * @throws IllegalArgumentException
     *             thrown when the id is null
     */
    public void delete(DTO dto);

    /**
     * Searches for objects matching given criteria. The result is limited by
     * the offset and limit arguments.
     * 
     * @param criteria
     * @param offset
     * @param limit
     *            maximum number of records to be returned
     * @return
     */
    public LimitedSearchResult<DTO> find(SearchCriteria<DTO> criteria,
            long offset, long limit);

    /**
     * Searches for objects matching given criteria in the given order. The
     * result is limited by the offset and limit arguments.
     * 
     * @param criteria
     * @param offset
     * @param limit
     *            maximum number of records to be returned
     * @param sortBy
     *            sorting criteria to be applied
     * @return
     */
    public LimitedSearchResult<DTO> find(SearchCriteria<DTO> criteria,
            long offset, long limit, List<SortCriterion> sortBy);

    /**
     * searches for objects matching given criteria.
     * 
     * @param criteria
     * @return
     */
    public SearchResult<DTO> find(SearchCriteria<DTO> criteria);

    /**
     * searches for objects matching given criteria in the given order.
     * 
     * @param criteria
     * @param sortBy
     *            sorting criteria to be applied
     * @return
     */
    public SearchResult<DTO> find(SearchCriteria<DTO> criteria,
            List<SortCriterion> sortBy);

}
