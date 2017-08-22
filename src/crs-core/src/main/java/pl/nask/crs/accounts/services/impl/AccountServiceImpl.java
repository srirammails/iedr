package pl.nask.crs.accounts.services.impl;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.email.AccountEmailParameters;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountHelperService;
import pl.nask.crs.accounts.services.AccountService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 * @author Marianna Mysiorska
 */
public class AccountServiceImpl implements AccountService, AccountHelperService {

    private static final String DEFAULT_REMARK_FOR_ACCOUNT_CREATE_IN_THE_NIC_HANDLE = "Account create. This nic handle is assigned as a billing contact for the account ";
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    private AccountDAO accountDAO;
    private HistoricalAccountDAO historicalAccountDAO;
    private ContactDAO contactDAO;
    private UserSearchService userSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private NicHandleService nicHandleService;
    private EmailTemplateSender emailTemplateSender;
    private ApplicationConfig applicationConfig;
    private UserService userService;


    public AccountServiceImpl(AccountDAO accountDAO, HistoricalAccountDAO historicalAccountDAO, ContactDAO contactDAO, UserSearchService userSearchService, NicHandleSearchService nicHandleSearchService, NicHandleService nicHandleService, EmailTemplateSender emailTemplateSender, ApplicationConfig appConf, UserService userService) throws JAXBException, SAXException {
		Validator.assertNotNull(accountDAO, "account dao");
        Validator.assertNotNull(historicalAccountDAO, "historical account dao");
        Validator.assertNotNull(contactDAO, "contact dao");
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        Validator.assertNotNull(nicHandleService, "nic handle service");
        Validator.assertNotNull(emailTemplateSender, "email template sender");
        Validator.assertNotNull(userService, "userService");
        this.accountDAO = accountDAO;
        this.historicalAccountDAO = historicalAccountDAO;
        this.contactDAO = contactDAO;
        this.userSearchService = userSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.emailTemplateSender = emailTemplateSender;
        this.applicationConfig = appConf;
        this.userService = userService;
    }

    public void alterStatus(Long id, String status, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark)
            throws AccountNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException {
        validateRemark(hostmastersRemark);
        Validator.assertNotNull(status, "nic handle status");
        Validator.assertNotNull(hostmasterHandle, "hostmasterHandle");
        Account account = lock(id);
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        if ("Deleted".equals(status) && !status.equals(account.getStatus()))
            nicHandleService.confirmNicHandleIsNotAssignedToAnyDomain(account.getBillingContact().getNicHandle());
        if (account.setStatus(status))
            updateAccountAndHistory(account, hostmasterHandle, superHostmasterHandle, hostmastersRemark);
    }

