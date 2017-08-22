package pl.nask.crs.app.users.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import pl.nask.crs.user.Level;

/**
 * @author Artur Gniadzik
 * 
 */
public class ChangePermissionsHostmasterTest extends AbstractChangePermissionsTest {
    
   

    @Test
    public void testForbidden() {
        List<Level> allForbidden = Arrays.asList(Level.values());
        
        Set<Level> guestForbidden = new HashSet<Level>(allForbidden);
        guestForbidden.remove(Level.Registrar);
        
        Set<Level> resellerForbidden = new HashSet<Level>(allForbidden);
        
        internalTestChangeLevelForbidden(HOSTMASTER, REGISTRAR, Level.Registrar, resellerForbidden);

        internalTestChangeLevelForbidden(HOSTMASTER, HOSTMASTER, Level.Hostmaster, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER, HOSTMASTER_LEAD, Level.HostmasterLead, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER, TECHNICAL_LEAD, Level.TechnicalLead, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER, TECHNICAL, Level.Technical, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER, FINANCE, Level.Finance, allForbidden);
        internalTestChangeLevelForbidden(HOSTMASTER, BATCH, Level.Batch, allForbidden);
    }
}
