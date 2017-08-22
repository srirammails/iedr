package pl.nask.crs.app;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/* 
 * all data are in the data model: theweb3.ie has 3 nameservers, ticket for the theweb3.ie has 2 nameservers.
 * bug #2107 caused a conversion error when getting Ticket from the database.
 */
public class TestForBug2107 extends AbstractCrsTest {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TicketAppService ticketAppService;

    AuthenticatedUser user;

    @BeforeMethod
	public void authenticateUser() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
        user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

	
    @Test
    public void testConversionErrorOnView() throws TicketNotFoundException, AccessDeniedException {
    	TicketModel t = ticketAppService.view(user, 300632);    	
    }
    
    @Test
    public void testNameserverModifications() throws TicketNotFoundException, AccessDeniedException {
    	TicketModel t = ticketAppService.view(user, 300632);
    	
    	
    	for (NameserverChange f: t.getTicket().getOperation().getNameserversField()) {
    		assertTrue("Field change "+f, f.isModification());
    	}    
    }
    
    @Test
    public void testConversionErrorOnFind() throws TicketNotFoundException, AccessDeniedException {
    	TicketSearchCriteria criteria = new TicketSearchCriteria();
    	criteria.setDomainName("theweb3.ie");
    	LimitedSearchResult<Ticket> res = ticketAppService.search(user, criteria, 0, 100, null);
    	assertEquals(res.getTotalResults(), res.getResults().size());
    }
    
    
}
