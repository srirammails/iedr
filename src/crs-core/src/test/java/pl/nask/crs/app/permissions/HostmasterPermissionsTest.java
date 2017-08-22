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
public class HostmasterPermissionsTest extends AbstractPermissionTest {

    private final static String USER_NAME = "AAA906-IEDR";
    private final static Level USER_LEVEL = Level.Hostmaster;

    private final static String NOT_OWN_INVOICE_NUMBER = "103";

    private final static long NOT_OWN_TICKET_ID = 7;

    private final static String NOT_OWN_DOMAIN_NAME = "chriswilson.ie";

    private final static String NOT_OWN_NH_NAME = "AAG061-IEDR";
    private final static long NOT_OWN_TRANSACTION_ID = 68; 
    private final static String NOT_OWN_ORDER_ID ="20120621144809-D-7485777";

    @Override
    protected String getSystemDiscriminator() {
    	return "crs";
    }
    
    @Override
    protected String getUserName() {
        return USER_NAME;
    }

    @Override
    protected Level getGroup() {
        return USER_LEVEL;
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void registrationRequest() {
        requestRegistration(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void topUpDeposit() {
        topUpDeposit(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void accessXmlInvoice() {
        accessXmlInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void accessPdfInvoice() {
        accessPdfInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void accessNotOwnInvoice() {
        accessXmlInvoice(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void pay() {
        pay(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void transferRequest() {
        transferRequest(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelTicket() {
        cancelTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void cancelNotOwnTicket() {
        cancelTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void placeDomainInVoluntaryNRP() {
        placeInVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void removeDomainFromVoluntaryNRP() {
        removeFromVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void placeNotOwnDomainInVoluntaryNRP() {
        placeInVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void removeNotOwnDomainFromVoluntaryNRP() {
        removeFromVoluntaryNRP(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyDomain() {
        modifyDomain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyDomainNameservers() {
        modifyNameservers(authenticatedUser, Arrays.asList(NOT_OWN_DOMAIN_NAME));
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomain() {
        modifyDomain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void modifyNotOwnDomainNameservers() {
        modifyNameservers(authenticatedUser, Arrays.asList(NOT_OWN_DOMAIN_NAME));
    }

    @Test
    public void editTicket() {
        editTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void editNotOwnTicket() {
        editTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void viewTicket() {
        viewTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void viewNotOwnTicket() {
        viewTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reauthoriseCCTransaction() {
        reauthoriseCCTransaction(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewUserDeposit() {
        viewUserDeposit(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findUserHistoricalDeposits() {
        findUserHistoricalDeposits(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getProductPrice() {
        getProductPrice(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
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

    @Test(expectedExceptions=AccessDeniedException.class)
    public void getValidVat() {
        getValidVat(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getDepositLimits() {
        getDepositLimits(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTopUpHistory() {
        getTopUpHistory(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendEmailWithInvoices() {
        sendEmailWithInvoices(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void sendEmailWithNotOwnInvoices() {
        sendEmailWithInvoices(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotSettledReservations() {
        getNotSettledReservations(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNotSettledReservationsTotals() {
        getNotSettledReservationsTotals(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findHistoricalReservations() {
        findHistoricalReservations(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getSettledTransactionHistory() {
        getSettledTransactionHistory(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getTransactionToReauthorise() {
        getTransactionToReauthorise(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findHistoricalTransactions() {
        findHistoricalTransactions(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void findUserInvoices() {
        findUserInvoices(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getInvoiceInfo() {
        getInvoiceInfo(authenticatedUser, NOT_OWN_INVOICE_NUMBER);
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

    @Test(expectedExceptions = AccessDeniedException.class)
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

    @Test
    public void createBulkTransferProcess() {
        createBulkTransferProcess(authenticatedUser);
    }

    @Test
    public void addDomains() {
        addDomains(authenticatedUser);
    }

    @Test
    public void findTransfers() {
        findTransfers(authenticatedUser);
    }

    @Test
    public void getTransferRequest() {
        getTransferRequest(authenticatedUser);
    }

    @Test
    public void removeDomain() {
        removeDomain(authenticatedUser);
    }

    @Test
    public void closeTransferRequest() {
        closeTransferRequest(authenticatedUser);
    }

    @Test
    public void forceCloseTransferRequest() {
        forceCloseTransferRequest(authenticatedUser);
    }

    @Test
    public void transferAll() {
        transferAll(authenticatedUser);
    }

    @Test
    public void transferValid() {
        transferValid(authenticatedUser);
    }

    @Test
    public void view() {
        view(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void viewNotOwn() {
        view(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void viewPlain() {
        viewPlain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void viewPlainNotOwn() {
        viewPlain(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void edit() {
        edit(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void editNotOwn() {
        edit(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void save() {
        save(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
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
        isEventValid(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
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
        updateHolderType(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
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

    @Test(expectedExceptions = AccessDeniedException.class)
    public void revertToBillable() {
        revertToBillable(authenticatedUser);
    }

    @Test
    public void checkPayAvailable() {
        checkPayAvailable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void checkPayAvailableNotOwn() {
        checkPayAvailable(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
    public void modifyRenewalMode() {
        modifyRenewalMode(authenticatedUser, NOT_OWN_DOMAIN_NAME);
    }

    @Test
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
        historyTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void historyNotOwnTicket() {
        historyTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void reviseTicket() {
        reviseTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void reviseNotOwnTicket() {
        reviseTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void checkOutTicket() {
        checkOutTicket(authenticatedUser);
    }

    @Test
    public void checkInTicket() {
        checkInTicket(authenticatedUser);
    }

    @Test
    public void alterStatusTicket() {
        alterStatusTicket(authenticatedUser);
    }

    @Test
    public void reassignTicket() {
        reassignTicket(authenticatedUser);
    }

    @Test
    public void acceptTicket() {
        acceptTicket(authenticatedUser);
    }

    @Test
    public void acceptTicket2() {
        acceptTicket2(authenticatedUser);
    }

    @Test
    public void rejectTicket() {
        rejectTicket(authenticatedUser);
    }

    @Test
    public void rejectTicket2() {
        rejectTicket2(authenticatedUser);
    }

    @Test
    public void updateTicket() {
        updateTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void updateNotOwnTicket() {
        updateTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void updateTicket2() {
        updateTicket2(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void updateNotOwnTicket2() {
        updateTicket2(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
    public void simpleUpdateTicket() {
        simpleUpdateTicket(authenticatedUser, NOT_OWN_TICKET_ID);
    }

    @Test
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

    @Test
    public void getAccount() {
        getAccount(authenticatedUser);
    }

    @Test
    public void historyAccount() {
        historyAccount(authenticatedUser);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() {
        alterStatusAccount(authenticatedUser);
    }

    @Test
    public void saveAccount() {
        saveAccount(authenticatedUser);
    }

    @Test
    public void createAccount() {
        createAccount(authenticatedUser);
    }

    @Test
    public void getNH() {
        getNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void getNotOwnNH() {
        getNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void historyNH() {
        historyNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void historyNotOwnNH() {
        historyNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void alterStatusNH() {
        alterStatusNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void alterStatusNotOwnNH() {
        alterStatusNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveNH() {
        saveNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveNotOwnNH() {
        saveNH(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveNewPassword() {
        saveNewPassword(authenticatedUser, USER_NAME);
    }

    @Test
    public void saveNewPasswordNotOwn() {
        saveNewPassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void changePassword() {
        changePassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void changePasswordNotOwn() {
        changePassword(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void resetPassword() {
        resetPassword(authenticatedUser, USER_NAME, IP);
    }

    @Test
    public void resetPasswordNotOwn() {
        resetPassword(authenticatedUser, NOT_OWN_NH_NAME, IP);
    }

    @Test
    public void createNH() {
        createNH(authenticatedUser);
    }

    @Test
    public void getDefaults() {
        getDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void getDefaultsNotOwn() {
        getDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveDefaults() {
        saveDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void saveDefaultsNotOwn() {
        saveDefaults(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void removeUserPermission() {
        removeUserPermission(authenticatedUser);
    }

    @Test
    public void addUserPermission() {
        addUserPermission(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Test
    public void changeTfaOwn() {
        changeTfa(authenticatedUser, USER_NAME);
    }

    @Test
    public void changeTfaNotOwn() {
        changeTfa(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    public void changeVATCategory() {
        changeVATCategory(authenticatedUser, NOT_OWN_NH_NAME);
    }

    @Override
    public void changeCountryAffectsVATCategory() {
        changeCountryAffectsVATCategory(authenticatedUser, NOT_OWN_NH_NAME);
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void getTransactionInfoById() {    	
    	getTransactionInfoById(authenticatedUser, NOT_OWN_TRANSACTION_ID);
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void getTransactionInfoByOrderId() {
    	getTransactionInfoByOrderId(authenticatedUser, NOT_OWN_ORDER_ID);    	
    }
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void findDeletedDomains() {
    	DeletedDomainSearchCriteria crit = new DeletedDomainSearchCriteria();
    	crit.setBillingNH(NOT_OWN_NH_NAME);
    	findDeletedDomains(authenticatedUser, crit);
    }
    
}
