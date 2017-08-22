package pl.nask.crs.api.common;

import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;
import static pl.nask.crs.api.Helper.createBasicCreateRequest;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.ticket.services.impl.TicketServiceImpl;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class CancelTicketTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TicketService ticketService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    DomainService domainService;

    String domainName = "registerDomain.ie";
    AuthenticatedUserVO user;

    @BeforeMethod
	public void authenticate() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
    	user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }


    @Test
    public void uc048sc01Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.NEW.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());
       
        cancelTicket(newTicketId);
    }


	private void cancelTicket(long newTicketId) throws TicketNotFoundException,	CancelTicketException, PaymentException {
		Ticket ticket;
		Reservation Reservation = (Reservation)paymentService.getNotReadyReservation("APITEST-IEDR", domainName);
        AssertJUnit.assertNotNull(Reservation);

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(Reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
	}

    @Test
    public void uc048sc02Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();
        ticketService.updateAdminStatus(newTicketId, AdminStatusEnum.PASSED, "test");

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        cancelTicket(newTicketId);
    }

    @Test
    public void uc048sc03Test() throws Exception {
        long newTicketId = registerDomainWithCCPayment();
		((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, null, "test", null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        cancelTicket(newTicketId);
    }

    @Test
    public void uc048sc05Test() throws Exception {
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);
		((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, null, "test", null);
        
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        Reservation reservation = paymentService.getNotReadyReservation("APITEST-IEDR", domainName);
        AssertJUnit.assertNull(reservation);

        crsCommonAppService.cancel(user, newTicketId);

        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc06Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        domainService.getOrCreateAuthCode(user.getUsername(), domainName);
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = transferDomainWithCCPayment(domainName, domain.getAuthCode());

        domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation)reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc07Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        domainService.getOrCreateAuthCode(user.getUsername(), domainName);
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = transferDomainWithCCPayment(domainName, domain.getAuthCode());
        ticketService.updateAdminStatus(newTicketId, AdminStatusEnum.PASSED, "test");

        domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(438, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation)reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(18, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc08Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        domainService.getOrCreateAuthCode(user.getUsername(), domainName);
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = transferDomainWithCCPayment(domainName, domain.getAuthCode());
        ((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, null, "test", null);

        domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(486, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation)reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);

        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(transaction.isCancelled());
        Assert.assertNotNull(transaction.getCancelledDate());
        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(20, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test(expectedExceptions = CancelTicketException.class)
    public void uc048sc09Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = transferDomainWithCCPayment(domainName, domain.getAuthCode());
        ((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, FinancialStatusEnum.PASSED, "test", null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.PASSED.getId(), ticket.getFinancialStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation)reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());

        crsCommonAppService.cancel(user, newTicketId);
    }

    @Test
    public void uc048sc10Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        Domain domain = domainSearchService.getDomain(domainName);
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1, domain.getAuthCode());
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);
        ((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, null, "test", null);

        domain = domainSearchService.getDomain(domainName);
        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        Reservation reservation = paymentService.getNotReadyReservation("APITEST-IEDR", domainName);
        AssertJUnit.assertNull(reservation);

        crsCommonAppService.cancel(user, newTicketId);

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());
        ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test
    public void uc048sc11Test() throws Exception {
        String domainName = "thedomena.modpending.ie";
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName(domainName);
        Ticket ticket = ticketSearchService.find(criteria, 0 ,10, null).getResults().get(0);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.MOD, ticket.getOperation().getType());

        crsCommonAppService.cancel(user, ticket.getId());
        ticket = ticketSearchService.getTicket(ticket.getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.CANCELLED, ticket.getCustomerStatus());
    }

    @Test(expectedExceptions = CancelTicketException.class)
    public void uc048sc12Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        Domain domain = domainSearchService.getDomain(domainName);
        long newTicketId = transferDomainWithCCPayment(domainName, domain.getAuthCode());
        ((TicketServiceImpl) ticketService).updateStatuses(newTicketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, FinancialStatusEnum.PASSED, "test", null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());
        AssertJUnit.assertEquals(AdminStatusEnum.PASSED.getId(), ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.PASSED.getId(), ticket.getFinancialStatus().getId());
        AssertJUnit.assertEquals(CustomerStatusEnum.NEW, ticket.getCustomerStatus());

        crsCommonAppService.cancel(user, newTicketId);
    }

    private long registerDomainWithCCPayment() throws Exception {
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        return crsCommonAppService.registerDomain(user, request, paymentRequest);
    }

    private long transferDomainWithCCPayment(String domainName, String authCode) throws Exception {
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1, authCode);
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        return crsCommonAppService.transferDomain(user, request, paymentRequest);
    }

}
