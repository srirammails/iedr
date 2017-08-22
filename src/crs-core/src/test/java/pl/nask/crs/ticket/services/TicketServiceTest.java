package pl.nask.crs.ticket.services;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.exceptions.InvalidStatusException;
import pl.nask.crs.ticket.exceptions.TicketAlreadyCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketCheckedOutToOtherException;
import pl.nask.crs.ticket.exceptions.TicketDuplicateNameserverException;
import pl.nask.crs.ticket.exceptions.TicketEditFlagException;
import pl.nask.crs.ticket.exceptions.TicketNameserverCountException;
import pl.nask.crs.ticket.exceptions.TicketNotCheckedOutException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketServiceTest extends AbstractContextAwareTest {

    @Resource
    TicketService ticketService;

    @Resource(name = "aStatusFactory")
    Dictionary<Integer, AdminStatus> adminStatusDictionary;

    @Resource
    TicketSearchService ticketSearchService;

    @Resource
    TicketHistorySearchService historySearchService;

    @Test
    public void testCheckIn() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 3 - Hold Papwerwork
        Assert.assertEquals(ticket.getAdminStatus().getId(), 3, "admin status");

        // 5 - Renew
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(5);
        ticketService.checkIn(256744L, newAdminStatus, "GEORGE-IEDR", null);
        ticket = ticketSearchService.getTicket(256744L);
        Assert.assertEquals(ticket.getAdminStatus().getId(), newAdminStatus.getId(), "admin status");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
    }

    @Test(expectedExceptions = TicketNotCheckedOutException.class)
    public void testCheckInWhenTicketNotCheckedOut() throws Exception {
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(5);
        ticketService.checkIn(256813L, newAdminStatus, "GEORGE-IEDR", null);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testCheckInWhenTicketCheckedOutToOtherNH() throws Exception {
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(5);
        ticketService.checkIn(256744L, newAdminStatus, "NTG1-IEDR", null);
    }

    @Test
    public void testCheckOut() throws Exception {
        ticketService.checkOut(256814L, "GEORGE-IEDR");
        Ticket ticket = ticketSearchService.getTicket(256814L);
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
    }

    @Test
    public void testCheckOutCalledTwice() throws Exception {
        ticketService.checkOut(256814L, "GEORGE-IEDR");
        ticketService.checkOut(256814L, "GEORGE-IEDR");
        Ticket ticket = ticketSearchService.getTicket(256814L);
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
    }

    
    @Test(expectedExceptions = TicketAlreadyCheckedOutException.class)
    public void testCheckOutWhenTicketAlreadyCheckedOut() throws Exception {
        ticketService.checkOut(256744L, "NTG1-IEDR");
    }

    @Test
    public void testAlterStatus() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(256744L);
        // 3 - Hold Papwerwork
        Assert.assertEquals(ticket.getAdminStatus().getId(), 3 ,"admin status");

        // 5 - Renew
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(5);
        ticketService.alterStatus(null, 256744L, newAdminStatus, "GEORGE-IEDR", null);
        ticket = ticketSearchService.getTicket(256744L);
        Assert.assertEquals(ticket.getAdminStatus().getId(), newAdminStatus.getId(), "admin status");
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "GEORGE-IEDR", "check out to");
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testAlterStatusWhenTicketCheckedOutToOtherNH() throws Exception {
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(5);
        ticketService.alterStatus(null, 256744L, newAdminStatus, "NTG1-IEDR", null);
    }

    @Test
    public void testAccept() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 3 - Hold Papwerwork
        Assert.assertEquals(ticket.getAdminStatus().getId(), 3, "admin status");
        Assert.assertNotNull(ticket.getCheckedOutTo(), "check out to");

        ticketService.accept(null, 256744L, null, "accepted", "GEORGE-IEDR", null);
        ticket = ticketSearchService.getTicket(256744L);
        // 1 - Passed
        Assert.assertEquals(ticket.getAdminStatus().getId(), 1, "admin status");
        Assert.assertEquals(ticket.getHostmastersRemark(), "accepted", "hostmaster remark");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testAcceptWhenTicketCheckedOutToOtherNH() throws Exception {
        ticketService.accept(null, 256744L, null, "accept", "NTG1-IEDR", null);
    }

    @Test(expectedExceptions = TicketNotFoundException.class)
    public void testDelete() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        ticketService.delete(256744L);
        ticket = ticketSearchService.getTicket(256744L);
    }

    @Test
    public void testReject() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 6 - Finance Holdup
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(6);
        ticketService.reject(null, 256744L, newAdminStatus, ticket.getOperation(), null, "rejected", "GEORGE-IEDR", null);
        ticket = ticketSearchService.getTicket(256744L);
        Assert.assertEquals(ticket.getAdminStatus().getId(), newAdminStatus.getId(), "admin status");
        Assert.assertNull(ticket.getCheckedOutTo(), "check out to");
    }

    @Test(expectedExceptions = InvalidStatusException.class)
    public void testRejectWithInvalidStatus() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 1 - Passed
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(1);
        ticketService.reject(null, 256744L, newAdminStatus, ticket.getOperation(), null, "rejected", "GEORGE-IEDR", null);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void testRejectWithEmptyRemark() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 6 - Passed
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(6);
        ticketService.reject(null, 256744L, newAdminStatus, ticket.getOperation(), null, "", "GEORGE-IEDR", null);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testRejectWhenTicketCheckedOutToOtherNH() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        // 6 - Passed
        AdminStatus newAdminStatus = adminStatusDictionary.getEntry(6);
        ticketService.reject(null, 256744L, newAdminStatus, ticket.getOperation(), null, "rejected", "NTG1-IEDR", null);
    }

    @Test
    public void testReassign() throws Exception{
        ticketService.reassign(256744L, "NTG1-IEDR");
        Ticket ticket = ticketSearchService.getTicket(256744L);
        Assert.assertNotNull(ticket.getCheckedOutTo(), "check out to");
        Assert.assertEquals(ticket.getCheckedOutTo().getNicHandle(), "NTG1-IEDR", "check out to");
    }

    @Test
    public void testSave() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(256744L);
        DomainOperation domainOperation = ticket.getOperation();
        Assert.assertNull(domainOperation.getDomainNameField().getFailureReason(), "domain name failure reason");

        domainOperation.getDomainNameField().setFailureReason(prepareInternalStatus());

        ticketService.save(256744L, domainOperation, null, "save failure reasons", "GEORGE-IEDR", null);

        ticket = ticketSearchService.getTicket(256744L);
        Assert.assertEquals(ticket.getOperation().getDomainNameField().getFailureReason().getDescription(), "Incorrect", "domain name failure reason description");
    }

    private InternalStatus prepareInternalStatus() {
        InternalStatus internalStatus = new InternalStatus();
        internalStatus.setDescription("Incorrect");
        internalStatus.setId(1);
        internalStatus.setDataField(10);
        return internalStatus;
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testSaveWhenTicketCheckedOutToOtherNH() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(256744L);
        DomainOperation domainOperation = ticket.getOperation();
        domainOperation.getDomainNameField().setFailureReason(prepareInternalStatus());

        ticketService.save(256744L, domainOperation, null, "save failure reasons", "NTG1-IEDR", null);
    }

    @Test
    public void testUpdate() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256744L);
        DomainOperation domainOperation = ticket.getOperation();
        domainOperation.getDomainNameField().setFailureReason(prepareInternalStatus());
        domainOperation.getDomainHolderField().setNewValue("new domain holder");

        ticketService.update(256744L, domainOperation, null, "save failure reasons", true, "GEORGE-IEDR", null, false);

        ticket = ticketSearchService.getTicket(256744L);
        Assert.assertEquals(ticket.getOperation().getDomainNameField().getFailureReason().getDescription(), "Incorrect", "domain name failure reason description");
        Assert.assertEquals(ticket.getOperation().getDomainHolderField().getNewValue(), "new domain holder", "domain holder field value");
    }

    @Test(expectedExceptions = TicketNameserverCountException.class)
    public void testUpdateWithOneNameserver() throws Exception {
        final long TICKET_ID = 256744L;
        Ticket ticket = ticketSearchService.getTicket(TICKET_ID);

        DomainOperation operation = ticket.getOperation();
        operation.setNameserversField(Arrays.asList(nsChange(0)));

        ticketService.update(TICKET_ID, operation, null, "Only one ns", true, "GEORGE-IEDR", null, false);
    }

    @Test(expectedExceptions = TicketNameserverCountException.class)
    public void testUpdateWithTooManyNameservers() throws Exception {
        final long TICKET_ID = 256744L;
        Ticket ticket = ticketSearchService.getTicket(TICKET_ID);

        DomainOperation operation = ticket.getOperation();
        operation.setNameserversField(Arrays.asList(
            nsChange(0),
            nsChange(1),
            nsChange(2),
            nsChange(3),
            nsChange(4),
            nsChange(5),
            nsChange(6),
            nsChange(7)
        ));

        ticketService.update(TICKET_ID, operation, null, "Eight nss", true, "GEORGE-IEDR", null, false);
    }

    @Test
    public void testUpdateNameservers() throws Exception {
        final long TICKET_ID = 256744L;

        List<HistoricalObject<Ticket>> history = historySearchService.getTicketHistory(TICKET_ID);
        Assert.assertNotNull(history);
        Assert.assertTrue(history.isEmpty());

        Ticket ticket = ticketSearchService.getTicket(TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getNameserversField().size(), 3);

        DomainOperation operation = ticket.getOperation();
        operation.setNameserversField(Arrays.asList(
                nsChange(0),
                nsChange(1),
                nsChange(2),
                nsChange(3)
        ));

        ticketService.update(TICKET_ID, operation, null, "Eight nss", true, "GEORGE-IEDR", null, false);
        Ticket dbTicket = ticketSearchService.getTicket(TICKET_ID);
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().size(), 4);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getName().getNewValue(), nameserverName(i));
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getNewValue(), nameserverIp(i));
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getName().getFailureReason());
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getFailureReason());
        }
        history = historySearchService.getTicketHistory(TICKET_ID);
        Assert.assertEquals(history.size(), 2);
        final List<NameserverChange> prevNameservers = history.get(1).getObject().getOperation().getNameserversField();
        Assert.assertEquals(prevNameservers.size(), 3);
        Assert.assertEquals(prevNameservers.get(0).getName().getNewValue(), "NS1.JNJ.COM");
        Assert.assertNull(prevNameservers.get(0).getIpAddress().getNewValue());
        Assert.assertEquals(prevNameservers.get(1).getName().getNewValue(), "NS3.JNJ.COM");
        Assert.assertNull(prevNameservers.get(1).getIpAddress().getNewValue());
        Assert.assertEquals(prevNameservers.get(2).getName().getNewValue(), "NS5.JNJ.COM");
        Assert.assertNull(prevNameservers.get(2).getIpAddress().getNewValue());

        Assert.assertEquals(history.get(0).getObject().getOperation().getNameserversField().size(), 4);
        for (int i = 0; i < 4; i++) {
            final NameserverChange nameserverChange = history.get(0).getObject().getOperation().getNameserversField().get(i);
            Assert.assertEquals(nameserverChange.getName().getNewValue(), nameserverName(i));
            Assert.assertEquals(nameserverChange.getIpAddress().getNewValue(), nameserverIp(i));
            Assert.assertNull(nameserverChange.getName().getFailureReason());
            Assert.assertNull(nameserverChange.getIpAddress().getFailureReason());
        }
    }

    @Test
    public void testNameserversWithFailureReasons() throws Exception {
        final long TICKET_ID = 256744L;

        List<HistoricalObject<Ticket>> history = historySearchService.getTicketHistory(TICKET_ID);
        Assert.assertNotNull(history);
        Assert.assertTrue(history.isEmpty());

        Ticket ticket = ticketSearchService.getTicket(TICKET_ID);
        Assert.assertEquals(ticket.getOperation().getNameserversField().size(), 3);

        DomainOperation operation = ticket.getOperation();
        final NameserverChange nameserverChange1 = nsChange(0);
        InternalStatus nameFailureReason = new InternalStatus();
        nameFailureReason.setId(1);
        nameFailureReason.setDataField(Field.DNS_FAIL.getDataFieldValue());
        InternalStatus ipFailureReason = new InternalStatus();
        ipFailureReason.setId(22);
        ipFailureReason.setDataField(Field.IP_FAIL.getDataFieldValue());
        nameserverChange1.getName().setFailureReason(nameFailureReason);
        nameserverChange1.getIpAddress().setFailureReason(ipFailureReason);
        operation.setNameserversField(Arrays.asList(
                nameserverChange1,
                nsChange(1)
        ));

        ticketService.update(TICKET_ID, operation, null, "remark", true, "GEORGE-IEDR", null, false);
        Ticket dbTicket = ticketSearchService.getTicket(TICKET_ID);
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().size(), 2);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getName().getNewValue(), nameserverName(i));
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getNewValue(), nameserverIp(i));
        }
        Assert.assertNotNull(dbTicket.getOperation().getNameserversField().get(0).getName().getFailureReason());
        Assert.assertNotNull(dbTicket.getOperation().getNameserversField().get(0).getIpAddress().getFailureReason());
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(0).getName().getFailureReason().getId(), nameFailureReason.getId());
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(0).getIpAddress().getFailureReason().getId(), ipFailureReason.getId());
        Assert.assertNull(dbTicket.getOperation().getNameserversField().get(1).getName().getFailureReason());
        Assert.assertNull(dbTicket.getOperation().getNameserversField().get(1).getIpAddress().getFailureReason());

        history = historySearchService.getTicketHistory(TICKET_ID);
        Assert.assertEquals(history.size(), 2);
        final List<NameserverChange> prevNameservers = history.get(1).getObject().getOperation().getNameserversField();
        Assert.assertEquals(prevNameservers.size(), 3);
        Assert.assertEquals(prevNameservers.get(0).getName().getNewValue(), "NS1.JNJ.COM");
        Assert.assertNull(prevNameservers.get(0).getIpAddress().getNewValue());
        Assert.assertEquals(prevNameservers.get(1).getName().getNewValue(), "NS3.JNJ.COM");
        Assert.assertNull(prevNameservers.get(1).getIpAddress().getNewValue());
        Assert.assertEquals(prevNameservers.get(2).getName().getNewValue(), "NS5.JNJ.COM");
        Assert.assertNull(prevNameservers.get(2).getIpAddress().getNewValue());

        Assert.assertEquals(history.get(0).getObject().getOperation().getNameserversField().size(), 2);
        for (int i = 0; i < 2; i++) {
            final NameserverChange nameserverChange = history.get(0).getObject().getOperation().getNameserversField().get(i);
            Assert.assertEquals(nameserverChange.getName().getNewValue(), nameserverName(i));
            Assert.assertEquals(nameserverChange.getIpAddress().getNewValue(), nameserverIp(i));
        }
        NameserverChange nameserverChange = history.get(0).getObject().getOperation().getNameserversField().get(0);
        Assert.assertNotNull(nameserverChange.getName().getFailureReason());
        Assert.assertNotNull(nameserverChange.getIpAddress().getFailureReason());
        Assert.assertEquals(nameserverChange.getName().getFailureReason().getId(), nameFailureReason.getId());
        Assert.assertEquals(nameserverChange.getIpAddress().getFailureReason().getId(), ipFailureReason.getId());
        nameserverChange = history.get(0).getObject().getOperation().getNameserversField().get(1);
        Assert.assertNull(nameserverChange.getName().getFailureReason());
        Assert.assertNull(nameserverChange.getIpAddress().getFailureReason());


    }

    @Test(expectedExceptions = TicketDuplicateNameserverException.class)
    public void testUpdateWithDuplicateNameservers() throws Exception {
        final long TICKET_ID = 256744L;
        Ticket ticket = ticketSearchService.getTicket(TICKET_ID);

        DomainOperation operation = ticket.getOperation();
        operation.setNameserversField(Arrays.asList(
                nsChange(0, 0),
                nsChange(0, 1)
        ));

        ticketService.update(TICKET_ID, operation, null, "Eight nss", true, "GEORGE-IEDR", null, false);
    }

    @Test(expectedExceptions = TicketEditFlagException.class)
    public void testUpdateWhenTicketEditFlagNotSet() throws Exception {
        Ticket ticket = null;
        ticket = ticketSearchService.getTicket(256813L);
        DomainOperation domainOperation = ticket.getOperation();
        domainOperation.getDomainNameField().setFailureReason(prepareInternalStatus());
        domainOperation.getDomainHolderField().setNewValue("new domain holder");

        ticketService.checkOut(256813L, "GEORGE-IEDR");
        ticketService.update(256813L, domainOperation, null, "save failure reasons", true, "GEORGE-IEDR", null, false);
    }

    @Test(expectedExceptions = TicketCheckedOutToOtherException.class)
    public void testUpdateWhenTicketCheckedOutToOtherNH() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(256744L);
        DomainOperation domainOperation = ticket.getOperation();
        domainOperation.getDomainNameField().setFailureReason(prepareInternalStatus());

        ticketService.update(256744L, domainOperation, null, "save failure reasons", true, "NTG1-IEDR", null, true);
    }

    @Test
    public void testCreateTicket() throws Exception {
        Ticket ticket = ticketSearchService.getTicket(256754L);
        ticket.getOperation().getDomainNameField().setNewValue("testCreateTicketDomain.ie");
        Ticket newTicket = new Ticket(
                ticket.getOperation(),
                ticket.getAdminStatus(),
                ticket.getAdminStatusChangeDate(),
                ticket.getTechStatus(),
                ticket.getTechStatusChangeDate(),
                "create",
                "create",
                new Contact("APITS1-IEDR"),
                new Date(),
                new Date(),
                ticket.getCheckedOutTo(),
                true,
                false,
                Period.fromYears(5),
                "CHY123",
                ticket.getFinancialStatus(),
                ticket.getFinancialStatusChangeDate());
        long newTicketId = ticketService.createTicket(newTicket);

        Ticket ticketFromDB = ticketSearchService.getTicket(newTicketId);

        Assert.assertEquals(ticketFromDB.getOperation().getDomainNameField().getNewValue(), "testCreateTicketDomain.ie", "ticket domain name field");
        Assert.assertEquals(ticketFromDB.getHostmastersRemark(), "create", "hostmaster remark");
        Assert.assertEquals(ticketFromDB.getRequestersRemark(), "create", "requester remark");
        Assert.assertEquals(ticketFromDB.getDomainPeriod().getYears(), 5, "domain period");
        Assert.assertEquals(ticketFromDB.getCharityCode(), "CHY123", "charity code");

        newTicket = new Ticket(
                ticket.getOperation(),
                ticket.getAdminStatus(),
                ticket.getAdminStatusChangeDate(),
                ticket.getTechStatus(),
                ticket.getTechStatusChangeDate(),
                "create",
                "create",
                new Contact("APITS1-IEDR"),
                new Date(),
                new Date(),
                ticket.getCheckedOutTo(),
                true,
                false,
                null,
                null,
                ticket.getFinancialStatus(),
                ticket.getFinancialStatusChangeDate());
        newTicketId = ticketService.createTicket(newTicket);

        ticketFromDB = ticketSearchService.getTicket(newTicketId);

        Assert.assertEquals(ticketFromDB.getOperation().getDomainNameField().getNewValue(), "testCreateTicketDomain.ie", "ticket domain name field");
        Assert.assertEquals(ticketFromDB.getHostmastersRemark(), "create", "hostmaster remark");
        Assert.assertEquals(ticketFromDB.getRequestersRemark(), "create", "requester remark");
        Assert.assertNull(ticketFromDB.getDomainPeriod(), "domain period");
        Assert.assertNull(ticketFromDB.getCharityCode(), "chariy code");
    }

    @Test
    public void updateTechStatusTest() throws Exception {
        Ticket actual = ticketSearchService.getTicket(259926L);
        Assert.assertEquals(actual.getTechStatus().getId(), TechStatusEnum.PASSED.getId());

        ticketService.updateTechStatus(259926L, TechStatusEnum.STALLED, "Passw0rd!");

        actual = ticketSearchService.getTicket(259926L);

        Assert.assertEquals(actual.getTechStatus().getId(), FinancialStatusEnum.STALLED.getId());
        Assert.assertTrue(DateUtils.isSameDay(new Date(), actual.getTechStatusChangeDate()));
    }

    private NameserverChange nsChange(int i) {
        return nsChange(i, i);
    }
    private NameserverChange nsChange(int i, Integer j) {
        return nsChange(i, null, j, null);
    }

    private NameserverChange nsChange(int nameCount, FailureReason nameFailure, Integer ipCount, FailureReason ipFailure) {
        return new NameserverChange(
                new SimpleDomainFieldChange<String>(null, nameserverName(nameCount), nameFailure),
                new SimpleDomainFieldChange<String>(null, nameserverIp(ipCount), ipFailure)
        );
    }

    private String nameserverName(int i ) {
        return "n"+Integer.toString(i)+".ie";
    }

    private String nameserverIp(Integer i) {
        if (i == null)
            return null;
        return "192.168.0." + Integer.toString(i);
    }
}
