package pl.nask.crs.accounts.services;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;


/**
 * @author Marianna Mysiorska
 */
public interface AccountService {

    public void alterStatus(Long id, String status, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark)
            throws AccountNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException;

    public void save(Account account, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws AccountNotFoundException, EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException, ContactCannotChangeException, ExportException;

    /**
     * Creates new account in the system. The new account is set as a parameter in the createAccountContener
     * @param createAccountContener new account data
     * @param hostmastersRemark creators remark
     * @param hostmastersHandle creator
     * @param superHostmasterHandle hostmaster logged as creator
     * @throws EmptyRemarkException thrown when the hostmastersRemark is empty
     * @throws ContactNotFoundException thrown when billing contact in createAccountContener is not found in the system
     * @throws pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException thrown when given billing contact is already assigned to some other account as a billing contact
     * @throws InvalidCountyException 
     * @throws InvalidCountryException 
     * @throws ExportException 
     */
    public void createAccount(CreateAccountContener createAccountContener, String hostmastersRemark, String hostmastersHandle, String superHostmastersHandle)
            throws EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, InvalidCountryException, InvalidCountyException, ExportException;


}
