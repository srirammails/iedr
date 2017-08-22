package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class HostmasterTest extends AbstractPermissionGroupTest {

    AuthenticatedUser hostmaster;

    @BeforeMethod
	public void setUp() throws Exception {
        hostmaster = authenticationService.authenticate("AAA906-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void getAccount() throws Exception {
        getAccount(hostmaster);
    }

    @Test
    public void getAccountHistory() throws Exception {
        getAccountHistory(hostmaster);
    }

    @Test(expectedExceptions=AccessDeniedException.class)
    public void alterStatusAccount() throws Exception {
        alterStatusAccount(hostmaster);
    }

    @Test
    public void saveAccount() throws Exception {
        saveAccount(hostmaster);
    }

    @Test
    public void createAccount() throws Exception {
        createAccount(hostmaster);
    }

    @Test
    public void viewDomain() throws Exception {
        viewDomain(hostmaster);
    }

    @Test
    public void editDomain() throws Exception {
        editDomain(hostmaster);
    }

	@Test
    public void saveDomain() throws Exception {
        saveDomain(hostmaster);
    }

//    @Test
//    public void transferDomain() throws Exception {
//        transferDomain(hostmaster);
//    }

    @Test
    public void getNicHandle() throws Exception {
        getNicHandle(hostmaster);
    }

    @Test
    public void getNicHandleHistory() throws Exception {
        getNicHandleHistory(hostmaster);
    }

    @Test
    public void alterStatusNicHandle() throws Exception {
        alterStatusNicHandle(hostmaster);
    }

    @Test
    public void saveNicHandle() throws Exception {
        saveNicHandle(hostmaster);
    }

    @Test
    public void saveNewPasswordNicHandle() throws Exception {
        saveNewPasswordNicHandle(hostmaster);
    }

    @Test
    public void generateNewPasswordNicHandle() throws Exception {
        generateNewPasswordNicHandle(hostmaster);
    }

    @Test
    public void createNicHandle() throws Exception {
        createNicHandle(hostmaster);
    }

    @Test
    public void viewTicket() throws Exception {
        viewTicket(hostmaster);
    }

    @Test
    public void viewTicketHistory() throws Exception {
        viewTicketHistory(hostmaster);
    }

    @Test
    public void reviseTicket() throws Exception {
        reviseTicket(hostmaster);
    }

    @Test
    public void editTicket() throws Exception {
        editTicket(hostmaster);
    }

    @Test
    public void checkOutTicket() throws Exception {
        checkOutTicket(hostmaster);
    }

    @Test
    public void checkInTicket() throws Exception {
        checkInTicket(hostmaster);
    }

    @Test
    public void alterStatusTicket() throws Exception {
        alterStatusTicket(hostmaster);
    }

    @Test
    public void reassignTicket() throws Exception {
        reassignTicket(hostmaster);
    }

    @Test
    public void saveTicket() throws Exception {
        saveTicket(hostmaster);
    }

    @Test
    public void acceptTicket() throws Exception {
        acceptTicket(hostmaster);
    }

    @Test
    public void rejectTicket() throws Exception {
        rejectTicket(hostmaster);
    }

    @Test
    public void updateTicket() throws Exception {
        updateTicket(hostmaster);
    }

    @Test(expectedExceptions=AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {
        changePermissionGroups(hostmaster);
    }

    @Test
    public void getUserHistory() throws Exception {
        getUserHistory(hostmaster);
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void createDomain() throws Exception {
        createDomain(hostmaster);
    }
    
    @Test
    public void getHmUsage() throws Exception {getHmUsage(hostmaster);}

}
