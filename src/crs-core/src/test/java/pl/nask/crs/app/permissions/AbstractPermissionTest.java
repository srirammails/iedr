package pl.nask.crs.app.permissions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.Permission;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
public abstract class AbstractPermissionTest extends AbstractTransactionalTestNGSpringContextTests implements TestMethods {

    protected final static String IP = "1.1.1.1";

    @Resource
    AuthenticationService authenticationService;

    @Resource
    CommonAppService commonAppService;

    @Resource
    PaymentAppService paymentAppService;

    @Resource
    DomainAppService domainAppService;

    @Resource
    TicketAppService ticketAppService;

    @Resource
    BulkTransferAppService bulkTransferAppService;

    @Resource
    ReportsAppService reportsAppService;

    @Resource
    ConfigAppService configAppService;

    @Resource
    AccountAppService accountAppService;

    @Resource
    NicHandleAppService nicHandleAppService;
    
    @Resource
    UserAppService userAppService;

    @Resource
    UserDAO userDAO;

    @Resource
    DomainDAO domainDAO;

    @Resource
    NicHandleDAO nicHandleDAO;
    
    @Resource 
    CountryFactory countryFactory;

    protected AuthenticatedUser authenticatedUser;

    protected abstract String getUserName();

    protected abstract Level getGroup();

    @BeforeClass
    public void setUp() throws AuthenticationException {
        this.authenticatedUser = authenticate(getUserName());
    }
    
    protected abstract String getSystemDiscriminator();
    
    protected AuthenticatedUser authenticate(String userName) throws AuthenticationException {
    	return authenticationService.authenticate(userName, "Passw0rd!", false, IP, false, null, true, getSystemDiscriminator());
    }

    @Test
    public void isInProperGroupTest() {
        User user = userDAO.get(authenticatedUser.getUsername());
        Assert.assertTrue(user.hasGroup(getGroup()), "User:" + authenticatedUser.getUsername() + " does not belong to group:" + getGroup());
        System.out.println("User's permissions:");
        for (Group g: user.getPermissionGroups()) {
        	System.out.println("User role: " + g + " with permissions: ");
        	for (Permission p: g.getPermissions()) {
        		System.out.println(p);
        	}
        }
        
    }

