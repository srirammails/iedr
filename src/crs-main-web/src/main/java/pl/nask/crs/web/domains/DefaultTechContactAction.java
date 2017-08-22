package pl.nask.crs.web.domains;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.wrappers.StringAccountNumberWrapper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Kasia Fulara
 */
public class DefaultTechContactAction extends AuthenticatedUserAwareAction {

    private ContactSearchService contactSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private AccountSearchService accountSearchService;
    private String billContact;
    private String defaultTechContact;
    private String error;
    private StringAccountNumberWrapper domainWrapper;
    
    

    public StringAccountNumberWrapper getDomainWrapper() {
        return domainWrapper;
    }

    public void setDomainWrapper(StringAccountNumberWrapper domainWrapper) {
        this.domainWrapper = domainWrapper;
    }

    public DefaultTechContactAction(NicHandleSearchService nicHandleSearchService, ContactSearchService contactSearchService, AccountSearchService accountSearchService) {
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        this.nicHandleSearchService = nicHandleSearchService;
        Validator.assertNotNull(contactSearchService, "contact search service");
        this.contactSearchService = contactSearchService;
        Validator.assertNotNull(accountSearchService, "account search service");
        this.accountSearchService = accountSearchService;
    }

    public String execute() throws Exception {
        try {
            defaultTechContact();
        } catch (Exception e) {
            return ERROR;
        }

        return SUCCESS;
    }

    public void defaultTechContact() throws NicHandleNotFoundException {
        
        Account account = accountSearchService.getAccount(Long.parseLong(domainWrapper.getAccountNo()));
        if (account == null)
            throw new IllegalArgumentException("Wrong account number");
        Validator.assertNotNull(account.getBillingContact(), "Billing contact for account " + account + " is undefined");
        
        defaultTechContact = contactSearchService.getDefaultTechContact(account.getBillingContact().getNicHandle());
        if ((defaultTechContact == null) || (nicHandleSearchService.getNicHandle(defaultTechContact) == null)) {
            error = "No default tech contact";
            throw new NicHandleNotFoundException("empty default tech contact");
        }
    }

    public String getDefaultTechContact() {
        return defaultTechContact;
    }

    public String getBillContact() {
        return billContact;
    }

    public void setBillContact(String billContact) {
        this.billContact = billContact;
    }

    public String getError() {
        return error;
    }
}
