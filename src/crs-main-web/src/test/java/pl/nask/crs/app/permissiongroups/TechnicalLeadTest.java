package pl.nask.crs.app.permissiongroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 */
public class TechnicalLeadTest extends AbstractPermissionGroupTest {
    
    AuthenticatedUser technicalLead;

    @BeforeMethod
	public void setUp() throws Exception{
        technicalLead = authenticationService.authenticate("AAE359-IEDR", "Passw0rd!", false, "1.1.1.1", false, null, true, "crs");
    }

    @Test
    public void getAccount() throws Exception{ getAccount(technicalLead);}

    @Test
    public void getAccountHistory() throws Exception {getAccountHistory(technicalLead);}

    @Test
    public void alterStatusAccount() throws Exception{alterStatusAccount(technicalLead);}

    @Test
    public void saveAccount() throws Exception{saveAccount(technicalLead);}

    @Test
    public void createAccount() throws Exception{createAccount(technicalLead);}

    @Test
    public void viewDomain() throws Exception{viewDomain(technicalLead);}

    @Test
    public void editDomain() throws Exception{editDomain(technicalLead);}

    @Test
    public void saveDomain() throws Exception{saveDomain(technicalLead);}

//    @Test
//    public void transferDomain() throws Exception{transferDomain(technicalLead);}

    @Test
    public void getNicHandle() throws Exception{getNicHandle(technicalLead);}

    @Test
    public void getNicHandleHistory() throws Exception{getNicHandleHistory(technicalLead);}

    @Test
    public void alterStatusNicHandle() throws Exception{alterStatusNicHandle(technicalLead);}

    @Test
    public void saveNicHandle() throws Exception{saveNicHandle(technicalLead);}

    @Test
    public void saveNewPasswordNicHandle() throws Exception{saveNewPasswordNicHandle(technicalLead);}

    @Test
    public void generateNewPasswordNicHandle() throws Exception{generateNewPasswordNicHandle(technicalLead);}

    @Test
    public void createNicHandle() throws Exception{createNicHandle(technicalLead);}

    @Test
    public void viewTicket() throws Exception{viewTicket(technicalLead);}

    @Test
    public void viewTicketHistory() throws Exception{viewTicketHistory(technicalLead);}

    @Test
    public void reviseTicket() throws Exception {reviseTicket(technicalLead);}

    @Test
    public void editTicket() throws Exception {editTicket(technicalLead);}

    @Test
    public void checkOutTicket() throws Exception {checkOutTicket(technicalLead);}

    @Test
    public void checkInTicket() throws Exception {checkInTicket(technicalLead);}

    @Test
    public void alterStatusTicket() throws Exception {alterStatusTicket(technicalLead);}

    @Test
    public void reassignTicket() throws Exception {reassignTicket(technicalLead);}

    @Test
    public void saveTicket() throws Exception {saveTicket(technicalLead);}

    @Test
    public void acceptTicket() throws Exception {acceptTicket(technicalLead);}

    @Test
    public void rejectTicket() throws Exception {rejectTicket(technicalLead);}

    @Test
    public void updateTicket() throws Exception {updateTicket(technicalLead);}

    @Test
    public void changePermissionGroups() throws Exception {changePermissionGroups(technicalLead);}

    @Test
    public void getUserHistory() throws Exception {getUserHistory(technicalLead);}
}
