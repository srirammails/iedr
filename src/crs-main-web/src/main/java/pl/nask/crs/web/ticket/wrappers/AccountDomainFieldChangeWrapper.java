package pl.nask.crs.web.ticket.wrappers;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

import java.util.List;

import org.apache.log4j.Logger;

public class AccountDomainFieldChangeWrapper extends
        SimpleDomainFieldChangeWrapper<Account> {

    private final AccountSearchService accountSearchService;
    
    private static final Logger LOG = Logger.getLogger(AccountDomainFieldChangeWrapper.class);

    public AccountDomainFieldChangeWrapper(
            SimpleDomainFieldChange<Account> orig,
            FailureReasonFactory frFactory, Field dataField,
            AccountSearchService accountSearchService, DomainOperationType type) {
        super(orig, frFactory, dataField, type);
        this.accountSearchService = accountSearchService;
    }

    @Override
    public Account getNewValue() {
        return getOrig().getNewValue();
    }

    @Override
    public void setNewValue(Account newValue) {
        getOrig().setNewValue(newValue);
    }

    public long getAccountId() {
        return getOrig().getNewValue() == null ? -1 : getOrig().getNewValue()
                .getId();
    }

    public void setAccountId(long accountId) {
    	LOG.debug("Set account Id: " + accountId);
        if (getOrig().getNewValue() != null
                && getOrig().getNewValue().getId() == accountId)
            return;
        // TODO: should check, if the account is not null. if it is, validation
        // error should be presented
        getOrig().setNewValue(accountSearchService.getAccount(accountId));
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccounts();
    }

    public String getName() {
        return getOrig().getNewValue() == null ? null : StringEscapeUtils.escapeHtml(getOrig().getNewValue().getName());
    }
    
    public boolean isAgreementSigned() {
        return getOrig().getNewValue() == null ? false : getOrig().getNewValue().isAgreementSigned();                
    }
    
    public boolean isTicketEdit() {
        return getOrig().getNewValue() == null ? false : getOrig().getNewValue().isTicketEdit();                
    }
}
