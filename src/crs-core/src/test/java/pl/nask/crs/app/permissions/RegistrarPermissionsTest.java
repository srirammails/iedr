package pl.nask.crs.app.permissions;

import java.util.Arrays;

import org.testng.annotations.Test;

import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Level;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class RegistrarPermissionsTest extends AbstractPermissionTest {

    private final static String USER_NAME = "AAA22-IEDR";
    private final static Level USER_LEVEL = Level.Registrar;

    private final static String OWN_INVOICE_NUMBER = "104";
    private final static String NOT_OWN_INVOICE_NUMBER = "103";

    private final static long OWN_TICKET_ID = 259932;
    private final static long NOT_OWN_TICKET_ID = 7;

    private final static String OWN_DOMAIN_NAME = "webwebweb.ie";
    private final static String NOT_OWN_DOMAIN_NAME = "chriswilson.ie";

    private final static String OWN_NH_NAME = "AAA22-IEDR";
    private final static String NOT_OWN_NH_NAME = "AAG061-IEDR";
    
    private final static long OWN_TRANSACTION_ID = 70;  
    private final static long NOT_OWN_TRANSACTION_ID = 68; 
    private final static String OWN_ORDER_ID = "20120621144809-D-7481113";
    private final static String NOT_OWN_ORDER_ID ="20120621144809-D-7485777";

    @Override
    protected String getSystemDiscriminator() {
    	return "ws";
    }
    
    @Override
    protected String getUserName() {
        return USER_NAME;
    }

    @Override
    protected Level getGroup() {
        return USER_LEVEL;
    }

    @Test
    public void registrationRequest() {
        requestRegistration(authenticatedUser);
    }

    @Test
    public void topUpDeposit() {
        topUpDeposit(authenticatedUser);
    }

    @Test
    public void accessXmlInvoice() {
        accessXmlInvoice(authenticatedUser, OWN_INVOICE_NUMBER);
    }

    @Test
    public void accessPdfInvoice() {
        accessPdfInvoice(authenticatedUser, OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void accessNotOwnInvoice() {
        accessXmlInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test
    public void pay() {
        pay(authenticatedUser);
    }

    @Test
    public void transferRequest() {
        transferRequest(authenticatedUser);
    }

    @Test
    public void cancelTicket() {
        cancelTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelNotOwnTicket() {
        cancelTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void placeDomainInVoluntaryNRP() {
        placeInVoluntaryNRP(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test
    public void removeDomainFromVoluntaryNRP() {
        removeFromVoluntaryNRP(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void placeNotOwnDomainInVoluntaryNRP() {
        placeInVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void removeNotOwnDomainFromVoluntaryNRP() {
        removeFromVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void modifyDomain() {
        modifyDomain(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test
    public void modifyDomainNameservers() {
        modifyNameservers(authenticatedUser, Arrays.asList(OWN_DOMAIN_NAME));
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomain() {
        modifyDomain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomainNameservers() {
        modifyNameservers(authenticatedUser, Arrays.asList(NOT_OWN_DOMAIN_NAME, OWN_DOMAIN_NAME));
    }

    @Test
    public void editTicket() {
        editTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editNotOwnTicket() {
        editTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void viewTicket() {
        viewTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewNotOwnTicket() {
        viewTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void reauthoriseCCTransaction() {
        reauthoriseCCTransaction(authenticatedUser);
    }

    @Test
    public void viewUserDeposit() {
        viewUserDeposit(authenticatedUser);
    }

    @Test
    public void findUserHistoricalDeposits() {
        findUserHistoricalDeposits(authenticatedUser);
    }

    @Test
    public void getProductPrice() {
        getProductPrice(authenticatedUser);
    }

    @Test
    public void getDomainPricing() {
        getDomainPricing(authenticatedUser);
    }

    @Test
    public void getVatRate() {
        getVatRate(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getPrice() {
        getPrice(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void addPrice() {
        addPrice(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyPrice() {
        modifyPrice(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void addVatRate() {
        addVatRate(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void invalidateVat() {
        invalidateVat(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getValidVat() {
        getValidVat(authenticatedUser);
    }

    @Test
    public void getDepositLimits() {
        getDepositLimits(authenticatedUser);
    }

    @Test
    public void getTopUpHistory() {
        getTopUpHistory(authenticatedUser);
    }

    @Test
    public void sendEmailWithInvoices() {
        sendEmailWithInvoices(authenticatedUser, OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendEmailWithNotOwnInvoices() {
        sendEmailWithInvoices(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test
    public void getNotSettledReservations() {
        getNotSettledReservations(authenticatedUser);
    }

    @Test
    public void getNotSettledReservationsTotals() {
        getNotSettledReservationsTotals(authenticatedUser);
    }

    @Test
    public void findHistoricalReservations() {
        findHistoricalReservations(authenticatedUser);
    }

    @Test
    public void getSettledTransactionHistory() {
        getSettledTransactionHistory(authenticatedUser);
    }

    @Test
    public void getTransactionToReauthorise() {
        getTransactionToReauthorise(authenticatedUser);
    }

    @Test
    public void findHistoricalTransactions() {
        findHistoricalTransactions(authenticatedUser);
    }

    @Test
    public void findUserInvoices() {
        findUserInvoices(authenticatedUser);
    }

    @Test
    public void getInvoiceInfo() {
        getInvoiceInfo(authenticatedUser, OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotOwnInvoiceInfo() {
        getInvoiceInfo(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findAllPrices() {
        findAllPrices(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDeposits() {
        findDeposits(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findDepositWithHistory() {
        findDepositWithHistory(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getReadyADPTransactionsReport() {
        getReadyADPTransactionsReport(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findInvoices() {
        findInvoices(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewDeposit() {
        viewDeposit(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void correctDeposit() {
        correctDeposit(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void depositFundsOffline() {
        depositFundsOffline(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void registerGIBODomain() {
        registerGIBODomain(authenticatedUser);
    }

    @Test
    public void isTransferPossible() {
        isTransferPossible(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void zoneCommit() {
        zoneCommit(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void zonePublished() {
        zonePublished(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void zoneUnpublished() {
        zoneUnpublished(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createBulkTransferProcess() {
        createBulkTransferProcess(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void addDomains() {
        addDomains(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTransfers() {
        findTransfers(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransferRequest() {
        getTransferRequest(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void removeDomain() {
        removeDomain(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void closeTransferRequest() {
        closeTransferRequest(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void forceCloseTransferRequest() {
        forceCloseTransferRequest(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void transferAll() {
        transferAll(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void transferValid() {
        transferValid(authenticatedUser);
    }

    @Test
    public void view() {
        view(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewNotOwn() {
        view(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void viewPlain() {
        viewPlain(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewPlainNotOwn() {
        viewPlain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void edit() {
        edit(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editNotOwn() {
        edit(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void save() {
        save(authenticatedUser, OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNotOwn() {
        save(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void search() {
        search(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void searchFull() {
        searchFull(authenticatedUser);
    }

    @Test
    public void checkDomainExists() {
        checkDomainExists(authenticatedUser);
    }

    @Test
    public void isEventValid() {
        isEventValid(authenticatedUser, OWN_DOMAIN_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void isEventValidNotOwn() {
        isEventValid(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void checkAvailability() {
        checkAvailability(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void forceDSMEvent() {
        forceDSMEvent(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void forceDSMState() {
        forceDSMState(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDsmStates() {
        getDsmStates(authenticatedUser);
    }

    @Test
    public void updateHolderType() {
        updateHolderType(authenticatedUser, OWN_DOMAIN_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateHolderTypeNotOwn() {
        updateHolderType(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void enterWipo() {
        enterWipo(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void exitWipo() {
        exitWipo(authenticatedUser);
    }

    @Test
    public void revertToBillable() {
        revertToBillable(authenticatedUser);
    }

    @Test
    public void checkPayAvailable() {
        checkPayAvailable(authenticatedUser, OWN_DOMAIN_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkPayAvailableNotOwn() {
        checkPayAvailable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void modifyRenewalMode() {
        modifyRenewalMode(authenticatedUser, OWN_DOMAIN_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyRenewalModeNotOwn() {
        modifyRenewalMode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomains() {
        findTotalDomains(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomainsPerDate() {
        findTotalDomainsPerDate(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findTotalDomainsPerClass() {
        findTotalDomainsPerClass(authenticatedUser);
    }

    @Test
    public void historyTicket() {
        historyTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void historyNotOwnTicket() {
        historyTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void reviseTicket() {
        reviseTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reviseNotOwnTicket() {
        reviseTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() {
        checkOutTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() {
        checkInTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() {
        alterStatusTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() {
        reassignTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() {
        acceptTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicket2() {
        acceptTicket2(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() {
        rejectTicket(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicket2() {
        rejectTicket2(authenticatedUser);
    }

    @Test
    public void updateTicket() {
        updateTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateNotOwnTicket() {
        updateTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void updateTicket2() {
        updateTicket2(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateNotOwnTicket2() {
        updateTicket2(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void simpleUpdateTicket() {
        simpleUpdateTicket(authenticatedUser, OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void simpleUpdateNotOwnTicket() {
        simpleUpdateTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getEntries() {
        getEntries(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateEntry() {
        updateEntry(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createEntry() {
        createEntry(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getEntry() {
        getEntry(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccount() {
        getAccount(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void historyAccount() {
        historyAccount(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() {
        alterStatusAccount(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccount() {
        saveAccount(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createAccount() {
        createAccount(authenticatedUser);
    }

    @Test
    public void getNH() {
        getNH(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotOwnNH() {
        getNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void historyNH() {
        historyNH(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void historyNotOwnNH() {
        historyNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNH() {
        alterStatusNH(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNotOwnNH() {
        alterStatusNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveNH() {
        saveNH(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNotOwnNH() {
        saveNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveNewPassword() {
        saveNewPassword(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNewPasswordNotOwn() {
        saveNewPassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void changePassword() {
        changePassword(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void changePasswordNotOwn() {
        changePassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void resetPassword() {
        resetPassword(authenticatedUser, OWN_NH_NAME, IP);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void resetPasswordNotOwn() {
        resetPassword(authenticatedUser, NOT_OWN_NH_NAME, IP);
    }

    @Test
    public void createNH() {
        createNH(authenticatedUser);
    }

    @Test
    public void getDefaults() {
        getDefaults(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDefaultsNotOwn() {
        getDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveDefaults() {
        saveDefaults(authenticatedUser, OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveDefaultsNotOwn() {
        saveDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void removeUserPermission() {
        removeUserPermission(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void addUserPermission() {
        addUserPermission(authenticatedUser, OWN_NH_NAME);
    }

    @Test
    public void changeTfaOwn() {
    	changeTfa(authenticatedUser, OWN_NH_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void changeTfaNotOwn() {
    	changeTfa(authenticatedUser, NOT_OWN_NH_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void changeVATCategory() {    
    	changeVATCategory(authenticatedUser, OWN_NH_NAME);
    }
    
    @Test(expectedExceptions = AccessDeniedException.class)
    public void changeCountryAffectsVATCategory() {
    	changeCountryAffectsVATCategory(authenticatedUser, OWN_NH_NAME);	    	
    }
    
    @Test
    public void getTransactionInfoById() {    	
    	getTransactionInfoById(authenticatedUser, OWN_TRANSACTION_ID);
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void getTransactionInfoByIdNotOwn() {    	
    	getTransactionInfoById(authenticatedUser, NOT_OWN_TRANSACTION_ID);
    }
    
    @Test
    public void getTransactionInfoByOrderId() {
    	getTransactionInfoByOrderId(authenticatedUser, OWN_ORDER_ID);    	
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void getTransactionInfoByOrderIdNotOwn() {
    	getTransactionInfoByOrderId(authenticatedUser, NOT_OWN_ORDER_ID);    	
    }
    
    @Test
    public void findDeletedDomains() {
    	DeletedDomainSearchCriteria crit = new DeletedDomainSearchCriteria();
    	crit.setBillingNH(getUserName());
    	findDeletedDomains(authenticatedUser, crit);
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void findDeletedDomainsNotOwn() {
    	DeletedDomainSearchCriteria crit = new DeletedDomainSearchCriteria();
    	crit.setBillingNH(NOT_OWN_NH_NAME);
    	findDeletedDomains(authenticatedUser, crit);
    }
}
