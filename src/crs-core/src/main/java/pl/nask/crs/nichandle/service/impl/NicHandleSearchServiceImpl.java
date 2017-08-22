package pl.nask.crs.nichandle.service.impl;

import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleDeletedException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleSearchServiceImpl implements NicHandleSearchService {

    private NicHandleDAO nicHandleDAO;

    public NicHandleSearchServiceImpl(NicHandleDAO nicHandleDAO) {
        Validator.assertNotNull(nicHandleDAO, "nic handle DAO");
        this.nicHandleDAO = nicHandleDAO;
    }

    public NicHandle getNicHandle(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle ret = nicHandleDAO.get(nicHandleId);
        if (ret == null)
            throw new NicHandleNotFoundException(nicHandleId);
        return ret;
    }

    public SearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria) {
        return nicHandleDAO.find(criteria);
    }

    public LimitedSearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return nicHandleDAO.find(criteria, offset, limit, orderBy);
    }

    public void confirmNicHandleNotDeleted(String nicHandleId)
            throws NicHandleNotFoundException, NicHandleDeletedException {
        NicHandle nicHandle = nicHandleDAO.get(nicHandleId);
        if (nicHandle == null)
            throw new NicHandleNotFoundException(nicHandleId);
        if (nicHandle.getStatus().equals(NicHandle.NHStatus.Deleted))
            throw new NicHandleDeletedException(nicHandleId);
    }

    public void confirmNicHandleActive(String nicHandleId)
            throws NicHandleNotFoundException, NicHandleNotActiveException {
        NicHandle nicHandle = nicHandleDAO.get(nicHandleId);
        if (nicHandle == null)
            throw new NicHandleNotFoundException(nicHandleId);
        if (!nicHandle.getStatus().equals(NicHandle.NHStatus.Active))
            throw new NicHandleNotActiveException(nicHandleId);
    }
}
