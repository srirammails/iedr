package pl.nask.crs.commons.dao;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Patrycja Wegrzynowicz
 */
public class ConvertingGenericDAO<SRC, DST, KEY> implements
        GenericDAO<DST, KEY> {

    private GenericDAO<SRC, KEY> internalDao;

    private Converter<SRC, DST> internalConverter;

    public ConvertingGenericDAO(GenericDAO<SRC, KEY> internalDao,
            Converter<SRC, DST> internalConverter) {
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDao = internalDao;
        this.internalConverter = internalConverter;
    }

    public void create(DST dst) {
        internalDao.create(internalConverter.from(dst));
    }

    public DST get(KEY id) {
        SRC src = internalDao.get(id);
        return internalConverter.to(src);
    }

    public boolean lock(KEY id) {
        return internalDao.lock(id);
    }

    public void update(DST dst) {
        internalDao.update(internalConverter.from(dst));
    }

    public void deleteById(KEY id) {
        internalDao.deleteById(id);
    }

    public void delete(DST dst) {
        internalDao.delete(internalConverter.from(dst));
    }

    public LimitedSearchResult<DST> find(SearchCriteria<DST> criteria,
            long offset, long limit) {
        return find(criteria, offset, limit, null);
    }

    public SearchResult<DST> find(SearchCriteria<DST> criteria) {
        return find(criteria, null);
    }

    protected GenericDAO<SRC, KEY> getInternalDao() {
        return internalDao;
    }

    protected Converter<SRC, DST> getInternalConverter() {
        return internalConverter;
    }

    public LimitedSearchResult<DST> find(SearchCriteria<DST> criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        // todo: ugly cast
        LimitedSearchResult<SRC> res = internalDao.find(
                (SearchCriteria) criteria, offset, limit, sortBy);
        List<DST> ret = internalConverter.to(res.getResults());
        return new LimitedSearchResult<DST>(criteria, sortBy, res.getLimit(),
                res.getOffset(), ret, res.getTotalResults());
    }

    public SearchResult<DST> find(SearchCriteria<DST> criteria,
            List<SortCriterion> sortBy) {
        // todo: ugly cast
        SearchResult<SRC> res = internalDao.find((SearchCriteria) criteria,
                sortBy);
        List<DST> ret = internalConverter.to(res.getResults());
        return new SearchResult<DST>(criteria, sortBy, ret);
    }
}
