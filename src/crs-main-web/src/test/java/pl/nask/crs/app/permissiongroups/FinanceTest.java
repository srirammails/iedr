package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class FinanceTest extends AbstractPermissionGroupTest {

    AuthenticatedUser finance;

    @BeforeMethod
	public void setUp() throws Exception{
        finance = authenticationService.authenticate("AAG45-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

    @Test 
    public void getAccount() throws Exception{ getAccount(finance);}

    @Test
    public void getAccountHistory() throws Exception {getAccountHistory(finance);}

    // FIXME: the expectation is changed here because it was conflicting the new permission model 
    @Test(expectedExceptions = AccessDeniedException.class)
    public void alterStatusAccount() throws Exception{alterStatusAccount(finance);}

    // FIXME: the expectation is changed here because it was conflicting the new permission model 
    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveAccount() throws Exception{saveAccount(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void createAccount() throws Exception{createAccount(finance);}

    @Test
    public void viewDomain() throws Exception{viewDomain(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void editDomain() throws Exception{editDomain(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveDomain() throws Exception{saveDomain(finance);}

//    @Test (expected = AccessDeniedException.class)
//    public void transferDomain() throws Exception{transferDomain(finance);}

    @Test
    public void getNicHandle() throws Exception{getNicHandle(finance);}

    @Test
    public void getNicHandleHistory() throws Exception{getNicHandleHistory(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void alterStatusNicHandle() throws Exception{alterStatusNicHandle(finance);}

    @Test(expectedExceptions = AccessDeniedException.class)
    public void saveNicHandle() throws Exception{saveNicHandle(finance);}

    @Test
    public void saveNewPasswordNicHandle() throws Exception{saveNewPasswordNicHandle(finance, "AAG45-IEDR");}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void generateNewPasswordNicHandle() throws Exception{generateNewPasswordNicHandle(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void createNicHandle() throws Exception{createNicHandle(finance);}

    @Test
    public void viewTicket() throws Exception{viewTicket(finance);}

    @Test
    public void viewTicketHistory() throws Exception{viewTicketHistory(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void reviseTicket() throws Exception {reviseTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void editTicket() throws Exception {editTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void checkOutTicket() throws Exception {checkOutTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void checkInTicket() throws Exception {checkInTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void alterStatusTicket() throws Exception {alterStatusTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void reassignTicket() throws Exception {reassignTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void saveTicket() throws Exception {saveTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void acceptTicket() throws Exception {acceptTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void rejectTicket() throws Exception {rejectTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void updateTicket() throws Exception {updateTicket(finance);}

    @Test (expectedExceptions = AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {changePermissionGroups(finance);}

    // FIXME: the expectation is changed here because it was conflicting the new permission model 
//    @Test
    @Test(expectedExceptions = AccessDeniedException.class)
    public void getUserHistory() throws Exception {getUserHistory(finance);}

}
