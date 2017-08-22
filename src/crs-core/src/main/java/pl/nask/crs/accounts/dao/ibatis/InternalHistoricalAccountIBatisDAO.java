package pl.nask.crs.accounts.dao.ibatis;

import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class InternalHistoricalAccountIBatisDAO extends GenericIBatisDAO<InternalHistoricalAccount, Long> {

    public InternalHistoricalAccountIBatisDAO() {
        setFindQueryId("historicalAccount.findHistoricalAccount");
        setCreateQueryId("historicalAccount.createHistoricalAccount");
    }
    
    @Override
    public void create(InternalHistoricalAccount dto) {
        Validator.assertNotNull(dto, "the object to be created");
        performInsert("historicalAccount.createHistoricalAccount", dto);
        performInsert("historicalAccount.createHistoricalAccountFlags", dto);
    }
}