    @Override
    public void createAccount(CreateAccountContener createAccountContener, String hostmastersRemark, String hostmastersHandle, String superHostmasterHandle)
            throws EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, InvalidCountryException, InvalidCountyException, ExportException {
        validateRemark(hostmastersRemark);
        Validator.assertNotEmpty(hostmastersHandle, "hostmasters handle");
        String billingContactNicHandleId = createAccountContener.getAccount().getBillingContact().getNicHandle();
        confirmBillingContactIsNotAssignedToAnyAccount(billingContactNicHandleId);
        confirmBillingContactActive(billingContactNicHandleId);
        createAccountContener.getAccount().updateRemark(prepareHostmasterHandleForRemark(hostmastersHandle, superHostmasterHandle));
        Account acc = createAccountContener.getAccount();
        Long newId = accountDAO.createAccount(acc);
        changeAccountNumberInNicHandle(createAccountContener.getAccount().getBillingContact().getNicHandle(), newId,  DEFAULT_REMARK_FOR_ACCOUNT_CREATE_IN_THE_NIC_HANDLE + newId, hostmastersHandle, superHostmasterHandle);
        Account newAccount = accountDAO.get(newId);
        createAccountContener.setAccount(newAccount);
        setRegistrarGroup(createAccountContener.getAccount().getBillingContact().getNicHandle(), prepareHostmasterHandleForRemark(hostmastersHandle, superHostmasterHandle));
        //TODO redundant?
        checkGroupsAssigned(createAccountContener);
        AccountEmailParameters params = new AccountEmailParameters(createAccountContener.getAccount(), hostmastersHandle);
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CREATE_ACCOUNT.getId(), params);
        } catch (Exception e) {
            throw new AccountEmailException(e);
        }
    }

    private void setRegistrarGroup(String billingNH, String hostmasterHandle) {
        userService.setUserGroup(billingNH, Level.Registrar, hostmasterHandle);
    }

    @Override
	public void save(Account account, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle)
            throws AccountNotFoundException, EmptyRemarkException, ContactNotFoundException,NicHandleNotFoundException, ContactCannotChangeException, ExportException {
        validateRemark(hostmastersRemark);
        Validator.assertNotNull(account, "account");
        Validator.assertTrue(account.getId() >= 0, "account id >= 0");
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        Validator.assertNotNull(hostmasterHandle, "hostmaster handle");
        Account accountDB = lock(account.getId());
        confirmBillingContactDoesNotChange(accountDB.getBillingContact(), account.getBillingContact());

        updateAccountAndHistory(account, hostmasterHandle, superHostmasterHandle, hostmastersRemark);
    }

    private void checkGroupsAssigned(CreateAccountContener createAccountContener){
        Validator.assertNotNull(createAccountContener, "create account contener");
        if (userSearchService.get(createAccountContener.getAccount().getBillingContact().getNicHandle()) == null)
            createAccountContener.setAccessGroups(false);
    }

    private void confirmBillingContactDoesNotChange(Contact from, Contact to)
            throws ContactCannotChangeException{
        Validator.assertNotNull(from, "contact from");
        Validator.assertNotNull(to, "contact to");
        if (!from.getNicHandle().equals(to.getNicHandle()))
            throw new ContactCannotChangeException(from.getNicHandle(), to.getNicHandle());
    }

    private void changeAccountNumberInNicHandle(String billingContactNicHandle, Long newId, String hostmastersRemark, String hostmastersHandle, String superHostmasterHandle)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, InvalidCountryException, InvalidCountyException, ExportException {
        Validator.assertNotNull(billingContactNicHandle, "billing contact nic handle");
        Validator.assertNotNull(newId, "new id");
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(billingContactNicHandle);
        if (newId != nicHandle.getAccount().getId()){
            nicHandle.setAccount(new Account(newId));
            nicHandleService.save(nicHandle, hostmastersRemark, hostmastersHandle, superHostmasterHandle, hostmastersHandle);
        }
    }

    private void confirmBillingContactIsNotAssignedToAnyAccount(String billingContactNicHandle)
            throws ContactNotFoundException, NicHandleIsAccountBillingContactException {
        confirmBillingContactExists(billingContactNicHandle);
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setNicHandle(billingContactNicHandle);
        SearchResult<Account> res = accountDAO.find(criteria);
        if (res.getResults().size() > 0)
            throw new NicHandleIsAccountBillingContactException(billingContactNicHandle, res.getResults().get(0).getId());
    }

    private void confirmBillingContactActive(String billingContactNicHandleId)
            throws NicHandleNotFoundException, NicHandleNotActiveException {
        nicHandleSearchService.confirmNicHandleActive(billingContactNicHandleId);
    }

    private void confirmBillingContactExists(String billingContactNicHandle)
            throws ContactNotFoundException{
        Contact contact = contactDAO.get(billingContactNicHandle);
        if (contact == null)
            throw new ContactNotFoundException(billingContactNicHandle);
    }

    private void updateAccountAndHistory(Account account, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark) {
    	account.validateFlags();
    	account.updateChangeDate();
        historicalAccountDAO.create(account.getId(), account.getChangeDate(), prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        account.setRemark(hostmastersRemark);
        account.updateRemark(prepareHostmasterHandleForRemark(hostmasterHandle, superHostmasterHandle));
        accountDAO.update(account);
    }

    private String prepareHostmasterHandleForHistory(String hostmasterHandle, String superHostmasterHandle) {
        return superHostmasterHandle == null ? hostmasterHandle : superHostmasterHandle;
    }

    private String prepareHostmasterHandleForRemark(String hostmasterHandle, String superHostmasterHandle) {
        return superHostmasterHandle == null ? hostmasterHandle : superHostmasterHandle + " B/O " + hostmasterHandle;
    }

    private Account lock(Long id) throws AccountNotFoundException {
        if (accountDAO.lock(id)) {
            return accountDAO.get(id);
        } else {
            throw new AccountNotFoundException(id);
        }
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }
    
    // from the AccountHelperService
    @Override
    public NicHandle getIEDRAccount() {
    	Account iedrAcc = accountDAO.get(applicationConfig.getIedrAccountId());
    	NicHandle iedrNh;
		try {
			iedrNh = nicHandleSearchService.getNicHandle(iedrAcc.getBillingContact().getNicHandle());
			iedrNh.setName(iedrAcc.getName());
			return iedrNh;
		} catch (NicHandleNotFoundException e) {
			throw new IllegalStateException("Couldn't find IEDR account data", e);
		}
    }	
    
    @Override
    public String getVatNo(long accountNumber) throws AccountNotFoundException {    
    	Account acc = accountDAO.get(accountNumber);
    	if (acc == null) {
    		throw new AccountNotFoundException(accountNumber);
    	}
    	String billC = acc.getBillingContact().getNicHandle();
    	NicHandle nh;
    	try {
    		nh = nicHandleSearchService.getNicHandle(billC);
    		return nh.getVat().getVatNo();
    	} catch (NicHandleNotFoundException e) {
    		logger.warn("No nichandle found for " + billC + " - not a valid billing contact for accountId=" + accountNumber);
    		return null;
    	}
    }
}
