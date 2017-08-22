package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class WebTest extends AbstractPermissionGroupTest {

    private static final String VALID_NH = "AAA22-IEDR";

    private static final String VALID_DN = "webwebweb.ie";

    private static final long VALID_TICKET = 257706;

    AuthenticatedUser web;


    @BeforeMethod
	public void setUp() throws Exception {
        web = authenticationService.authenticate("AAA22-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccount() throws Exception {
        getAccount(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccountHistory() throws Exception {
        getAccountHistory(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws Exception {
        alterStatusAccount(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccount() throws Exception {
        saveAccount(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createAccount() throws Exception {
        createAccount(web);
    }

    @Test
    public void viewDomain() throws Exception {
        viewDomain(web, VALID_DN);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editDomainAD() throws Exception {
        editDomain(web);
    }

    @Test
    public void editDomain() throws Exception {
        editDomain(web, VALID_DN);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveDomainAD() throws Exception {
        saveDomain(web);
    }

    @Test
    public void saveDomain() throws Exception {
        saveDomain(web, VALID_DN);
    }

//    @Test(expected = AccessDeniedException.class)
//    public void transferDomain() throws Exception {
//        transferDomain(web);
//    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNicHandleAD() throws Exception {
        getNicHandle(web);
    }

    @Test
    public void getNicHandle() throws Exception {
        getNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNicHandleHistoryAD() throws Exception {
        getNicHandleHistory(web);
    }

    @Test
    public void getNicHandleHistory() throws Exception {
        getNicHandleHistory(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandleAD() throws Exception {
        alterStatusNicHandle(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandle() throws Exception {
        alterStatusNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNicHandleAD() throws Exception {
        saveNicHandle(web);
    }

    @Test
    public void saveNicHandle() throws Exception {
        saveNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNewPasswordNicHandleAD() throws Exception {
        saveNewPasswordNicHandle(web);
    }

    @Test
    public void saveNewPasswordNicHandle() throws Exception {
        saveNewPasswordNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateNewPasswordNicHandleAD() throws Exception {
        generateNewPasswordNicHandle(web);
    }

    @Test
    public void generateNewPasswordNicHandle() throws Exception {
        generateNewPasswordNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createNicHandleAD() throws Exception {
        createNicHandle(web);
    }


    @Test
    public void createNicHandle() throws Exception {
        createNicHandle(web, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewTicketAD() throws Exception {
        viewTicket(web);
    }

    @Test
    public void viewTicket() throws Exception {
        viewTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewTicketHistoryAD() throws Exception {
        viewTicketHistory(web);
    }

    @Test
    public void viewTicketHistory() throws Exception {
        viewTicketHistory(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reviseTicketAD() throws Exception {
        reviseTicket(web);
    }

    @Test
    public void reviseTicket() throws Exception {
        reviseTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editTicketAD() throws Exception {
        editTicket(web);
    }

    @Test
    public void editTicket() throws Exception {
        editTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicketAD() throws Exception {
        checkOutTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() throws Exception {
        checkOutTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicketAD() throws Exception {
        checkInTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() throws Exception {
        checkInTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicketAD() throws Exception {
        alterStatusTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() throws Exception {
        alterStatusTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicketAD() throws Exception {
        reassignTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() throws Exception {
        reassignTicket(web, VALID_TICKET, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveTicketAD() throws Exception {
        saveTicket(web);
    }

    @Test
    public void saveTicket() throws Exception {
        saveTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicketAD() throws Exception {
        acceptTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() throws Exception {
        acceptTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicketAD() throws Exception {
        rejectTicket(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() throws Exception {
        rejectTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateTicketAD() throws Exception {
        updateTicket(web);
    }

    @Test
    public void updateTicket() throws Exception {
        updateTicket(web, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {
        changePermissionGroups(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getUserHistory() throws Exception {
        getUserHistory(web);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createDomain() throws Exception {
        createDomain(web);
    }

}
