package pl.nask.crs.accounts.dao;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.commons.dao.GenericDAO;

import java.util.List;


/**
 * @author Kasia Fulara
 * @author Marianna Mysiorska
 */
public interface AccountDAO extends GenericDAO<Account, Long> {

    public Long createAccount(Account account);

    public List<InternalRegistrar> getRegistrarForInternalLogin();
}
