package pl.nask.crs.ticket.dao;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;


/**
 * @author Patrycja Wegrzynowicz
 */
public class HistoricTicketDAOTest extends AbstractContextAwareTest {

    @Resource
    private HistoricTicketDAO historicTicketDAO;

    @Resource
    private TicketDAO ticketDAO;

    @Test
    public void testGetHistoryPresent() {
        List<HistoricalObject<Ticket>> history = historicTicketDAO.get(259573L);
        Assert.assertEquals(history.size(), 3);
    }

    @Test
    public void testGetHistoryEmpty() {
        List<HistoricalObject<Ticket>> history = historicTicketDAO.get(256625L);
        Assert.assertEquals(history.size(), 0);

    }

    @Test
    public void testCreate() {
        final long ID = 256745;
        Date changeDate = new Date(((new Date().getTime())/1000)*1000);
        final String nicHandle = "NicHandle";
        Ticket origTicket = ticketDAO.get(ID);
        Assert.assertTrue(origTicket.getOperation().getResellerAccountField().getNewValue().isAgreementSigned());

        historicTicketDAO.create(ID, changeDate, nicHandle);
        List<HistoricalObject<Ticket>> history = historicTicketDAO.get(ID);
        Assert.assertEquals(history.size(), 1);

        final HistoricalObject<Ticket> ticketHistoricalObject = history.get(0);
        Assert.assertEquals(ticketHistoricalObject.getChangeDate(), changeDate, "Historical change date should be the one passed to DAO");
        Assert.assertEquals(ticketHistoricalObject.getChangedBy(), nicHandle, "ChangeBy should be correctly set");

        final Ticket ticket = ticketHistoricalObject.getObject();
        final List<NameserverChange> nameserverChanges = ticket.getOperation().getNameserversField();
        Assert.assertNotNull(nameserverChanges);
        Assert.assertEquals(nameserverChanges.size(), origTicket.getOperation().getNameserversField().size());
        Assert.assertEquals(nameserverChanges.size(), 3, "There should be exactly 3 nameservers");
        Assert.assertEquals(nameserverChanges.get(0).getName().getNewValue(), "NS1.JNJ.COM");
        Assert.assertNull(nameserverChanges.get(0).getName().getFailureReason());
        Assert.assertNull(nameserverChanges.get(0).getIpAddress().getNewValue());
        Assert.assertNull(nameserverChanges.get(0).getIpAddress().getFailureReason());
        Assert.assertEquals(nameserverChanges.get(1).getName().getNewValue(), "NS3.JNJ.COM");
        Assert.assertNull(nameserverChanges.get(1).getName().getFailureReason());
        Assert.assertNull(nameserverChanges.get(1).getIpAddress().getNewValue());
        Assert.assertNull(nameserverChanges.get(1).getIpAddress().getFailureReason());
        Assert.assertEquals(nameserverChanges.get(2).getName().getNewValue(), "NS5.JNJ.COM");
        Assert.assertNull(nameserverChanges.get(2).getName().getFailureReason());
        Assert.assertNull(nameserverChanges.get(2).getIpAddress().getNewValue());
        Assert.assertNull(nameserverChanges.get(2).getIpAddress().getFailureReason());

        Assert.assertTrue(origTicket.getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
        Assert.assertEquals(ticket.getOperation().getResellerAccountField().getNewValue().isAgreementSigned(), origTicket.getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
    }

    @Test
    public void testLimitedFindByDomainName() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainName("neway");
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicTicketDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(found.getResults().size(), 10);
        Assert.assertEquals(found.getTotalResults(), 16);
    }

    @Test
    public void testLimitedFindByAccount() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicTicketDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(found.getResults().size(), 10);
        Assert.assertEquals(found.getTotalResults(), 87);
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainHolder("Castlebar");
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicTicketDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(found.getResults().size(), 10);
        Assert.assertEquals(found.getTotalResults(), 49);
    }

    @Test
    public void testLimitedFindAll() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicTicketDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(found.getResults().size(), 10);
        Assert.assertEquals(found.getTotalResults(), 504);
    }

    @Test
    public void testFindAll() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        SearchResult<HistoricalObject<Ticket>> found = historicTicketDAO.find(criteria);
        Assert.assertEquals(found.getResults().size(), 504);
    }
}
