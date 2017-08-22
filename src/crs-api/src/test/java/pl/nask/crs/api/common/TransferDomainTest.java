package pl.nask.crs.api.common;

import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ReservationDAO;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class TransferDomainTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSCommonAppService crsCommonAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    ReservationDAO reservationDAO;
    
    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    DomainSearchService domainSearchService;

    @Resource
    DomainService domainService;

    @Test
    public void transferDomainTest() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";

        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1, domain.getAuthCode());
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(390, domain.getDsmState().getId());
    }

    @Test
    public void transferDomainCCTest() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";

        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(17, domain.getDsmState().getId());

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", false, 1, domain.getAuthCode());
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        long newTicketId = crsCommonAppService.transferDomain(user, request, paymentRequest);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(390, domain.getDsmState().getId());

        ReservationSearchCriteria criteria = ReservationSearchCriteria.newReadyForSettlementInstance(false);
        criteria.setDomainName(domainName);
        Reservation reservation = (Reservation)reservationDAO.getReservations(criteria, 0, 1, null).getResults().get(0);
        AssertJUnit.assertEquals("APITEST-IEDR", reservation.getNicHandleId());
        AssertJUnit.assertEquals(domainName.toLowerCase(), reservation.getDomainName());
        AssertJUnit.assertEquals(BigDecimal.valueOf(13.00).setScale(2), reservation.getVatAmount());
        Transaction t = transactionDAO.get(reservation.getTransactionId());
        
        Assert.assertNotNull(t.getRealexPassRef());
        Assert.assertNotNull(t.getRealexAuthCode());
        Assert.assertNotNull(reservation.getTicketId());
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());
        AssertJUnit.assertFalse(reservation.isReadyForSettlement());
    }

    @Test
    public void transferDomainCharityTest() throws Exception {
        String domainName = "transferDomainUC006SC06.ie";

        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(121, domain.getDsmState().getId());

        AuthenticatedUserVO user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(user.getUsername(), domainName).getAuthcode();
        TransferRequestVO request = createBasicTransferRequest(domainName, "APIT1-IEDR", true, 1, authCode);
        long newTicketId = crsCommonAppService.transferDomain(user, request, null);

        Ticket ticket = ticketSearchService.getTicket(newTicketId);
        AssertJUnit.assertEquals(DomainOperation.DomainOperationType.XFER, ticket.getOperation().getType());

        domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(403, domain.getDsmState().getId());
    }
}
