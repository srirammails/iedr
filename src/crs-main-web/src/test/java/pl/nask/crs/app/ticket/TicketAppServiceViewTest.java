package pl.nask.crs.app.ticket;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * tests
 * {@link TicketAppService#view(pl.nask.crs.security.authentication.AuthenticatedUser, long)
 * method
 */
public class TicketAppServiceViewTest extends AbstractCrsTest {
    @Autowired
    TicketAppService ticketAppService;
    
    @Autowired
    AuthenticationService authenticationService;
         
    @Test
    public void testOtherDomainNames() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, TicketNotFoundException, AccessDeniedException {
     // testing on modification ticket for domain theweb.ie
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketModel ticket = ticketAppService.view(user, 259632);
        AssertJUnit.assertEquals("other domain names", 2, ticket.getAdditionalInfo().getRelatedDomainNames().size());
    }
 
    @Test
    public void testPrevoiusHolder() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, TicketNotFoundException, AccessDeniedException {
        // testing on modification ticket for domain theweb.ie
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketModel ticket = ticketAppService.view(user, 259632);
        AssertJUnit.assertEquals("preious domain holder", "Edison Waters", ticket.getAdditionalInfo().getPreviousHolder());
    }

    // CRS-72
    /*@Test
    public void testDocumentsFlagPos() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, TicketNotFoundException, AccessDeniedException {
        // testing on modification ticket for domain castlebargolfclub.ie
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketModel ticket = ticketAppService.view(user, 258973);
        AssertJUnit.assertEquals("has documents (Ticket)", true, ticket.getTicket().isHasDocuments());
        AssertJUnit.assertEquals("has documents (TicketInfo)", true, ticket.getAdditionalInfo().isDocuments());
    }*/

    @Test
    public void testDocumentsFlagNeg() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, TicketNotFoundException, AccessDeniedException {
        // testing on modification ticket for domain theweb.ie
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketModel ticket = ticketAppService.view(user, 259632);
        AssertJUnit.assertEquals("has documents (Ticket)", false, ticket.getTicket().isHasDocuments());
        AssertJUnit.assertEquals("has documents (TicketInfo)", false, ticket.getAdditionalInfo().isDocuments());
    }
    
    @Test
    public void testPendingDomainNames() throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException, TicketNotFoundException, AccessDeniedException {
     // testing on modification ticket for domain theweb.ie
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
        TicketModel ticket = ticketAppService.view(user, 259632);
        AssertJUnit.assertEquals("pending domain names", 5, ticket.getAdditionalInfo().getPendingDomainNames().size());
    }
}
