package pl.nask.crs.app.users.impl;

import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Level;

/**
 * 
 * @author Artur Gniadzik
 * 
 */
public class ChangePermissionsTechnicalTest extends AbstractChangePermissionsTest {

    /*
     * technical group - valid operations first
     */
    // technical group - part 1
    @Test
    public void testAllowed1() throws AccessDeniedException {       
        // LeadHostmaster -> CS
        internalTestChangeLevel(TECHNICAL, HOSTMASTER_LEAD, Level.HostmasterLead, Level.Hostmaster);
    }

    // technical group - part 2
    @Test
    public void testAllowed2() throws AccessDeniedException {
        // hostmaster -> hostmasterLead
        internalTestChangeLevel(TECHNICAL, HOSTMASTER, Level.Hostmaster, Level.HostmasterLead);
        // CS -> hostmasterLead
    }

    // technical group - part 3 (* -> finance)
    @Test
    public void testAllowed3() throws AccessDeniedException {
        // hostmaster -> finance
        internalTestChangeLevel(TECHNICAL, HOSTMASTER, Level.Hostmaster, Level.Finance);
        // LeadHostmaster -> finance
        internalTestChangeLevel(TECHNICAL, HOSTMASTER_LEAD, Level.HostmasterLead, Level.Finance);
    }

    // technical group - part 5 (forbidden operations)
    @Test
    public void testForbidden() {
        List<Level> forbidden = Arrays.asList(Level.Technical, Level.TechnicalLead, Level.Registrar, Level.Batch, Level.SuperRegistrar);
        
        List<Level> allForbidden = Arrays.asList(Level.values());
        
        internalTestChangeLevelForbidden(TECHNICAL, HOSTMASTER, Level.Hostmaster, forbidden);
        internalTestChangeLevelForbidden(TECHNICAL, HOSTMASTER_LEAD, Level.HostmasterLead, forbidden);
        
        internalTestChangeLevelForbidden(TECHNICAL, TECHNICAL_LEAD, Level.TechnicalLead, allForbidden);
        internalTestChangeLevelForbidden(TECHNICAL, TECHNICAL, Level.Technical, allForbidden);
        internalTestChangeLevelForbidden(TECHNICAL, FINANCE, Level.Finance, allForbidden);
        internalTestChangeLevelForbidden(TECHNICAL, REGISTRAR, Level.Registrar, allForbidden);
        internalTestChangeLevelForbidden(TECHNICAL, BATCH, Level.Batch, allForbidden);
                
    }
}
