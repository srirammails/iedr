package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class ResellerTest extends AbstractPermissionGroupTest {

    private static final String VALID_NH = "AAH296-IEDR";

    private static final String VALID_DN = "webwebweb2.ie";

    private static final long VALID_TICKET = 259925;

    AuthenticatedUser reseller;

    @BeforeMethod
	public void setUp() throws Exception {
        reseller = authenticationService.authenticate("AAH296-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccount() throws Exception {
        getAccount(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccountHistory() throws Exception {
        getAccountHistory(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws Exception {
        alterStatusAccount(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccount() throws Exception {
        saveAccount(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createAccount() throws Exception {
        createAccount(reseller);
    }

    @Test
    public void viewDomain() throws Exception {
        viewDomain(reseller, VALID_DN);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editDomainAD() throws Exception {
        editDomain(reseller);
    }

    @Test
    public void editDomain() throws Exception {
        editDomain(reseller, VALID_DN);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveDomainAD() throws Exception {
        saveDomain(reseller);
    }

    @Test
    public void saveDomain() throws Exception {
        saveDomain(reseller, VALID_DN);
    }

//    @Test(expected = AccessDeniedException.class)
//    public void transferDomain() throws Exception {
//        transferDomain(reseller);
//    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNicHandleAD() throws Exception {
        getNicHandle(reseller);
    }

    @Test
    public void getNicHandle() throws Exception {
        getNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getNicHandleHistoryAD() throws Exception {
        getNicHandleHistory(reseller);
    }

    @Test
    public void getNicHandleHistory() throws Exception {
        getNicHandleHistory(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandleAD() throws Exception {
        alterStatusNicHandle(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandle() throws Exception {
        alterStatusNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNicHandleAD() throws Exception {
        saveNicHandle(reseller);
    }

    @Test
    public void saveNicHandle() throws Exception {
        saveNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNewPasswordNicHandleAD() throws Exception {
        saveNewPasswordNicHandle(reseller);
    }

    @Test
    public void saveNewPasswordNicHandle() throws Exception {
        saveNewPasswordNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void generateNewPasswordNicHandleAD() throws Exception {
        generateNewPasswordNicHandle(reseller);
    }

    @Test
    public void generateNewPasswordNicHandle() throws Exception {
        generateNewPasswordNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createNicHandleAD() throws Exception {
        createNicHandle(reseller);
    }


    @Test
    public void createNicHandle() throws Exception {
        createNicHandle(reseller, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewTicketAD() throws Exception {
        viewTicket(reseller);
    }

    @Test
    public void viewTicket() throws Exception {
        viewTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void viewTicketHistoryAD() throws Exception {
        viewTicketHistory(reseller);
    }

    @Test
    public void viewTicketHistory() throws Exception {
        viewTicketHistory(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reviseTicketAD() throws Exception {
        reviseTicket(reseller);
    }

    @Test
    public void reviseTicket() throws Exception {
        reviseTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void editTicketAD() throws Exception {
        editTicket(reseller);
    }

    @Test
    public void editTicket() throws Exception {
        editTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicketAD() throws Exception {
        checkOutTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() throws Exception {
        checkOutTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicketAD() throws Exception {
        checkInTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() throws Exception {
        checkInTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicketAD() throws Exception {
        alterStatusTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() throws Exception {
        alterStatusTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicketAD() throws Exception {
        reassignTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() throws Exception {
        reassignTicket(reseller, VALID_TICKET, VALID_NH);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveTicketAD() throws Exception {
        saveTicket(reseller);
    }

    @Test
    public void saveTicket() throws Exception {
        saveTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicketAD() throws Exception {
        acceptTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() throws Exception {
        acceptTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicketAD() throws Exception {
        rejectTicket(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() throws Exception {
        rejectTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void updateTicketAD() throws Exception {
        updateTicket(reseller);
    }

    @Test
    public void updateTicket() throws Exception {
        updateTicket(reseller, VALID_TICKET);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {
        changePermissionGroups(reseller);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getUserHistory() throws Exception {
        getUserHistory(reseller);
    }

    @Test
    public void createDomain() throws Exception {
        createDomain(reseller);
    }

}
