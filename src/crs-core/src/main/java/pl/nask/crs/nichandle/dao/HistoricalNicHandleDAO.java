package pl.nask.crs.nichandle.dao;

import java.util.Date;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;

/**
 * @author Marianna Mysiorska
 */
public interface HistoricalNicHandleDAO extends GenericDAO<HistoricalObject<NicHandle>, HistoricalNicHandleKey> {

    void create(NicHandle nicHandle, Date changeDate, String changedBy);

    SearchResult<HistoricalObject<NicHandle>> find(SearchCriteria<HistoricalObject<NicHandle>> criteria);

    HistoricalObject<NicHandle> get(HistoricalNicHandleKey changeId);
}
