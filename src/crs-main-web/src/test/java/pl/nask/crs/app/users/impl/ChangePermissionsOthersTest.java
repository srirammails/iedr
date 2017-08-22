package pl.nask.crs.app.users.impl;

import org.testng.annotations.Test;

/**
 * Groups: Finance, Lead Finance, Guest, Reseller, Customer Service, Batch and
 * Registrar (no such user in a database currently) have no permissions to
 * change any access level.
 * 
 * 
 * @author Artur Gniadzik
 * 
 */
public class ChangePermissionsOthersTest extends AbstractChangePermissionsTest {

    @Test
    public void testForbidden() {    
        internalTestChangeLevelForbidden(FINANCE);
        internalTestChangeLevelForbidden(FINANCE_LEAD);
        internalTestChangeLevelForbidden(GUEST);
        internalTestChangeLevelForbidden(REGISTRAR);
        internalTestChangeLevelForbidden(CUSTOMER_SERVICE);
        internalTestChangeLevelForbidden(BATCH);                
    }
}
