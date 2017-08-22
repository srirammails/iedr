package pl.nask.crs.api.common;

import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;
import static pl.nask.crs.api.Helper.createBasicCreateRequest;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;
/**
 *
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class RegisterDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;
    
    @Resource 
    TransactionDAO transactionDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    TicketSearchService ticketSearchService;

    String domainName = "registerDomain.ie";

    @Test
    public void registerDomainTest() throws Exception {
        List<Reservation> reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        int noOfReservations = reservations.size();
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        Assert.assertNull(ticket.getCharityCode());

        reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        // no new reservations
        AssertJUnit.assertEquals(noOfReservations, reservations.size());
    }

    private List<Reservation> getAllReservationsForBillingNH(String billingNH) {
        ReservationSearchCriteria criteria = ReservationSearchCriteria.newInstance();
        criteria.setBillingNH(billingNH);
        return reservationDAO.getAllReservations(criteria);
    }

    @Test
    public void registerCCDomainTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        long newTicketId = crsCommonAppService.registerDomain(user, request, paymentRequest);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        Assert.assertNull(ticket.getCharityCode());

        Reservation Reservation = (Reservation)paymentService.getNotReadyReservation("APITEST-IEDR", domainName);
        Transaction t = transactionDAO.get(Reservation.getTransactionId());
        // new reservation added
        AssertJUnit.assertEquals("APITEST-IEDR", Reservation.getNicHandleId());
        AssertJUnit.assertEquals(domainName.toLowerCase(), Reservation.getDomainName());
        AssertJUnit.assertEquals(BigDecimal.valueOf(13.00).setScale(2), Reservation.getVatAmount());
        Assert.assertNotNull(t.getRealexPassRef());
        Assert.assertNotNull(t.getRealexAuthCode());
        Assert.assertNotNull(Reservation.getTicketId());
    }

    @Test
    public void registerCharityDomain() throws Exception {
    	List<Reservation> reservations = getAllReservationsForBillingNH("APITEST-IEDR");
    	int noOfReservations = reservations.size();
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        RegistrationRequestVO request = createBasicCreateRequest(domainName, "APITEST-IEDR");
        request.setCharityCode("CHY123");
        long newTicketId = crsCommonAppService.registerDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(domainName.toLowerCase(), ticket.getOperation().getDomainNameField().getNewValue());
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.REG, ticket.getOperation().getType());
        AssertJUnit.assertEquals(1, ticket.getDomainPeriod().getYears());
        AssertJUnit.assertEquals("CHY123", ticket.getCharityCode());

        // no reservation was added!
        reservations = getAllReservationsForBillingNH("APITEST-IEDR");
        AssertJUnit.assertEquals(noOfReservations, reservations.size());
    }
}
