package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class BatchTest extends AbstractPermissionGroupTest {

    AuthenticatedUser batch;

    @BeforeMethod
	public void setUp() throws Exception {
        batch = authenticationService.authenticate("AAB069-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void getAccount() throws Exception{ getAccount(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void getAccountHistory() throws Exception {getAccountHistory(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws Exception{alterStatusAccount(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveAccount() throws Exception{saveAccount(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void createAccount() throws Exception{createAccount(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void viewDomain() throws Exception{viewDomain(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void editDomain() throws Exception{editDomain(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveDomain() throws Exception{saveDomain(batch);}
    
//    @Test (expected = AccessDeniedException.class)
//    public void transferDomain() throws Exception{transferDomain(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void getNicHandle() throws Exception{getNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void getNicHandleHistory() throws Exception{getNicHandleHistory(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandle() throws Exception{alterStatusNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveNicHandle() throws Exception{saveNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveNewPasswordNicHandle() throws Exception{saveNewPasswordNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void generateNewPasswordNicHandle() throws Exception{generateNewPasswordNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void createNicHandle() throws Exception{createNicHandle(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void viewTicket() throws Exception{viewTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void viewTicketHistory() throws Exception{viewTicketHistory(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void reviseTicket() throws Exception {reviseTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void editTicket() throws Exception {editTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() throws Exception {checkOutTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() throws Exception {checkInTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() throws Exception {alterStatusTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() throws Exception {reassignTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveTicket() throws Exception {saveTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() throws Exception {acceptTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() throws Exception {rejectTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void updateTicket() throws Exception {updateTicket(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {changePermissionGroups(batch);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void getUserHistory() throws Exception {getUserHistory(batch);}

}
