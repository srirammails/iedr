package pl.nask.crs.ticket.dao;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public class TicketDAOTest extends AbstractContextAwareTest {

    @Autowired
    TicketDAO ticketDao;

    @Test
    public void testGetNormal() {
        Ticket actual = ticketDao.get(258973L);
        assertNotNull(actual);
        assertNotNull(actual.getChangeDate());
        // EqualsBuilder.reflectionEquals(expected, actual);
    }
    
    @Test
    public void testGetWithAccountFlags() {
        Ticket actual = ticketDao.get(256744L);
        assertNotNull(actual);
        assertNotNull(actual.getChangeDate());
        assertNotNull("reseller", actual.getOperation().getResellerAccountField().getNewValue());
        assertEquals("reseller Id ", 1L, actual.getOperation().getResellerAccountField().getNewValue().getId());
        assertTrue("agreementSigned", actual.getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
        assertTrue("ticketEdit", actual.getOperation().getResellerAccountField().getNewValue().isTicketEdit());
        // EqualsBuilder.reflectionEquals(expected, actual);
    }
    
    @Test
    public void testGetNonExisting() {
        Ticket actual = ticketDao.get(-1L);
        Assert.assertNull(actual, "Ticket");
    }

    @Test
    public void testLock() {
        assertTrue(ticketDao.lock(258973L));
        Ticket actual = ticketDao.get(258973L);
        assertNotNull(actual);
        // EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    public void testUpdateCheckOut() {
        Ticket existing = ticketDao.get(258973L);
        assertFalse(existing.isCheckedOut());
        assertNotNull(existing.getChangeDate());

        existing.checkOut(new Contact("IH4-IEDR"));
        ticketDao.update(existing);

        Ticket changed = ticketDao.get(258973L);
        assertTrue(changed.isCheckedOut());
        assertNotNull(changed.getChangeDate());
    }

    @Test
    public void testUpdateCheckIn() {
        Ticket existing = ticketDao.get(256744L);
        assertTrue(existing.isCheckedOut());

        existing.checkIn();
        ticketDao.update(existing);

        Ticket changed = ticketDao.get(256744L);
        assertFalse(changed.isCheckedOut());
    }

    @Test
    public void testUpdateHostmastersRemark() {
        String remark = "test-ticket-dao-remark";

        Ticket existing = ticketDao.get(257777L);
        assertNull(existing.getHostmastersRemark());

        existing.setHostmastersRemark(remark);
        ticketDao.update(existing);

        Ticket changed = ticketDao.get(257777L);
        assertEquals(remark, changed.getHostmastersRemark());
    }

    @Test
    public void testUpdateClikPaid() {
        Ticket existing = ticketDao.get(257777L);
        boolean clikPaid = existing.isClikPaid();

        existing.setClikPaid(!clikPaid);
        ticketDao.update(existing);

        Ticket changed = ticketDao.get(257777L);
        assertTrue(changed.isClikPaid() != clikPaid);
    }

    @Test
    public void testLimitedFindByBillingNH() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("APITEST-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(10, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainName() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName("neway");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainHolder("Bill");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 2);
        assertEquals(2, found.getResults().size());
        assertEquals(4, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccountName() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(107, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAdminStatus() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(0);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(3, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByTechStatus() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTechStatus(1);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(30, found.getTotalResults());
    }

    @Test
    public void testLimitedFindFromDate() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setFrom(new Date("8/10/2008"));
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(287, found.getTotalResults());
    }

    @Test
    public void testLimitedFindToDate() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTo(new Date("8/10/2008"));
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(643, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByCheckedOutTo() {
        Ticket existing = ticketDao.get(258973L);
        assertFalse(existing.isCheckedOut());
        assertNotNull(existing.getChangeDate());

        existing.checkOut(new Contact("IH4-IEDR"));
        ticketDao.update(existing);

        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setNicHandle("IH4-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }
    
    @Test
    public void testFindByChangeDate() {
    	long ticketId = 258973L;
        Ticket existing = ticketDao.get(ticketId);
       
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setChangeDateFrom(new Date());
        SearchResult<Ticket> res = ticketDao.find(criteria);
        assertEquals("numner of results", 0, res.getResults().size());
        
        // update the ticket and perform the search again
        existing.checkOut(new Contact("IH4-IEDR"));
        existing.setChangeDate(new Date());
        ticketDao.update(existing);        
               
        res = ticketDao.find(criteria);
        assertEquals("number of results", 1, res.getResults().size());
        assertEquals("ticket id", ticketId, res.getResults().get(0).getId());        
        
        // change the criteria to include chng date
        existing = ticketDao.get(ticketId);
        criteria.setChangeDateTo(new Date());
        
        // still one result expected
        res = ticketDao.find(criteria);
        assertEquals("number of results", 1, res.getResults().size());
        assertEquals("ticket id", ticketId, res.getResults().get(0).getId());
        
        

    }
    
    @Test
    public void testGetModTicketNoDomain() {
    	long ticketId = 300638L;
    	Ticket t = ticketDao.get(ticketId);
    	assertTrue("Hostmaster's remark starts with 'Domain name does not exist'", t.getHostmastersRemark().startsWith("Domain name does not exist"));
    	assertTrue("Original domain name starts with 'Domain name does not exist'", t.getOperation().getDomainNameField().getCurrentValue().startsWith("Domain name does not exist"));
    }

    @Test
    public void ticketCreateTest() {
        final Date aDate = pl.nask.crs.commons.utils.DateUtils.endOfDay(new Date());
        DomainOperation operation = new DomainOperation(
                DomainOperation.DomainOperationType.REG,
                aDate,
                new SimpleDomainFieldChange<String>(null, "domainName"),
                new SimpleDomainFieldChange<String>(null, "holderName"),
                new SimpleDomainFieldChange<String>(null, "holderClass"),
                new SimpleDomainFieldChange<String>(null, "holderCategory"),
                new SimpleDomainFieldChange<Account>(null, new Account(1)),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("adminC"))),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("techC"))),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("billingC"))),
                Arrays.asList(new NameserverChange(new SimpleDomainFieldChange<String>(null, "ns1.ie"), new SimpleDomainFieldChange<String>(null, "1.1.1.1")),
                        new NameserverChange(new SimpleDomainFieldChange<String>(null, "ns2.ie"), new SimpleDomainFieldChange<String>(null, "2.2.2.2"))));
                InternalStatus status = new InternalStatus();
                status.setId(1);

        Ticket ticket = new Ticket(operation, status, aDate, status, aDate, "reqRemark", "hostRemark", new Contact("creatorNH"), aDate, aDate, new Contact("CheckOutToNH"), false, false, Period.fromYears(2), "CHY1", FinancialStatusEnum.NEW, aDate);
        ticket.getCustomerStatusChangeDate().setTime(aDate.getTime());
        long newTicketId = ticketDao.createTicket(ticket);

        Ticket ticketFromDB = ticketDao.get(newTicketId);
        ticketFromDB.getDomainPeriod();
        assertEquals(ticket.getHostmastersRemark(), ticketFromDB.getHostmastersRemark());
        assertEquals(ticket.getRequestersRemark(), ticketFromDB.getRequestersRemark());
        assertEquals(ticket.getDomainPeriod(), ticketFromDB.getDomainPeriod());
        assertEquals(ticket.getCharityCode(), ticketFromDB.getCharityCode());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCreationDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.DATE), ticketFromDB.getAdminStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.DATE), ticketFromDB.getTechStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getFinancialStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCustomerStatusChangeDate());
        assertEquals(FinancialStatusEnum.NEW, ticketFromDB.getFinancialStatus());
        assertEquals(CustomerStatusEnum.NEW, ticketFromDB.getCustomerStatus());

        assertEquals("After creation nameservers should be preserved", 2, ticketFromDB.getOperation().getNameserversField().size());
        assertEquals("First nameserver should still be the first one", "ns1.ie", ticketFromDB.getOperation().getNameserversField().get(0).getName().getNewValue());
        assertEquals("First nameserver should have correct ip", "1.1.1.1", ticketFromDB.getOperation().getNameserversField().get(0).getIpAddress().getNewValue());
        assertEquals("Second nameserver should still be the second one", "ns2.ie", ticketFromDB.getOperation().getNameserversField().get(1).getName().getNewValue());
        assertEquals("Second nameserver should have correct ip", "2.2.2.2", ticketFromDB.getOperation().getNameserversField().get(1).getIpAddress().getNewValue());
    }

    @Test
    public void testDateFieldsTruncateOnUpdate() {
        Date aDate = pl.nask.crs.commons.utils.DateUtils.endOfDay(new Date());
        final long ticketId = 258973L;
        Ticket actual = ticketDao.get(ticketId);
        // instead of setting change dates (most of which are private and changed only
        // when some other field is touched) I'm using the fact that Ticket object
        // returns it's actual date field, not a copy of it.
        actual.getChangeDate().setTime(aDate.getTime());
        actual.getAdminStatusChangeDate().setTime(aDate.getTime());
        actual.getTechStatusChangeDate().setTime(aDate.getTime());
        actual.setFinancialStatus(FinancialStatusEnum.STALLED);
        actual.getFinancialStatusChangeDate().setTime(aDate.getTime());
        actual.setCustomerStatus(CustomerStatusEnum.CANCELLED);
        actual.getCustomerStatusChangeDate().setTime(aDate.getTime());
        ticketDao.update(actual);
        Ticket ticketFromDB = ticketDao.get(ticketId);
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.DATE), ticketFromDB.getAdminStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.DATE), ticketFromDB.getTechStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getFinancialStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCustomerStatusChangeDate());
    }

    @Test
    public void testUpdateFinancialStatus() {
    	 Ticket actual = ticketDao.get(258973L); // some existing ticket
    	 // check the financial status first:
    	 assertEquals(FinancialStatusEnum.NEW, actual.getFinancialStatus());
    	 actual.setFinancialStatus(FinancialStatusEnum.PASSED);
    	 Date dt = actual.getFinancialStatusChangeDate();
    	 
    	 ticketDao.update(actual);
    	 actual = ticketDao.get(258973L);
    	 
    	 assertEquals(FinancialStatusEnum.PASSED, actual.getFinancialStatus());
    	 assertTrue(DateUtils.isSameDay(dt, actual.getFinancialStatusChangeDate()));
    	 
    }

    @Test
    public void updateTechStatusTest() {
        Ticket actual = ticketDao.get(259926L);
        assertEquals(TechStatusEnum.PASSED.getId(), actual.getTechStatus().getId());
        actual.setTechStatus(TechStatusEnum.STALLED);
        Date date = actual.getTechStatusChangeDate();

        ticketDao.update(actual);
        actual = ticketDao.get(259926L);

        assertEquals(FinancialStatusEnum.STALLED.getId(), actual.getTechStatus().getId());
        assertTrue(DateUtils.isSameDay(date, actual.getTechStatusChangeDate()));
    }

    @Test
    public void findByCustomerStatusTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setCustomerStatus(CustomerStatusEnum.CANCELLED);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(0, result.getTotalResults());

        criteria.setCustomerStatus(CustomerStatusEnum.NEW);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(908, result.getTotalResults());
    }

    @Test
    public void findAllTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        SearchResult<Ticket> result = ticketDao.find(criteria);
        assertEquals(908, result.getResults().size());
    }

    @Test
    public void findByFinancialStatusTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setFinancialStatus(FinancialStatusEnum.STALLED);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(4, result.getTotalResults());

        criteria.setFinancialStatus(FinancialStatusEnum.NEW);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(904, result.getTotalResults());

        criteria.setFinancialStatus(FinancialStatusEnum.PASSED);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByTicketType() throws Exception {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.MOD);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(22, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.XFER);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(1, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.REG);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(882, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.MOD, DomainOperation.DomainOperationType.XFER, DomainOperation.DomainOperationType.REG);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(905, result.getTotalResults());
    }

    @Test
    public void updateNameserversWithFailureReasons() {
        final long TICKET_ID = 256745;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 3);

        InternalStatus nameFailureReason = new InternalStatus();
        nameFailureReason.setId(9);
        nameFailureReason.setDataField(Field.DNS_FAIL.getDataFieldValue());
        InternalStatus ipFailureReasonDestinationUnreachable = new InternalStatus();
        ipFailureReasonDestinationUnreachable.setId(22);
        ipFailureReasonDestinationUnreachable.setDataField(Field.IP_FAIL.getDataFieldValue());
        InternalStatus ipFailureReasonHostUnreachable = new InternalStatus();
        ipFailureReasonHostUnreachable.setId(23);
        ipFailureReasonHostUnreachable.setDataField(Field.IP_FAIL.getDataFieldValue());

        DomainOperation newOp = new DomainOperation(
                DomainOperation.DomainOperationType.REG,
                new Date(),
                new SimpleDomainFieldChange<String>(null, "domainName"),
                new SimpleDomainFieldChange<String>(null, "holderName"),
                new SimpleDomainFieldChange<String>(null, "holderClass"),
                new SimpleDomainFieldChange<String>(null, "holderCategory"),
                new SimpleDomainFieldChange<Account>(null, new Account(1)),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("adminC"))),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("techC"))),
                Arrays.asList(new SimpleDomainFieldChange<Contact>(null, new Contact("billingC"))),
                Arrays.asList(new NameserverChange(new SimpleDomainFieldChange<String>(null, "ns1.ie"), new SimpleDomainFieldChange<String>(null, "1.1.1.1")),
                        new NameserverChange(new SimpleDomainFieldChange<String>(null, "ns2.ie"), new SimpleDomainFieldChange<String>(null, "2.2.2.2"))));

        newOp.populateValues(actual.getOperation());
        newOp.populateFailureReasons(actual.getOperation());

        final NameserverChange change1 = nsChange(0);
        final NameserverChange change2 = nsChange(1);
        change1.getName().setFailureReason(nameFailureReason);
        change1.getIpAddress().setFailureReason(ipFailureReasonDestinationUnreachable);
        change2.getName().setFailureReason(nameFailureReason);
        change2.getIpAddress().setFailureReason(ipFailureReasonHostUnreachable);

        newOp.setNameserversField(Arrays.asList(change1, change2));
        actual.getOperation().populateValues(newOp);
        actual.getOperation().populateFailureReasons(newOp);
        ticketDao.update(actual);

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        Assert.assertNotNull(dbTicket.getOperation().getNameserversField());
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().size(), 2);

        final NameserverChange nameserverChange0 = dbTicket.getOperation().getNameserversField().get(0);
        final SimpleDomainFieldChange<String> name0 = nameserverChange0.getName();
        final SimpleDomainFieldChange<String> ipAddress0 = nameserverChange0.getIpAddress();
        Assert.assertEquals(name0.getNewValue(), nameserverName(0));
        Assert.assertNotNull(name0.getFailureReason());
        Assert.assertEquals(name0.getFailureReason().getId(), nameFailureReason.getId());
        Assert.assertEquals(ipAddress0.getNewValue(), nameserverIp(0));
        Assert.assertNotNull(ipAddress0.getFailureReason());
        Assert.assertEquals(ipAddress0.getFailureReason().getId(), ipFailureReasonDestinationUnreachable.getId());

        final NameserverChange nameserverChange1 = dbTicket.getOperation().getNameserversField().get(1);
        final SimpleDomainFieldChange<String> name1 = nameserverChange1.getName();
        final SimpleDomainFieldChange<String> ipAddress1 = nameserverChange1.getIpAddress();
        Assert.assertEquals(name1.getNewValue(), nameserverName(1));
        Assert.assertNotNull(name1.getFailureReason());
        Assert.assertEquals(name1.getFailureReason().getId(), nameFailureReason.getId());
        Assert.assertEquals(ipAddress1.getNewValue(), nameserverIp(1));
        Assert.assertNotNull(ipAddress1.getFailureReason());
        Assert.assertEquals(ipAddress1.getFailureReason().getId(), ipFailureReasonHostUnreachable.getId());

    }

    @Test
    public void updateWithTwoNameservers() {
        final long TICKET_ID = 256745;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 3);

        DomainOperation newOp = actual.getOperation();
        newOp.setNameserversField(Arrays.asList(nsChange(0), nsChange(1)));
        actual.getOperation().populateValues(newOp);
        ticketDao.update(actual);

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        Assert.assertNotNull(dbTicket.getOperation().getNameserversField());
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().size(), 2);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getName().getNewValue(), nameserverName(i));
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getName().getFailureReason());
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getNewValue(), nameserverIp(i));
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getFailureReason());
        }
    }

    @Test
    public void updateWithSevenNameservers() {
        final long TICKET_ID = 259926L;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 2);

        DomainOperation newOp = actual.getOperation();
        newOp.setNameserversField(Arrays.asList(
                nsChange(0, null),
                nsChange(1),
                nsChange(2, null),
                nsChange(3),
                nsChange(4, null),
                nsChange(5),
                nsChange(6, null)));
        actual.getOperation().populateValues(newOp);
        ticketDao.update(actual);

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        Assert.assertNotNull(dbTicket.getOperation().getNameserversField());
        Assert.assertEquals(dbTicket.getOperation().getNameserversField().size(), 7);
        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getName().getNewValue(), nameserverName(i));
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getName().getFailureReason());
            if (i % 2 == 0)
                Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getNewValue());
            else
                Assert.assertEquals(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getNewValue(), nameserverIp(i));
            Assert.assertNull(dbTicket.getOperation().getNameserversField().get(i).getIpAddress().getFailureReason());
        }
    }

    @Test
    public void testFindDoesNotReturnNameservers() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("APITEST-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(10, found.getTotalResults());
        for (Ticket t : found.getResults()) {
            Assert.assertEquals(t.getOperation().getNameserversField(), Collections.emptyList());
        }
    }

    private NameserverChange nsChange(int i) {
        return nsChange(i, i);
    }
    private NameserverChange nsChange(int i, Integer j) {
        return new NameserverChange(
                new SimpleDomainFieldChange<String>(null, nameserverName(i)),
                new SimpleDomainFieldChange<String>(null, nameserverIp(j))
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
