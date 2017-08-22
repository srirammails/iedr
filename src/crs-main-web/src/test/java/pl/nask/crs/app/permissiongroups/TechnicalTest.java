package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class TechnicalTest extends AbstractPermissionGroupTest {

    AuthenticatedUser technical;

    @BeforeMethod
	public void setUp() throws Exception{
        technical = authenticationService.authenticate("AAE553-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void getAccount() throws Exception{ getAccount(technical);}

    @Test
    public void getAccountHistory() throws Exception {getAccountHistory(technical);}

    @Test(expectedExceptions=AccessDeniedException.class)
    public void alterStatusAccount() throws Exception{alterStatusAccount(technical);}

    @Test
    public void saveAccount() throws Exception{saveAccount(technical);}

    @Test
    public void createAccount() throws Exception{createAccount(technical);}

    @Test
    public void viewDomain() throws Exception{viewDomain(technical);}

    @Test
    public void editDomain() throws Exception{editDomain(technical);}

    @Test
    public void saveDomain() throws Exception{saveDomain(technical);}

//    @Test
//    public void transferDomain() throws Exception{transferDomain(technical);}

    @Test
    public void getNicHandle() throws Exception{getNicHandle(technical);}

    @Test
    public void getNicHandleHistory() throws Exception{getNicHandleHistory(technical);}

    @Test
    public void alterStatusNicHandle() throws Exception{alterStatusNicHandle(technical);}

    @Test
    public void saveNicHandle() throws Exception{saveNicHandle(technical);}

    @Test
    public void saveNewPasswordNicHandle() throws Exception{saveNewPasswordNicHandle(technical);}

    @Test
    public void generateNewPasswordNicHandle() throws Exception{generateNewPasswordNicHandle(technical);}

    @Test
    public void createNicHandle() throws Exception{createNicHandle(technical);}

    @Test
    public void viewTicket() throws Exception{viewTicket(technical);}

    @Test
    public void viewTicketHistory() throws Exception{viewTicketHistory(technical);}

    @Test
    public void reviseTicket() throws Exception {reviseTicket(technical);}

    @Test
    public void editTicket() throws Exception {editTicket(technical);}

    @Test
    public void checkOutTicket() throws Exception {checkOutTicket(technical);}

    @Test
    public void checkInTicket() throws Exception {checkInTicket(technical);}

    @Test
    public void alterStatusTicket() throws Exception {alterStatusTicket(technical);}

    @Test
    public void reassignTicket() throws Exception {reassignTicket(technical);}

    @Test
    public void saveTicket() throws Exception {saveTicket(technical);}

    @Test
    public void acceptTicket() throws Exception {acceptTicket(technical);}

    @Test
    public void rejectTicket() throws Exception {rejectTicket(technical);}

    @Test
    public void updateTicket() throws Exception {updateTicket(technical);}

    @Test
    public void changePermissionGroups() throws Exception {changePermissionGroups(technical);}

    @Test
    public void getUserHistory() throws Exception {getUserHistory(technical);}
    
    @Test(expectedExceptions=AccessDeniedException.class)
    public void getHmUsage() throws Exception {getHmUsage(technical);}
}
