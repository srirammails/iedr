package pl.nask.crs.web.accounts;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.web.GenericSearchAction;

/**
 * @author Marianna Mysiorska
 */
public class AccountSearchAction extends GenericSearchAction<Account, AccountSearchCriteria> {
    private final static Logger log = Logger.getLogger(AccountSearchAction.class);
                        
        public AccountSearchAction(AccountAppService accountAppService) {
            super(accountAppService);
        }

    @Override
    protected void updateSearchCriteria(AccountSearchCriteria searchCriteria) {
        Long lall = -1l;
        if (lall.equals(searchCriteria.getId()))
            searchCriteria.setId(null);

        if ("".equals(searchCriteria.getName()))
            searchCriteria.setName(null);

        if ("".equals(searchCriteria.getNicHandle()))
            searchCriteria.setNicHandle(null);

        if ("".equals(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);
    }

    @Override
    protected AccountSearchCriteria createSearchCriteria() {
        return new AccountSearchCriteria();
    }        

       

}