    protected void requestRegistration(AuthenticatedUser user) {
        try {
            commonAppService.registerDomain(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip only AccessDeniedException is important
        }
    }

    protected void topUpDeposit(AuthenticatedUser user) {
        try {
            paymentAppService.depositFunds(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void accessXmlInvoice(AuthenticatedUser user, String invoiceNumber) {
        try {
            paymentAppService.viewXmlInvoice(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void accessPdfInvoice(AuthenticatedUser user, String invoiceNumber) {
        try {
            paymentAppService.viewPdfInvoice(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void pay(AuthenticatedUser user) {
        try {
            paymentAppService.pay(user, null, null, null, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferRequest(AuthenticatedUser user) {
        try {
            commonAppService.transfer(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cancelTicket(AuthenticatedUser user, long ticketId) {
        try {
            commonAppService.cancel(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void placeInVoluntaryNRP(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.enterVoluntaryNRP(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeFromVoluntaryNRP(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.removeFromVoluntaryNRP(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyDomain(AuthenticatedUser user, String domainName) {
        try {
            commonAppService.modifyDomain(user, domainName, null, null, null, null, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyNameservers(AuthenticatedUser user, List<String> domainNames) {
        try {
            commonAppService.modifyNameservers(user, domainNames, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void editTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.edit(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.view(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reauthoriseCCTransaction(AuthenticatedUser user) {
        try {
            commonAppService.reauthoriseTransaction(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewUserDeposit(AuthenticatedUser user) {
        try {
            paymentAppService.viewUserDeposit(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findUserHistoricalDeposits(AuthenticatedUser user) {
        try {
            paymentAppService.findUserHistoricalDeposits(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getProductPrice(AuthenticatedUser user) {
        try {
            paymentAppService.getProductPrice(user, 1, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDomainPricing(AuthenticatedUser user) {
        try {
            paymentAppService.getDomainPricing(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getVatRate(AuthenticatedUser user) {
        try {
            paymentAppService.getVatRate(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getPrice(AuthenticatedUser user) {
        try {
            paymentAppService.getPrice(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void addPrice(AuthenticatedUser user) {
        try {
            paymentAppService.addPrice(user, null, null, BigDecimal.ZERO, 0, null, null, false, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void modifyPrice(AuthenticatedUser user) {
        try {
            paymentAppService.modifyPrice(user, null, null, BigDecimal.ZERO, 0, null, null, false, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void addVatRate(AuthenticatedUser user) {
        try {
            paymentAppService.addVatRate(user, null, null, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void invalidateVat(AuthenticatedUser user) {
        try {
            paymentAppService.invalidate(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getValidVat(AuthenticatedUser user) {
        try {
            paymentAppService.getValid(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDepositLimits(AuthenticatedUser user) {
        try {
            paymentAppService.getDepositLimits(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTopUpHistory(AuthenticatedUser user) {
        try {
            paymentAppService.getTopUpHistory(user, null, null, 0, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber) {
        try {
            paymentAppService.sendEmailWithInvoices(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNotSettledReservations(AuthenticatedUser user) {
        try {
            paymentAppService.getNotSettledReservations(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNotSettledReservationsTotals(AuthenticatedUser user) {
        try {
            paymentAppService.getNotSettledReservationsTotals(user, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findHistoricalReservations(AuthenticatedUser user) {
        try {
            paymentAppService.findHistoricalReservations(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getSettledTransactionHistory(AuthenticatedUser user) {
        try {
            paymentAppService.getSettledTransactionHistory(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTransactionToReauthorise(AuthenticatedUser user) {
        try {
            paymentAppService.getTransactionToReauthorise(user, 0, 10, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findHistoricalTransactions(AuthenticatedUser user) {
        try {
            paymentAppService.findHistoricalTransactions(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findUserInvoices(AuthenticatedUser user) {
        try {
            paymentAppService.findUserInvoices(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getInvoiceInfo(AuthenticatedUser user, String invoiceNumber) {
        try {
            paymentAppService.getInvoiceInfo(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDeposits(AuthenticatedUser user) {
        try {
            paymentAppService.findDeposits(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDepositWithHistory(AuthenticatedUser user) {
        try {
            paymentAppService.findDepositWithHistory(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getReadyADPTransactionsReport(AuthenticatedUser user) {
        try {
            paymentAppService.getReadyADPTransactionsReport(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findInvoices(AuthenticatedUser user) {
        try {
            paymentAppService.findInvoices(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findAllPrices(AuthenticatedUser user) {
        try {
            paymentAppService.findAllPrices(user, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewDeposit(AuthenticatedUser user) {
        try {
            paymentAppService.viewDeposit(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void correctDeposit(AuthenticatedUser user) {
        try {
            paymentAppService.correctDeposit(user, null, 0 ,null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void depositFundsOffline(AuthenticatedUser user) {
        try {
            paymentAppService.depositFundsOffline(user, null, 0 , null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void registerGIBODomain(AuthenticatedUser user) {
        try {
            commonAppService.registerGIBODomain(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isTransferPossible(AuthenticatedUser user) {
        try {
            commonAppService.isTransferPossible(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void zoneCommit(AuthenticatedUser user) {
        try {
            commonAppService.zoneCommit(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void zonePublished(AuthenticatedUser user) {
        try {
            commonAppService.zonePublished(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void zoneUnpublished(AuthenticatedUser user) {
        try {
            commonAppService.zoneUnpublished(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createBulkTransferProcess(AuthenticatedUser user) {
        try {
            bulkTransferAppService.createBulkTransferProcess(user, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addDomains(AuthenticatedUser user) {
        try {
            bulkTransferAppService.addDomains(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTransfers(AuthenticatedUser user) {
        try {
            bulkTransferAppService.findTransfers(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTransferRequest(AuthenticatedUser user) {
        try {
            bulkTransferAppService.getTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeDomain(AuthenticatedUser user) {
        try {
            bulkTransferAppService.removeDomain(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void closeTransferRequest(AuthenticatedUser user) {
        try {
            bulkTransferAppService.closeTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceCloseTransferRequest(AuthenticatedUser user) {
        try {
            bulkTransferAppService.forceCloseTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferAll(AuthenticatedUser user) {
        try {
            bulkTransferAppService.transferAll(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferValid(AuthenticatedUser user) {
        try {
            bulkTransferAppService.transferValid(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void view(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.view(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewPlain(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.viewPlain(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void edit(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.edit(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void save(AuthenticatedUser user, String domainName) {
        try {
            Domain d = domainDAO.get(domainName);
            domainAppService.save(user, d);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void search(AuthenticatedUser user) {
        try {
            domainAppService.search(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchFull(AuthenticatedUser user) {
        try {
            domainAppService.searchFull(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkDomainExists(AuthenticatedUser user) {
        try {
            domainAppService.checkDomainExists(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isEventValid(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.isEventValid(user, domainName, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
        	//skip
        }
    }

    protected void checkAvailability(AuthenticatedUser user) {
        try {
            domainAppService.checkAvailability(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceDSMEvent(AuthenticatedUser user) {
        try {
            domainAppService.forceDSMEvent(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceDSMState(AuthenticatedUser user) {
        try {
            domainAppService.forceDSMState(user, null, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDsmStates(AuthenticatedUser user) {
        try {
            domainAppService.getDsmStates(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateHolderType(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.updateHolderType(user, domainName, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void enterWipo(AuthenticatedUser user) {
        try {
            domainAppService.enterWipo(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void exitWipo(AuthenticatedUser user) {
        try {
            domainAppService.exitWipo(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void revertToBillable(AuthenticatedUser user) {
        try {
            domainAppService.revertToBillable(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkPayAvailable(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.checkPayAvailable(user, Arrays.asList(domainName));
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyRenewalMode(AuthenticatedUser user, String domainName) {
        try {
            domainAppService.modifyRenewalMode(user, Arrays.asList(domainName), null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTotalDomains(AuthenticatedUser user) {
        try {
            reportsAppService.findTotalDomains(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void findTotalDomainsPerDate(AuthenticatedUser user) {
        try {
            reportsAppService.findTotalDomainsPerDate(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void findTotalDomainsPerClass(AuthenticatedUser user) {
        try {
            reportsAppService.findTotalDomainsPerClass(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void historyTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.history(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reviseTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.revise(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkOutTicket(AuthenticatedUser user) {
        try {
            ticketAppService.checkOut(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkInTicket(AuthenticatedUser user) {
        try {
            ticketAppService.checkIn(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusTicket(AuthenticatedUser user) {
        try {
            ticketAppService.alterStatus(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reassignTicket(AuthenticatedUser user) {
        try {
            ticketAppService.reassign(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void acceptTicket(AuthenticatedUser user) {
        try {
            ticketAppService.accept(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void acceptTicket2(AuthenticatedUser user) {
        try {
            ticketAppService.accept(user, 0, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void rejectTicket(AuthenticatedUser user) {
        try {
            ticketAppService.reject(user, 0, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void rejectTicket2(AuthenticatedUser user) {
        try {
            ticketAppService.reject(user, 0, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.update(user, ticketId, null, null, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateTicket2(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.update(user, ticketId, null, null, null, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void simpleUpdateTicket(AuthenticatedUser user, long ticketId) {
        try {
            ticketAppService.simpleUpdate(user, ticketId, null, null, null, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getEntries(AuthenticatedUser user) {
        try {
            configAppService.getEntries(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void updateEntry(AuthenticatedUser user) {
        try {
            configAppService.updateEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void createEntry(AuthenticatedUser user) {
        try {
            configAppService.createEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    protected void getEntry(AuthenticatedUser user) {
        try {
            configAppService.getEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getAccount(AuthenticatedUser user) {
        try {
            accountAppService.get(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void historyAccount(AuthenticatedUser user) {
        try {
            accountAppService.history(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusAccount(AuthenticatedUser user) {
        try {
            accountAppService.alterStatus(user, 0, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveAccount(AuthenticatedUser user) {
        try {
            accountAppService.save(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createAccount(AuthenticatedUser user) {
        try {
            accountAppService.create(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNH(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.get(user, nh);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        	e.printStackTrace();
        }
    }

    protected void historyNH(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.history(user, nh, 0, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusNH(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.alterStatus(user, nh, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveNH(AuthenticatedUser user, String nh) {
        try {
            NicHandle nic = nicHandleDAO.get(nh);
            nicHandleAppService.save(user, nic, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }
    
    protected void saveNewPassword(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.saveNewPassword(user, null, null, nh);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void changePassword(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.changePassword(user, null, null, null, nh);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void resetPassword(AuthenticatedUser user, String nh, String ip) {
        try {
            nicHandleAppService.resetPassword(user, nh, ip);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createNH(AuthenticatedUser user) {
        try {
            nicHandleAppService.create(user, null, null, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDefaults(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.getDefaults(user, nh);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveDefaults(AuthenticatedUser user, String nh) {
        try {
            nicHandleAppService.saveDefaults(user, nh, null, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeUserPermission(AuthenticatedUser user) {
        try {
            nicHandleAppService.removeUserPermission(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addUserPermission(AuthenticatedUser user, String nicHandleId) {
        try {
            nicHandleAppService.addUserPermission(user, nicHandleId, "Passw0rd!");
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        	e.printStackTrace();
        	
        }
    }
    
    protected void changeTfa(AuthenticatedUser user, String nicHandleId) {
    	try {
    		userAppService.changeTfa(user, nicHandleId, false);
    	} catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        } 
    }
    
    protected void changeVATCategory(AuthenticatedUser user, String nicHandleId) {
    	try {
    		NicHandle nic = nicHandleAppService.get(user, nicHandleId);
    		if ("A".equals(nic.getVatCategory())) {
    			nic.setVatCategory("B");    			
    		} else {
    			nic.setVatCategory("A");
    		}
    		nicHandleAppService.save(user, nic, "vat category change");
    	} catch (AccessDeniedException e) {
    		throw e;
    	} catch (Exception e) {
    		// skip
    	}
    }
    
    protected void changeCountryAffectsVATCategory(AuthenticatedUser user, String nicHandleId) {
    	try {
    		NicHandle nic = nicHandleAppService.get(user, nicHandleId);
    		String vatCategory = nic.getVatCategory();
    		List<Country> countries = countryFactory.getEntries();
    		for (Country c: countries) {
    			if (!Validator.isEqual(vatCategory, c.getVatCategory())) {
    				nic.setCountry(c.getName());
    				if (c.getCounties().isEmpty()) {
    					nic.setCounty(null);
    				} else {
    					nic.setCounty(c.getCounties().get(0));
    				}
    			}
    		}
    		nicHandleAppService.save(user, nic, "vat category change");
    	} catch (AccessDeniedException e) {
    		throw e;
    	} catch (Exception e) {
    		// skip
    	}
    }

	public void getTransactionInfoByOrderId(AuthenticatedUser user, String orderId) {
		try {
			paymentAppService.getTransactionInfo(user, orderId);
		} catch (AccessDeniedException e) {
			throw e;
		} catch (Exception e) {
			// skip
		}
	}
	
	public void getTransactionInfoById(AuthenticatedUser user, long transactionId) {
		try {
			paymentAppService.getTransactionInfo(user, transactionId);
		} catch (AccessDeniedException e) {
			throw e;
		} catch (Exception e) {
			// skip
		}
	}
	
	public void findDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria crit) {
		try {
			domainAppService.findDeletedDomains(user, crit, 0, 1, null);
		} catch (AccessDeniedException e) {
			throw e;
		} catch (Exception e) {
			// skip
		}
	
	}

}
