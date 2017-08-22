package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.ibatis.objects.InternalDeposit;
import pl.nask.crs.payment.dao.DepositDAO;

import java.util.Date;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class ConvertingDepositDAO extends ConvertingGenericDAO<InternalDeposit, Deposit, String> implements DepositDAO {

    InternalDepositIbatisDAO internalDAO;
    
    public ConvertingDepositDAO(InternalDepositIbatisDAO internalDao, Converter<InternalDeposit, Deposit> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDAO = internalDao;
    }

    @Override
    public LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset, long limit) {
        return internalDAO.getDepositTopUp(nicHandle, fromDate, toDate, offset, limit);
    }

    @Override
    public LimitedSearchResult<Deposit> findHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDeposit> tmp = internalDAO.findHistory(criteria, offset, limit, sortBy);
        return new LimitedSearchResult<Deposit>(
                criteria,
                sortBy,
                limit,
                offset,
                getInternalConverter().to(tmp.getResults()),
                tmp.getTotalResults()
        );
    }

    @Override
    public LimitedSearchResult<Deposit> findDepositWithHistory(DepositSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDeposit> tmp = internalDAO.findDepositWithHistory(criteria, offset, limit, sortBy);
        return new LimitedSearchResult<Deposit>(criteria, sortBy, limit, offset, getInternalConverter().to(tmp.getResults()), tmp.getTotalResults());
    }
}
