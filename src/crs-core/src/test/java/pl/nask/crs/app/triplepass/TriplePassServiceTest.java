package pl.nask.crs.app.triplepass;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/application-services-config.xml"})
public class TriplePassServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    TriplePassAppService triplePassAppService;

    @Resource
    AuthenticationService authenticationService;

    @Resource
    TicketSearchService ticketSearchService;
    
    @Resource
    TicketService ticketService;

    @Resource
    PaymentService paymentService;
    
    @Resource
    DomainSearchService domainSearchService;

    @Resource
    TriplePassSupportService tripplePassSupportService;

    @Resource
    TransactionDAO transactionDAO;

    private ReservationSearchCriteria readyCriteria;

    private ReservationSearchCriteria notReadyCriteria;

    {
        readyCriteria = ReservationSearchCriteria.newReadyForSettlementInstance(true);
        readyCriteria.setBillingNH("APITEST-IEDR");
        notReadyCriteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        notReadyCriteria.setBillingNH("APITEST-IEDR");
    }

    @Test
    public void charityDomainFinancialCheckTest() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(259926);
        AssertJUnit.assertEquals(1, ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(1, ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW, ticket.getFinancialStatus());

        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        boolean result = triplePassAppService.triplePass(user, "charityDomainTechPassed.ie");
        AssertJUnit.assertTrue("domain created", result);
    }

    @Test
    public void CCDomainFinancialCheckTest() throws Exception {
        long ticketId = 259927;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(1, ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(1, ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        List<Reservation> readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(4, readyReservationsBefore);
        Reservation reservation = paymentService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        Assert.assertNull(transaction.getFinanciallyPassedDate());

        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        boolean result = triplePassAppService.triplePass(user, "createCCDomainTechPassed.ie");
        AssertJUnit.assertTrue("domain created", result);

        AssertJUnit.assertEquals(1, ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(1, ticket.getTechStatus().getId());
        notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(4, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        reservation = paymentService.getReservationForTicket(ticketId);
        transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void ADPDomainFinanacialCheckWithReservationTest() throws Exception {
        long ticketId = 259928L;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(1, ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(1, ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        List<Reservation> readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(4, readyReservationsBefore);
        Reservation reservation = paymentService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        Assert.assertNull(transaction.getFinanciallyPassedDate());

        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        boolean result = triplePassAppService.triplePass(user, "adpDomain.ie");

        AssertJUnit.assertTrue("domain created", result);
        notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(notReadyReservationsBefore - 1, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        reservation = paymentService.getReservationForTicket(ticketId);
        transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void ADPDomainFinanacialCheckWithoutReservationTest() throws Exception {
        long ticketId = 259929;
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(1, ticket.getAdminStatus().getId());
        AssertJUnit.assertEquals(1, ticket.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW, ticket.getFinancialStatus());
        List<Reservation> notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        List<Reservation> readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        int notReadyReservationsBefore = notReadyReservations.size();
        int readyReservationsBefore = readyReservations.size();
        AssertJUnit.assertEquals(5, notReadyReservationsBefore);
        AssertJUnit.assertEquals(4, readyReservationsBefore);

        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
        boolean result = triplePassAppService.triplePass(user, "createDomainRegistrarBasic3.ie");
        AssertJUnit.assertTrue("domain created", result);
        
        notReadyReservations = paymentService.findReservations(notReadyCriteria, 0, 10, null).getResults();
        readyReservations = paymentService.findReservations(readyCriteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(notReadyReservationsBefore, notReadyReservations.size());
        AssertJUnit.assertEquals(readyReservationsBefore + 1, readyReservations.size());
        Reservation reservation = paymentService.getReservationForTicket(ticketId);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        Assert.assertNotNull(transaction.getFinanciallyPassedDate());
    }
    
    @Test
    public void testPromoteTicketToDomain() throws TicketNotFoundException, DomainNotFoundException, TicketIllegalStateException {
    	long ticketId = 259929;
    	ticketService.updateFinanacialStatus(ticketId, FinancialStatusEnum.PASSED, "aaa");
    	String domainName = tripplePassSupportService.promoteTicketToDomain(null, ticketId);
    	// the ticket should be deleted and the domain object should be created
    	
    	try {
    		ticketSearchService.getTicket(ticketId);
    		AssertJUnit.fail("There should be no ticket with id=" + ticketId + " anymore");
    	} catch (TicketNotFoundException e) {
    		// this was desired
    	}
    	
    	Domain d = domainSearchService.getDomain(domainName);
    	AssertJUnit.assertNotNull(d);
    }
    
    @Test(expectedExceptions=TicketIllegalStateException.class)
    public void testPromoteTicketToDomainFail() throws TicketIllegalStateException, TicketNotFoundException {
    	long ticketId = 259929;
    	// Illegal state exception should be thrown
    	tripplePassSupportService.promoteTicketToDomain(null, ticketId);
    }

    @Test
    public void modifyTicketTriplePassTest() throws Exception {
        long ticketId = 259922;
        ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "aaa");

        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");

        String domainName = "thedomena.modpending.ie";
        boolean result = triplePassAppService.triplePass(user, domainName);
        AssertJUnit.assertTrue("domain created", result);

        try {
            ticketSearchService.getTicket(ticketId);
            AssertJUnit.fail("There should be no ticket with id=" + ticketId + " anymore");
        } catch (TicketNotFoundException e) {
            // this was desired
        }

        Domain d = domainSearchService.getDomain(domainName);

        AssertJUnit.assertNotNull(d);
        for (Contact contact : d.getAdminContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
        for (Contact contact : d.getTechContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
        for (Contact contact : d.getBillingContacts()) {
            AssertJUnit.assertEquals("IDL2-IEDR", contact.getNicHandle());
        }
    }
}
