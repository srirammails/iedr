package pl.nask.crs.accounts.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;

/**
 * @author Marianna Mysiorska
 */
public class AccountConverter extends AbstractConverter<InternalAccount, Account> {

    protected Account _to(InternalAccount internalAccount) {
        Contact contact = new Contact(internalAccount.getBillingContactId(), internalAccount.getBillingContactName(), internalAccount.getBillingContactEmail(), internalAccount.getBillingContactCompanyName(), internalAccount.getBillingContactCountry(), null);
        return new Account(
                internalAccount.getId(),
                internalAccount.getName(),
                contact,
                internalAccount.getStatus(),
                internalAccount.getAddress(),
                internalAccount.getCounty(),
                internalAccount.getCountry(),
                internalAccount.getWebAddress(),
                internalAccount.getInvoiceFreq(),
                internalAccount.getNextInvMonth(),
                internalAccount.getPhone(),
                internalAccount.getFax(),
                internalAccount.getTariff(),
                internalAccount.getRemark(),
                internalAccount.getCreationDate(),
                internalAccount.getStatusChangeDate(),
                internalAccount.getChangeDate(),
                internalAccount.isAgreementSigned(),
                internalAccount.isTicketEdit(),
                internalAccount.getVatCategory()
                );
    }

    protected InternalAccount _from(Account account) {
        return new InternalAccount(
                account.getId(),
                account.getName(),
                account.getStatus(),
                account.getAddress(),
                account.getCounty(),
                account.getCountry(),
                account.getWebAddress(),
                account.getInvoiceFreq(),
                account.getNextInvMonth(),
                account.getPhone(),
                account.getFax(),
                account.getTariff(),
                account.getRemark(),
                account.getBillingContact().getNicHandle(),
                account.getBillingContact().getName(),
                account.getBillingContact().getEmail(),
                account.getBillingContact().getCompanyName(),
                account.getBillingContact().getCountry(),
                account.getCreationDate(),
                account.getStatusChangeDate(),
                account.getChangeDate(),
                account.isAgreementSigned(),
                account.isTicketEdit(),
                account.getVatCategory()
        );
    }
}
