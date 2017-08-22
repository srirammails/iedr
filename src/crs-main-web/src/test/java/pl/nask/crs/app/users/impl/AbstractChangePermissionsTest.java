package pl.nask.crs.app.users.impl;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.AssertJUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.AbstractCrsTest;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

public abstract class AbstractChangePermissionsTest extends AbstractCrsTest {

    /*
     * nic handles which have an access level set
     */
    public static final String TECHNICAL_LEAD = "AAE359-IEDR";
    public static final String TECHNICAL = "AAE553-IEDR";
    public static final String HOSTMASTER_LEAD = "AAA967-IEDR";
    public static final String HOSTMASTER = "AAA906-IEDR";
    public static final String FINANCE_LEAD = "AAG061-IEDR";
    public static final String FINANCE = "AAG45-IEDR";
    public static final String GUEST = "AA11-IEDR";
    public static final String REGISTRAR = "AAA22-IEDR";
    public static final String CUSTOMER_SERVICE = "AAA442-IEDR";
    public static final String BATCH = "AAB069-IEDR";
    


    @Autowired
    UserAppService userAppService;
    
    @Autowired
    AuthorizationGroupsFactory groupsFactory;

    /**
     * tests, if the user with nic handle given as a parameter has a right to
     * change any permission. If it has, the test fails.
     * 
     * @param authUsername
     *            tested user nic handle
     */                              
    void internalTestChangeLevelForbidden(String authUsername) {
        List<Level> allForbidden = Arrays.asList(Level.values());
        

        internalTestChangeLevelForbidden(authUsername, HOSTMASTER, Level.Hostmaster, allForbidden);
        internalTestChangeLevelForbidden(authUsername, HOSTMASTER_LEAD, Level.HostmasterLead, allForbidden);

        internalTestChangeLevelForbidden(authUsername, TECHNICAL_LEAD, Level.TechnicalLead, allForbidden);
        internalTestChangeLevelForbidden(authUsername, TECHNICAL, Level.Technical, allForbidden);
        internalTestChangeLevelForbidden(authUsername, FINANCE, Level.Finance, allForbidden);
        internalTestChangeLevelForbidden(authUsername, REGISTRAR, Level.Registrar, allForbidden);
        internalTestChangeLevelForbidden(authUsername, BATCH, Level.Batch, allForbidden);
    }

    /**
     * checks, if the user (given by authUsername) has a right to change an
     * access level for another user (given by testedUsername)
     * 
     * @param authUsername
     * @param testedUsername
     * @param from
     * @param to
     * @throws AccessDeniedException
     *             , if authUsername cannot change permission for testedUsername
     */
    void internalTestChangeLevel(String authUsername, String testedUsername, Level from, Level to) throws AccessDeniedException {
        internalTestChangeGroup(authUsername, testedUsername, createGroupSet(from), createGroupSet(to));
    }
      
    /**
     * checks, if authUsername has a right to change access level for
     * testedUsername to one of 'to' levels. fails, if it has.
     * 
     * @param authUsername
     * @param testedUsername
     * @param from
     * @param to
     */
    void internalTestChangeLevelForbidden(String authUsername, String testedUsername, Level from, Collection<Level> to) {
        for (Level l : to) {
            if (from != l) { 
                try {
                    internalTestChangeGroup(authUsername, testedUsername, createGroupSet(from), createGroupSet(l));
                    AssertJUnit.fail("Exception expected when changing access level from " + from.getName() + " to " + l.getName() + " by user " + authUsername);
                } catch (AccessDeniedException e) {
                    // exception expected
                }
            }
        }
    }
    
        
    void internalTestChangeGroup(String authUsername, String testedUsername, Set<Group> from, Set<Group> to) throws AccessDeniedException {
        // technical lead can do anything
        AuthenticatedUser au = makeAuthUser(authUsername);
        User u = userAppService.getUser(testedUsername);

        Set<Group> permissions = new HashSet<Group>(u.getPermissionGroups());
        for (Group g : from) {
            permissions.remove(g);
        }

        for (Group g : to) {
            permissions.add(g);
        }
        
        // EVERY user has a default group.
        permissions.add(new Group(Level.Default, "Default", Collections.EMPTY_SET));

        userAppService.changePermissionGroups(au, u, from, to);

        // check, that all groups are set
        User u2 = userAppService.getUser(testedUsername);

           assertEquals(permissions, u2.getPermissionGroups());
    }

    /**
     * creates a singleton with a Group matching the level given as a parameter
     * 
     * @param l
     * @return
     */
    Set<Group> createGroupSet(Level l) {
        Set<Group> s = new HashSet<Group>();
        s.add(groupsFactory.getGroupByName(l.getName()));
        return s;
    }
    
    private AuthenticatedUser makeAuthUser(final String username) {
        return new AuthenticatedUser() {
            public String getUsername() {
                return username;
            }

			public String getAuthenticationToken() {
				return username;
			}
			
			@Override
			public String getSuperUserName() {
				return null;
			}
        };
    }

}
