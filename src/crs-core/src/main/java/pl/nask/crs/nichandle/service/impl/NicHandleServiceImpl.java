package pl.nask.crs.nichandle.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.impl.AccountUpdateExporter;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.EmailValidator;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.search.ContactSearchCriteria;
import pl.nask.crs.contacts.services.ContactSearchService;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.Vat;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.nichandle.email.TacNicHandleDetailsEmailParams;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleIsTicketContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.nichandle.service.NicHandleIdGenerator;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.nichandle.service.impl.helper.PasswordHelper;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleServiceImpl implements NicHandleService {

    private final static String RESET_PASSWORD_REMARK = "Change password";
    private final static NicHandle.NHStatus DEFAULT_STATUS = NicHandle.NHStatus.Active;
    private final static boolean DEFAULT_BILLC_IND = false;
    private final static Logger logger = Logger.getLogger(NicHandleServiceImpl.class);

    private NicHandleDAO nicHandleDAO;
    private HistoricalNicHandleDAO historicalNicHandleDAO;
    private NicHandleIdGenerator nicHandleIdGenerator;
    private EmailTemplateSender emailTemplateSender;
    private UserService userService;
    private UserSearchService userSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private ContactSearchService contactSearchService;
    private CountryFactory countryFactory;
	private final AccountUpdateExporter exporter;
	private AccountDAO accountDao;
    private final ApplicationConfig applicationConfig;

    public NicHandleServiceImpl(NicHandleDAO nicHandleDAO, HistoricalNicHandleDAO historicalNicHandleDAO, NicHandleIdGenerator nicHandleIdGenerator, EmailTemplateSender emailTemplateSender, UserService userService, UserSearchService userSearchService, NicHandleSearchService nicHandleSearchService, ContactSearchService contactSearchService, CountryFactory countryFactory, AccountUpdateExporter exporter, AccountDAO accountDAO, ApplicationConfig applicationConfig) {
		Validator.assertNotNull(nicHandleDAO, "nic handle dao");
        Validator.assertNotNull(historicalNicHandleDAO, "historical nic handle dao");
        Validator.assertNotNull(nicHandleIdGenerator, "nic handle id generator");
        Validator.assertNotNull(emailTemplateSender, "email template sender");
        Validator.assertNotNull(userService, "user service");
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        Validator.assertNotNull(contactSearchService, "contact search service");
        Validator.assertNotNull(countryFactory, "countryFactory");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.nicHandleDAO = nicHandleDAO;
        this.historicalNicHandleDAO = historicalNicHandleDAO;
        this.nicHandleIdGenerator = nicHandleIdGenerator;
        this.emailTemplateSender = emailTemplateSender;
        this.userService = userService;
        this.userSearchService = userSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.contactSearchService = contactSearchService;
        this.countryFactory = countryFactory;
        this.exporter = exporter;
        this.accountDao = accountDAO;
        this.applicationConfig = applicationConfig;
    }

    public void save(NicHandle newNicHandle, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle, String loggedUserName)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, InvalidCountryException, InvalidCountyException, ExportException {
        validateRemark(hostmastersRemark);
        Validator.assertNotNull(newNicHandle, "nic handle");
        Validator.assertNotNull(hostmasterHandle, "hostmaster handle");
        Validator.assertNotEmpty(newNicHandle.getNicHandleId(), "nic handle id");
        Validator.assertNotEmpty(newNicHandle.getEmail(), "nic handle email");
        EmailValidator.validateEmail(newNicHandle.getEmail());
        validateCountryCounty(newNicHandle.getCountry(), newNicHandle.getCounty(), true);
        long accountId = newNicHandle.getAccount().getId();
        NicHandle nicHandleDB = nicHandleSearchService.getNicHandle(newNicHandle.getNicHandleId());
        confirmAccountActive(accountId);
        lock(newNicHandle.getNicHandleId());
        confirmAccountCanBeChanged(nicHandleDB, accountId);
        handleVatCategoryUpdate(newNicHandle, nicHandleDB);
        notifyIfVatDataChanged(newNicHandle, nicHandleDB, loggedUserName);
        updateNicHandleAndHistory(newNicHandle, hostmasterHandle, superHostmasterHandle, hostmastersRemark);
        exportChange(newNicHandle);
        sendNotification(hostmasterHandle, newNicHandle);
    }

    private void sendNotification(String username, NicHandle nicHandle) throws NicHandleEmailException {
    	NicHandle creator = nicHandleDAO.get(username);
    	NicHandle billingNh = getBillingNicFor(nicHandle);
    	EmailParameters templateParameters = new TacNicHandleDetailsEmailParams(creator, nicHandle, billingNh, username);
    	User u = userSearchService.get(nicHandle.getNicHandleId());
    	
    	EmailTemplateNamesEnum template = null;
    	if (u != null) {
    		if (u.hasDefaultAccessOnly()) {
    			if (nicHandle.getAccount().getId() == (long) applicationConfig.getGuestAccountId()) {
    				template = EmailTemplateNamesEnum.TAC_NIC_HANDLE_DETAILS_AMENDED_DIRECT;
    			} else {    				
    				template = EmailTemplateNamesEnum.TAC_NIC_HANDLE_DETAILS_AMENDED_REG;
    			}
    		} else {
    			if (u.hasGroup(Level.Direct) || u.hasGroup(Level.Registrar) || u.hasGroup(Level.SuperRegistrar)) {
    				template = EmailTemplateNamesEnum.ACCOUNT_DATA_UPDATE;
    			}
    		}
    	}

    	if (template != null) {
    		try {
    			emailTemplateSender.sendEmail(template.getId(), templateParameters);
    		} catch (Exception e) {
    			logger.error("Problems when sending an email with id= " + template.getId(), e);
    		} 
    	}
    }
    	

	private NicHandle getBillingNicFor(NicHandle nh) {
		if (nh.getAccount().getId() != applicationConfig.getGuestAccountId()) {
			Account a = accountDao.get(nh.getAccount().getId());
			return nicHandleDAO.get(a.getBillingContact().getNicHandle());		
		} else {
			logger.warn("Not immplemented: find a Direct registrar for a given tech/admin contact");
			NicHandle ret = nicHandleDAO.getDirectNhForContact(nh.getNicHandleId());
			if (ret == null)
				logger.warn("Direct registrar not found for " + nh.getNicHandleId());
			return ret;
		}
	}

	private void handleVatCategoryUpdate(NicHandle nicHandle, NicHandle nicHandleDB) throws NicHandleNotFoundException {
        String currentVatCategory = nicHandleDB.getVatCategory();
        String newVatCategory = nicHandle.getVatCategory();
        if (!isVatCategoryChanged(currentVatCategory, newVatCategory)) {
            populateVatFromCountry(nicHandleDB, nicHandle);
        }
    }

    private boolean isVatCategoryChanged(String currentVatCategory, String newVatCategory) {
        return (currentVatCategory == null && newVatCategory != null) || currentVatCategory != null && !currentVatCategory.equals(newVatCategory);
    }

    private void populateVatFromCountry(NicHandle nicHandleDB, NicHandle nicHandle) {
        String currentCountry = nicHandleDB.getCountry();
        String newCountry = nicHandle.getCountry();
        if (!currentCountry.equals(newCountry)) {
            nicHandle.setVatCategory(countryFactory.getCountryVatCategory(newCountry));
        }
    }

    @Override
    public void triggerExport(String nicHandleId) throws ExportException, NicHandleNotFoundException {
    	NicHandle nh = nicHandleSearchService.getNicHandle(nicHandleId);
    	exportChange(nh);
    }

    private void exportChange(NicHandle nicHandle) throws ExportException {
    	long accountId = nicHandle.getAccount().getId(); 
    	Account account = accountDao.get(accountId);
        long guestAccountId = applicationConfig.getGuestAccountId();
        
        User u = userSearchService.get(nicHandle.getNicHandleId());

        if ((accountId == guestAccountId && u != null && u.hasGroup(Level.Direct)) || account.getBillingContact().getNicHandle().equals(nicHandle.getNicHandleId())) {
            logger.info("NicHandle export started.");
        	exporter.exportAccount(accountId, nicHandle.getName(), nicHandle.getVat().getVatNo(), nicHandle.getAddress(),
        			null, null, nicHandle.getNicHandleId(), nicHandle.getCountry(), nicHandle.getCounty(),
                    nicHandle.getChangeDate(), nicHandle.getPhonesAsString(), nicHandle.getFaxesAsString(), nicHandle.getEmail(), nicHandle.getVatCategory());
        } else {
            logger.warn("NicHandle export skipped (not direct nor registrar billing nic).");
        }
	}

	private void validateCountryCounty(String country, String county, boolean strictCountyValidation) throws InvalidCountryException, InvalidCountyException {
    	Validator.assertNotNull(county, "county");
        Validator.assertNotEmpty(country, "country");
    	countryFactory.validate(country, county, strictCountyValidation);
	}

	public void alterStatus(String nicHandleId, NicHandle.NHStatus status, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark)
            throws NicHandleNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException, NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException {
        validateRemark(hostmastersRemark);
        Validator.assertNotNull(status, "nic handle status");
        Validator.assertNotNull(hostmasterHandle, "hostmasterHandle");
        if (status.equals(NicHandle.NHStatus.Deleted)){
            confirmNicHandleNotAccountBillingContact(nicHandleId);
            confirmNicHandleIsNotAssignedToAnyDomain(nicHandleId);
            confirmNicHandleIsNotTicketContact(nicHandleId);
        }
        NicHandle nicHandle = lock(nicHandleId);
        if (nicHandle.setStatus(status))
            updateNicHandleAndHistory(nicHandle, hostmasterHandle, superHostmasterHandle, hostmastersRemark);
    }

    public void saveNewPassword(String password1, String password2, String nicHandleId, String hostmasterHandle, String superHostmasterHandle, String loggedUserName)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(hostmasterHandle, "hostmaster handle");
        PasswordHelper.validatePassword(password1, password2);
        saveNewPassword(nicHandleId, password1, hostmasterHandle, superHostmasterHandle, EmailTemplateNamesEnum.CHANGE_PASSWORD.getId(), true, loggedUserName);
    }

    private void saveNewPassword(String nicHandleId, String password, String hostmasterHandle, String superHostmasterHandle, int emailTemplateId, boolean sentNotificationEmail, String loggedUserName) throws PasswordAlreadyExistsException, NicHandleNotFoundException, NicHandleEmailException {
        userService.changePassword(nicHandleId, password, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        updateNicAndHistoryAndSendNotifications(nicHandle, hostmasterHandle, superHostmasterHandle, emailTemplateId, sentNotificationEmail, loggedUserName);
    }

    private void generateAndSaveNewPassword(String nicHandleId, String hostmasterHandle, String superHostmasterHandle, int emailTemplateId, boolean sentNotificationEmail, String loggedUserName) throws NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(hostmasterHandle, "hostmaster handle");
        String newPassword = PasswordHelper.generateNewPassword(16);
        saveNewPassword(nicHandleId, newPassword, hostmasterHandle, superHostmasterHandle, emailTemplateId, sentNotificationEmail, loggedUserName);
    }

    @Override
    public void changePassword(String oldPassword, String password1, String password2, String nicHandleId, String hostmasterHandle, String superHostmasterHandle)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(hostmasterHandle, "hostmaster handle");
        PasswordHelper.validatePassword(password1, password2);
        userService.changePassword(nicHandleId, oldPassword, password1, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        updateNicAndHistoryAndSendNotifications(
            nicHandle, hostmasterHandle,
            superHostmasterHandle, EmailTemplateNamesEnum.CHANGE_PASSWORD.getId(), true, hostmasterHandle
        );
    }
    
    public void resetPassword(AuthenticatedUser user, String nicHandleId, String hostmasterHandle, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(hostmasterHandle, "hostmaster handle");
        privateResetPassword(user, nicHandleId, hostmasterHandle, ipAddress, EmailTemplateNamesEnum.RESET_PASSWORD.getId(), true);
    }

    private void privateResetPassword(AuthenticatedUser user, String nicHandleId, String hostmasterHandle, String ipAddress, 
            int emailTemplateId, boolean sendNotificationEmail)
            throws NicHandleNotFoundException, NicHandleEmailException {
        String token = PasswordHelper.generateNewPassword(16);
        userService.resetPassword(nicHandleId, token, ipAddress, hostmasterHandle);
        try {
            if (sendNotificationEmail) {
                NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
                NicHandle billingNH = this.getBillingNicFor(nicHandle);
                NicHandleEmailParameters params = new NicHandleEmailParameters(
                        nicHandle, token,
                        (user == null) ? null : user.getUsername(),
                        (billingNH == null) ? null : billingNH.getNicHandleId());
                emailTemplateSender.sendEmail(emailTemplateId, params);
            }
        } catch (NicHandleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Problem with sending email while resetting password.", e);
            throw new NicHandleEmailException(nicHandleId, e);
        }
    }

    private void updateNicAndHistoryAndSendNotifications(NicHandle nicHandle, String hostmasterHandle, String superHostmasterHandle, int emailTemplateId, boolean sendNotificationEmail, String loggedUserName)
            throws NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException {
        updateNicHandleAndHistory(nicHandle, hostmasterHandle, superHostmasterHandle, RESET_PASSWORD_REMARK);
        if (sendNotificationEmail) {
            NicHandle billingNH = this.getBillingNicFor(nicHandle);
            NicHandleEmailParameters params = new NicHandleEmailParameters(
                nicHandle,
                loggedUserName,
                (billingNH ==  null) ? null : billingNH.getNicHandleId());
            try {
            	emailTemplateSender.sendEmail(emailTemplateId, params);
            } catch (Exception e) {
                logger.error("Problem with sending email while changing password.", e);
                throw new NicHandleEmailException(nicHandle.getNicHandleId(), e);
            }
        }
    }

    public NicHandle createNicHandle(String name, String companyName, String email, String address, String county, String country, Long accountNumber, String accountName, Set<String> phones, Set<String> faxes, String nicHandleRemark, String nicHandleCreator, String vatNo, boolean sendNotificationEmail, String superNicHandle, boolean generatePassword, boolean strictCountyValidation)
            throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException {
        validateRemark(nicHandleRemark);
//        Validator.assertNotEmpty(nicHandleCreator, "nic handle creator");
        Validator.assertNotEmpty(name, "name");
        Validator.assertNotNull(accountNumber, "account number");
//        Validator.assertNotNull(accountName, "account name");
        Validator.assertNotNull(companyName, "company name");
        Validator.assertNotEmpty(address, "address");
        Validator.assertNotEmpty(email, "email");
        validateCountryCounty(country, county, strictCountyValidation);
        EmailValidator.validateEmail(email);
        confirmAccountActive(accountNumber);
        String nicHandleId = nicHandleIdGenerator.generateNicHandleId();
        Date todayDate = new Date();
        Timestamp todayTimestamp = new Timestamp(todayDate.getTime());
        String hostmaster = (Validator.isEmpty(nicHandleCreator)) ? nicHandleId : nicHandleCreator;
        
        NicHandle nicHandle = new NicHandle(
                nicHandleId,
                name,
                new Account(accountNumber, accountName),
                companyName,
                address,
                phones,
                faxes,
                county,
                country,
                email,
                DEFAULT_STATUS,
                todayDate,
                todayDate,
                todayTimestamp,
                DEFAULT_BILLC_IND,
                nicHandleRemark,
                hostmaster,
                new Vat(vatNo),
                countryFactory.getCountryVatCategory(country));
        
        nicHandle.updateRemark(prepareHostmasterHandleForRemark(hostmaster, superNicHandle));
        nicHandleDAO.create(nicHandle);
        if (generatePassword) {
            generateAndSaveNewPassword(nicHandleId, hostmaster, superNicHandle, EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(), sendNotificationEmail, nicHandleCreator);
        }
        return nicHandle;
    }

    public void confirmNicHandleIsNotAssignedToAnyDomain(String nicHandleId)
            throws NicHandleAssignedToDomainException {
        if (nicHandleDAO.getNumberOfAssignedDomains(nicHandleId) > 0)
            throw new NicHandleAssignedToDomainException(nicHandleId);
    }

    private void confirmNicHandleNotAccountBillingContact(String nicHandleId)
            throws NicHandleIsAccountBillingContactException {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setNicHandle(nicHandleId);
        if (nicHandleDAO.getNumberOfAccountsForIdAndNicHandle(criteria) > 0)
            throw new NicHandleIsAccountBillingContactException(nicHandleId);

    }

    private void confirmNicHandleIsNotTicketContact(String nicHandleId)
            throws NicHandleIsTicketContactException{
        if (nicHandleDAO.getNumberOfTicketsForNicHandle(nicHandleId) > 0)
            throw new NicHandleIsTicketContactException(nicHandleId);
    }

    private NicHandle lock(String nicHandleId)
            throws NicHandleNotFoundException {
        if (nicHandleDAO.lock(nicHandleId)) {
            return nicHandleDAO.get(nicHandleId);
        } else {
            throw new NicHandleNotFoundException(nicHandleId);
        }
    }

    private void updateNicHandleAndHistory(NicHandle nicHandle, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark) {
        NicHandle existingNicHandle = nicHandleDAO.get(nicHandle.getNicHandleId());
        nicHandle.updateChangeDate();
        Date chngDt = nicHandle.getChangeDate();
        historicalNicHandleDAO.create(existingNicHandle, chngDt, prepareHostmasterHandleForHistory(hostmasterHandle, superHostmasterHandle));
        nicHandle.setNicHandleRemark(hostmastersRemark);
        nicHandle.updateRemark(prepareHostmasterHandleForRemark(hostmasterHandle, superHostmasterHandle));
        nicHandleDAO.update(nicHandle);
    }

    private String prepareHostmasterHandleForHistory(String hostmasterHandle, String superHostmasterHandle) {
        return superHostmasterHandle == null ? hostmasterHandle : superHostmasterHandle;
    }

    private String prepareHostmasterHandleForRemark(String hostmasterHandle, String superHostmasterHandle) {
        return superHostmasterHandle == null ? hostmasterHandle : superHostmasterHandle + " B/O " + hostmasterHandle;
    }

    /**
     * Function checks if the account number of a nic handle can be changed.
     * Account number cannot be changed if:
     * - the current account number == 1 and the new account number != current and the nic handle is in Contact table with type = "B" for any domain.
     * or
     * - the current account number > 99 and the new account number != current account number and the nic handle is listed in Account table as Biling_NH for the current account number.
     *
     * @param nicHandleDB      NicHandle object assigned with account
     * @param newAccountNumber new account number for the nic handle
     * @throws pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException
     *          exception thrown when the change cannot be made
     */
    private void confirmAccountCanBeChanged(NicHandle nicHandleDB, long newAccountNumber)
            throws NicHandleIsAccountBillingContactException, NicHandleNotFoundException {        
        String nicHandleId = nicHandleDB.getNicHandleId();
        if (nicHandleDB.getAccount().getId() == 1 && newAccountNumber != 1) {
            ContactSearchCriteria criteria = new ContactSearchCriteria();
            criteria.setNicHandle(nicHandleId);
            criteria.setType("B");
            if (contactSearchService.findContacts(criteria, 1, 1).getResults().size() > 0)
                throw new NicHandleIsAccountBillingContactException(nicHandleId, nicHandleDB.getAccount().getId());
        }
        if (nicHandleDB.getAccount().getId() > 99 && newAccountNumber != nicHandleDB.getAccount().getId()) {
            AccountSearchCriteria criteria = new AccountSearchCriteria();
            criteria.setId(nicHandleDB.getAccount().getId());
            criteria.setNicHandle(nicHandleId);
            if (nicHandleDAO.getNumberOfAccountsForIdAndNicHandle(criteria) > 0)
                throw new NicHandleIsAccountBillingContactException(nicHandleId, nicHandleDB.getAccount().getId());
        }
    }

    /**
     * Compares VAT number in the given nicHandle and in the nicHandle in DB
     * if the VAT has changed, an e-mail is sent
     * @param nicHandle
     */
    private void notifyIfVatDataChanged(NicHandle nicHandle, NicHandle nicHandleDB, String loggedUserName)
            throws NicHandleNotFoundException, NicHandleEmailException {
        Validator.assertNotNull(nicHandle, "nic handle");
        NicHandle billingNH = this.getBillingNicFor(nicHandle);
        if (isVatDataChanged(nicHandle, nicHandleDB)){
            NicHandleEmailParameters params = new NicHandleEmailParameters(
                nicHandle,
                loggedUserName,
                (billingNH ==  null) ? null : billingNH.getNicHandleId()
            );
            try {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.VAT_CHANGE.getId(), params);
            } catch (Exception e) {
                logger.error("Problem with sending email while changing vat.", e);
                throw new NicHandleEmailException(nicHandle.getNicHandleId(), e);
            }
        }
    }

    private boolean isVatDataChanged(NicHandle nicHandle, NicHandle nicHandleDB) {
        String vatNo;
        String vatNoDB;
        if (nicHandleDB.getVat() == null || nicHandleDB.getVat().getVatNo() == null || nicHandleDB.getVat().getVatNo().trim().equals("")) {
            vatNoDB = "";
        } else {
            vatNoDB = nicHandleDB.getVat().getVatNo();
        }
        if (nicHandle.getVat() == null || nicHandle.getVat().getVatNo() == null || nicHandle.getVat().getVatNo().trim().equals("")) {
            vatNo = "";
        } else {
            vatNo = nicHandle.getVat().getVatNo();
        }
        boolean vatNoChanged = !vatNo.equals(vatNoDB);

        String vatCategoryDB =  Validator.isEmpty(nicHandleDB.getVatCategory()) ? "" : nicHandleDB.getVatCategory();
        String vatCategory = Validator.isEmpty(nicHandle.getVatCategory()) ? "" : nicHandle.getVatCategory();
        boolean vatCategoryChanged = !vatCategory.equals(vatCategoryDB);
        return vatNoChanged || vatCategoryChanged;
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    private void confirmAccountActive(long id)
            throws AccountNotActiveException, AccountNotFoundException {
        String status = nicHandleDAO.getAccountStatus(id);
        if (status == null)
            throw new AccountNotFoundException(id);
        if (!"Active".equals(status))
            throw new AccountNotActiveException(id);
    }
    
    @Override
    public void removeDeletedNichandles() {   
    	nicHandleDAO.deleteMarkedNichandles();
    }

    @Override
    public void delete(String nicHandle) {
    	nicHandleDAO.deleteById(nicHandle);
    }        

    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
    	userService.addUserPermission(nicHandleId, permissionName);
    }
    
    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
    	userService.removeUserPermission(nicHandleId, permissionName);
    }
    
    @Override
    public NewAccount createDirectAccount(String name, String companyName,
    		String email, String address, String country, String county,
    		Set<String> phones, Set<String> faxes, String vatNo,
    		String password, boolean useTfa) throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, NicHandleEmailException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException {
    	NicHandle nh = createNicHandle(
    			name, companyName, email, address, county, country, 
    			applicationConfig.getGuestAccountId(), "", 
    			phones, faxes, 
    			"Direct Account Creation", 
    			null, 
    			vatNo, 
    			true, 
    			null, false, true);
    	userService.changePassword(nh.getNicHandleId(), password, nh.getNicHandleId());
    	userService.addUserToGroup(nh.getNicHandleId(), Level.Direct, nh.getNicHandleId());
    	String secret = userService.changeTfa(nh.getNicHandleId(), useTfa);
        exportChange(nh);
        sendCreateDirectEmail(nh);
    	return new NewAccount(nh.getNicHandleId(), secret);
    }

    private void sendCreateDirectEmail(NicHandle nicHandle) throws NicHandleEmailException {
        NicHandleEmailParameters params = new NicHandleEmailParameters(nicHandle, null, null);
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(), params);
        } catch (Exception e) {
            logger.error("Problem with sending email while creating direct nh: " + nicHandle.getName(), e);
            throw new NicHandleEmailException(nicHandle.getNicHandleId(), e);
        }
    }

    @Override
    public String useToken(String token, String nicHandleId) throws TokenExpiredException {    
    	return userService.useToken(token, nicHandleId);
    }
}



