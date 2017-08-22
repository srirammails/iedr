package pl.nask.crs.ticket.services;

import org.testng.annotations.Test;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * @author Kasia Fulara
 */
public class TicketSearchServiceTest extends AbstractContextAwareTest {

    @Resource
    TicketSearchService ticketSearchService;

    @Test
    public void testFind() {
        TicketSearchCriteria t = new TicketSearchCriteria();

        t.setNicHandle("GEORGE-IEDR");
        LimitedSearchResult<Ticket> result = ticketSearchService.find(t, 2, 4,
                null);

        int i = 0;
    }
    
    @Test(expectedExceptions = TicketNotFoundException.class)
    public void testGetNonExisting() throws TicketNotFoundException {
        ticketSearchService.getTicket(-1);
    }

    /*
     * @Test public void
     * testGetAdditionalInfoForDomainPresentlyRegisteredNoPrevHolder() { // does
     * not exists in crsdb_mini - todo: prepare test data }
     * 
     * @Test public void
     * testGetAdditionalInfoForDomainPresentlyRegisteredDiffPrevHolder() {
     * DomainInfo expected = new DomainInfo("lilywhite.ie", null,
     * "Lily White Print Ltd", Collections.EMPTY_LIST); DomainInfo info =
     * ticketSearchService.getAdditionalInfo("lilywhite.ie", null);
     * Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, info)); }
     * 
     * @Test public void testGetAdditionalInfoForDomainRegisteredInPast() { //
     * does not exists in crsdb_mini - todo: prepare test data }
     * 
     * @Test public void testGetAdditionalInfoForDomainNeverRegistered() {
     * DomainInfo expected = new DomainInfo("nask.pl", "nask", null,
     * Collections.EMPTY_LIST); DomainInfo info =
     * ticketSearchService.getAdditionalInfo("nask.pl", "nask");
     * Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, info)); }
     */
}
