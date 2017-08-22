package pl.nask.crs.web.nichandles;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.web.GenericSearchAction;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleSearchAction extends GenericSearchAction<NicHandle, NicHandleSearchCriteria> {
    private final static Logger log = Logger.getLogger(NicHandleSearchAction.class);

    private AccountSearchService accountSearchService;

    private List<Account> accounts;

    public NicHandleSearchAction(NicHandleAppService nicHandleAppService, AccountSearchService accountSearchService) {
        super(nicHandleAppService);
        Validator.assertNotNull(nicHandleAppService, "nichandle app service");
        Validator.assertNotNull(accountSearchService, "account search service");
        this.accountSearchService = accountSearchService;
        this.accounts  = accountSearchService.getAccountsWithExclusion();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    protected void updateSearchCriteria(NicHandleSearchCriteria searchCriteria) {        
        Long all = -1l;
        if (all.equals(searchCriteria.getAccountNumber()))
            searchCriteria.setAccountNumber(null);

        if ("".equals(searchCriteria.getCompanyName()))
            searchCriteria.setCompanyName(null);

        if ("".equals(searchCriteria.getEmail()))
            searchCriteria.setEmail(null);

        if ("".equals(searchCriteria.getName()))
            searchCriteria.setName(null);

        if ("".equals(searchCriteria.getNicHandleId()))
            searchCriteria.setNicHandleId(null);

        if ("".equals(searchCriteria.getDomainName()))
            searchCriteria.setDomainName(null);

    }

    @Override
    protected NicHandleSearchCriteria createSearchCriteria() {
        return new NicHandleSearchCriteria();
    }



}
