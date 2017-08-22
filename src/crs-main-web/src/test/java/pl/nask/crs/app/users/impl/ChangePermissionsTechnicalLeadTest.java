package pl.nask.crs.app.users.impl;

import org.testng.annotations.Test;
import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;

/**
 * 
 * @author Artur Gniadzik
 * 
 */
public class ChangePermissionsTechnicalLeadTest extends AbstractChangePermissionsTest {

    
    /*
     * LeadTechnical group
     */
    @Test
    public void testSetPermissionGroupsTechnicalLead() throws AccessDeniedException {
        Set<Group> allPerms = new HashSet<Group>(groupsFactory.getAllGroups());

        // set all permissions
        internalTestChangeGroup(TECHNICAL_LEAD, TECHNICAL_LEAD, allPerms, allPerms);
        // change to technicalLead only
        internalTestChangeGroup(TECHNICAL_LEAD, TECHNICAL_LEAD, allPerms, createGroupSet(Level.TechnicalLead));
    }
    
    
}
