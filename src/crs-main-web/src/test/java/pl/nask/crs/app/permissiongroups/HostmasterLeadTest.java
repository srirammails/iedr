package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class HostmasterLeadTest extends AbstractPermissionGroupTest {

    AuthenticatedUser hostmasterLead;

    @BeforeMethod
	public void setUp() throws Exception{
        hostmasterLead = authenticationService.authenticate("AAA967-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void getAccount() throws Exception{ getAccount(hostmasterLead);}

    @Test
    public void getAccountHistory() throws Exception {getAccountHistory(hostmasterLead);}

    @Test
    public void alterStatusAccount() throws Exception{alterStatusAccount(hostmasterLead);}

    @Test
    public void saveAccount() throws Exception{saveAccount(hostmasterLead);}

    @Test
    public void createAccount() throws Exception{createAccount(hostmasterLead);}

    @Test
    public void viewDomain() throws Exception{viewDomain(hostmasterLead);}

    @Test
    public void editDomain() throws Exception{editDomain(hostmasterLead);}

	@Test
    public void saveDomain() throws Exception{saveDomain(hostmasterLead);}

//    @Test
//    public void transferDomain() throws Exception{transferDomain(hostmasterLead);}

    @Test
    public void getNicHandle() throws Exception{getNicHandle(hostmasterLead);}

    @Test
    public void getNicHandleHistory() throws Exception{getNicHandleHistory(hostmasterLead);}

    @Test
    public void alterStatusNicHandle() throws Exception{alterStatusNicHandle(hostmasterLead);}

    @Test
    public void saveNicHandle() throws Exception{saveNicHandle(hostmasterLead);}

    @Test
    public void saveNewPasswordNicHandle() throws Exception{saveNewPasswordNicHandle(hostmasterLead);}

    @Test
    public void generateNewPasswordNicHandle() throws Exception{generateNewPasswordNicHandle(hostmasterLead);}

    @Test
    public void createNicHandle() throws Exception{createNicHandle(hostmasterLead);}

    @Test
    public void viewTicket() throws Exception{viewTicket(hostmasterLead);}

    @Test
    public void viewTicketHistory() throws Exception{viewTicketHistory(hostmasterLead);}

    @Test
    public void reviseTicket() throws Exception {reviseTicket(hostmasterLead);}

    @Test
    public void editTicket() throws Exception {editTicket(hostmasterLead);}

    @Test
    public void checkOutTicket() throws Exception {checkOutTicket(hostmasterLead);}

    @Test
    public void checkInTicket() throws Exception {checkInTicket(hostmasterLead);}

    @Test
    public void alterStatusTicket() throws Exception {alterStatusTicket(hostmasterLead);}

    @Test
    public void reassignTicket() throws Exception {reassignTicket(hostmasterLead);}

    @Test
    public void saveTicket() throws Exception {saveTicket(hostmasterLead);}

    @Test
    public void acceptTicket() throws Exception {acceptTicket(hostmasterLead);}

    @Test
    public void rejectTicket() throws Exception {rejectTicket(hostmasterLead);}

    @Test
    public void updateTicket() throws Exception {updateTicket(hostmasterLead);}

    @Test(expectedExceptions=AccessDeniedException.class)
    public void changePermissionGroups() throws Exception {changePermissionGroups(hostmasterLead);}

    @Test
    public void getUserHistory() throws Exception {getUserHistory(hostmasterLead);}

    @Test
    public void getHmUsage() throws Exception {getHmUsage(hostmasterLead);}
}
