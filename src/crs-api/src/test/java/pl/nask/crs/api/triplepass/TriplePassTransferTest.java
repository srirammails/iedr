package pl.nask.crs.api.triplepass;

import static pl.nask.crs.api.Helper.createBasicCCPaymentRequest;
import static pl.nask.crs.api.Helper.createBasicTransferRequest;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.impl.TicketServiceImpl;

import java.util.Collections;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassTransferTest extends TriplePassTest {

    @Test
    public void uc07sc01Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);            	

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    private void updateTicketStatuses(long ticketId, AdminStatusEnum as, TechStatusEnum ts) throws TicketNotFoundException {
        ((TicketServiceImpl) ticketService).updateStatuses(ticketId, as, ts, null, "test", null);
    }

    private void updateTicketStatuses(long ticketId, AdminStatusEnum as, TechStatusEnum ts, FinancialStatusEnum fs) throws TicketNotFoundException {
        ((TicketServiceImpl) ticketService).updateStatuses(ticketId, as, ts, fs, "test", null);
    }

    @Test
    public void uc07sc02Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc03Test() throws Exception {
        String domainName = "transferDomainUC006SC03.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 25);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc04Test() throws Exception {
        String domainName = "transferDomainUC007SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 113);

        long ticketId = transferDomainCharity(domainName, gainingNH);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);
        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationNotCreated(ticketId);
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 113);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc05Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 18);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc06Test() throws Exception {
        String domainName = "transferDomainUC006SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 19);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc07Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 20);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);
        
        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc08Test() throws Exception {
        String domainName = "transferDomainUC007SC08.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 21);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc09Test() throws Exception {
        String domainName = "transferDomainUC006SC07.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 81);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc10Test() throws Exception {
        String domainName = "transferDomainUC007SC10.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 49);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc12Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainADPWrongNameserver(domainName, gainingNH);

    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.STALLED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.NEW.getId(), t.getFinancialStatus().getId());
    }

    @Test
    public void uc07sc13Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 390);

    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.CANCELLED, "test");

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        assertDomainState(domainName, 17);
    }

    @Test
    public void uc07sc14Test() throws Exception {
        String domainName = "transferDomainUC006SC03.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 25);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc07sc15Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.PASSED, "test");
    	ticketService.updateFinanacialStatus(ticketId, FinancialStatusEnum.NEW, "test");
    	
        AuthenticatedUserVO user = authenticate(gainingNH);
        
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 1);
        
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.STALLED.getId(), t.getFinancialStatus().getId());
    }

    @Test
    public void uc07sc16Test() throws Exception {
        String domainName = "transferDomainUC007SC05.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 18);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 438);

    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.CANCELLED, "test");

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        assertDomainState(domainName, 18);
    }

    @Test
    public void uc07sc17Test() throws Exception {
        String domainName = "transferDomainUC006SC04.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 20);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);
        assertDomainState(domainName, 486);

    	ticketService.updateAdminStatus(ticketId, AdminStatusEnum.CANCELLED, "test");

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        assertDomainState(domainName, 20);
    }

    @Test
    public void uc37sc01Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "ZM10-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithCCPayment(domainName, gainingNH, 2);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.STALLED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 25);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc37sc02Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "APITEST-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, FinancialStatusEnum.STALLED);
        
        AuthenticatedUserVO user = authenticate(gainingNH);
        boolean passed = triplePassAppService.triplePass(user, domainName);

        assertTransferReservationCreatedAndReady(ticketId, domainName.toLowerCase());
        AssertJUnit.assertTrue(passed);
        assertDomainTransfered(domainName, gainingNH, 17);
        assertTicketClosed(ticketId);
    }

    @Test
    public void uc37sc03Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.STALLED, FinancialStatusEnum.NEW);

        AuthenticatedUserVO user = authenticate(gainingNH);
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 1);
        
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.STALLED.getId(), t.getFinancialStatus().getId());
    }

    @Test
    public void uc37sc04Test() throws Exception {
        String domainName = "transferDomainUC006SC01.ie";
        String gainingNH = "SWD2-IEDR";
        assertDomainState(domainName, 17);

        long ticketId = transferDomainWithADPPayment(domainName, gainingNH, 1);

        updateTicketStatuses(ticketId, AdminStatusEnum.PASSED, TechStatusEnum.PASSED, FinancialStatusEnum.STALLED);

        AuthenticatedUserVO user = authenticate(gainingNH);
        expectInsufficientDepositNotificationWillBeSent(EmailTemplateNamesEnum.INSUFFICIENT_DEPOSIT_FUNDS_XFER, 0); // no email should be sent here!
        
        boolean passed = triplePassAppService.triplePass(user, domainName);

        AssertJUnit.assertTrue(!passed);
        Ticket t = ticketSearchService.getTicket(ticketId);
        AssertJUnit.assertEquals(TechStatusEnum.PASSED.getId(), t.getTechStatus().getId());
        AssertJUnit.assertEquals(FinancialStatusEnum.STALLED.getId(), t.getFinancialStatus().getId());
    }

    private long transferDomainWithADPPayment(String domainName, String gainingNH, int period) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(user.getUsername(), domainName).getAuthcode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, period, authCode);
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private long transferDomainWithCCPayment(String domainName, String gainingNH, int period) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(user.getUsername(), domainName).getAuthcode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, period, authCode);
        PaymentRequestVO paymentRequest = createBasicCCPaymentRequest();
        return crsCommonAppService.transferDomain(user, request, paymentRequest);
    }

    private long transferDomainCharity(String domainName, String gainingNH) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(user.getUsername(), domainName).getAuthcode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, true, 0, authCode);
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private long transferDomainADPWrongNameserver(String domainName, String gainingNH) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AuthenticatedUserVO user = crsAuthenticationService.authenticate(gainingNH, "Passw0rd!", "1.1.1.1", null);
        String authCode = domainService.getOrCreateAuthCode(user.getUsername(), domainName).getAuthcode();
        TransferRequestVO request = createBasicTransferRequest(domainName, gainingNH, false, 1, authCode);
        request.updateNameservers(Collections.singletonList(new NameserverVO("bad." + domainName, "127.1.1.1")));
        return crsCommonAppService.transferDomain(user, request, null);
    }

    private AuthenticatedUserVO authenticate(String nicHandleId) throws IllegalArgumentException, AuthenticationException {
        return crsAuthenticationService.authenticate(nicHandleId, "Passw0rd!", "1.1.1.1", null);
    }

    private void assertDomainState(String domainName, int expectedDSMState) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(expectedDSMState, domain.getDsmState().getId());
    }

    private void assertDomainTransfered(String domainName, String gainingNH, int expectedDSMState) throws Exception {
        Domain domain = domainSearchService.getDomain(domainName);
        AssertJUnit.assertEquals(gainingNH, domain.getBillingContacts().get(0).getNicHandle());
        AssertJUnit.assertEquals(expectedDSMState, domain.getDsmState().getId());
        AssertJUnit.assertNull(domain.getAuthCode());
    }

    private void assertTransferReservationCreatedAndReady(long ticketId, String domainName) {
        Reservation reservation = paymentService.getReservationForTicket(ticketId);
        AssertJUnit.assertNotNull(reservation);
        AssertJUnit.assertEquals(OperationType.TRANSFER, reservation.getOperationType());
        AssertJUnit.assertEquals(domainName, reservation.getDomainName());
        AssertJUnit.assertTrue(reservation.isReadyForSettlement());
    }

    private void assertTransferReservationNotCreated(long ticketId) {
        Reservation reservation = paymentService.getReservationForTicket(ticketId);
        AssertJUnit.assertNull(reservation);
    }

}
