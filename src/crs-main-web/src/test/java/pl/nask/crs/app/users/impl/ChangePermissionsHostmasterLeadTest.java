package pl.nask.crs.app.users.impl;

import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.user.Level;

/**
 * 
 * @author Artur Gniadzik
 * 
 */
public class ChangePermissionsHostmasterLeadTest extends AbstractChangePermissionsTest {
    
    @Test
    public void testForbidden() {
        
        List<Level> allForbidden = Arrays.asList(Level.values());
        
        Set<Level> guestForbidden = new HashSet<Level>(allForbidden);
        guestForbidden.remove(Level.Registrar);

        Set<Level> resellerForbidden = new HashSet<Level>(allForbidden);
        
        Set<Level> hostmasterForbidden = new HashSet<Level>(allForbidden);

        Set<Level> csForbidden = new HashSet<Level>(allForbidden);
        csForbidden.remove(Level.Hostmaster);
        
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, REGISTRAR, Level.Registrar, resellerForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, HOSTMASTER, Level.Hostmaster, hostmasterForbidden);


        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, HOSTMASTER_LEAD, Level.HostmasterLead, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, TECHNICAL_LEAD, Level.TechnicalLead, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, TECHNICAL, Level.Technical, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, FINANCE, Level.Finance, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER_LEAD, BATCH, Level.Batch, allForbidden);        
    }
}
