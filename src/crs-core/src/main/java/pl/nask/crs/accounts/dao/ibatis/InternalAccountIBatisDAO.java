package pl.nask.crs.accounts.dao.ibatis;

import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public class InternalAccountIBatisDAO extends GenericIBatisDAO<InternalAccount, Long> {

    public InternalAccountIBatisDAO() {
        setGetQueryId("account.getAccountById");
        setFindQueryId("account.findAccounts");
        setUpdateQueryId("account.updateAccount");
        setCountFindQueryId("account.countTotalSearchResult");
        setLockQueryId("account.getLockedAccountById");
    }

    public Long createAccount(InternalAccount internalAccount){
        Long l = performInsert("account.createAccount", internalAccount);
        performInsert("account.createOrUpdateAccountFlags", internalAccount);
        return l;
    }
    
    @Override
    public void update(InternalAccount dto) {
        Validator.assertNotNull(dto, "the object to be updated");
        performUpdate("account.updateAccount", dto);
        performUpdate("account.createOrUpdateAccountFlags", dto);
    }

    public List<InternalRegistrar> getRegistrarForLogin() {
        return performQueryForList("account.getInternalRegistrar");
    }

}
