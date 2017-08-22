package pl.nask.crs.security.impl;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.easymock.EasyMock.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.authentication.impl.AuthenticationServiceImpl;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;

/**
 * @author Marianna Mysiorska
 */

public class AuthenticationServiceImplTest {

    private AuthenticationService authService;
    private UserDAO mockDAO;
    private HashAlgorithm mockAlgorithm;
    private ApplicationConfig mockedAppConfig;
    private LoginAttemptDAO loginAttemptDAO;

    @BeforeMethod
	public void init() {
        mockDAO = createMock(UserDAO.class);
        mockAlgorithm = createMock(HashAlgorithm.class);
        mockedAppConfig = createMock(ApplicationConfig.class);
        loginAttemptDAO = createMock(LoginAttemptDAO.class);
        authService = new AuthenticationServiceImpl(mockDAO, Arrays.asList(mockAlgorithm), mockedAppConfig, loginAttemptDAO);
        expect(mockDAO.get("IH4-IEDR")).andStubReturn(createUser());
        expect(mockDAO.get("ih4-iEdR")).andStubReturn(createUser());
        expect(mockDAO.get("USER-NOT-IN-DB")).andStubReturn(null);
        expect(mockAlgorithm.hashString("123456", "fQMUoHUvZ4giN5rq9y4mEe")).andReturn("p.KIBZJgwp1zaOriBKTX9dxvVis5.Pa");
        expect(mockAlgorithm.hashString("password not in db", "fQMUoHUvZ4giN5rq9y4mEe")).andReturn("hashed password not in db");
        expect(mockDAO.getLastPasswordChangeDate("IH4-IEDR")).andStubReturn(new Date(6L));
        expect(mockedAppConfig.getPasswordExpiryPeriod()).andStubReturn(30);
        expect(loginAttemptDAO.createAttempt((LoginAttempt) anyObject())).andReturn(1L).times(1);
        expect(loginAttemptDAO.getLastAttemptByNic((String) anyObject())).andReturn(LoginAttempt.newSuccessInstance("IH4-IEDR", "1.1.1.1")).times(1);
        replay(loginAttemptDAO);
        replay(mockDAO);
        replay(mockAlgorithm);
    }

    protected User createUser() {
        User u = new User();
        u.setUsername("IH4-IEDR");
        u.setPassword("p.KIBZJgwp1zaOriBKTX9dxvVis5.Pa");
        u.setSalt("fQMUoHUvZ4giN5rq9y4mEe");
        Permission perm = new FullAccessPermission("fullAccess", "fullAccess");
        Set<Permission> perms = new HashSet<Permission>();
        perms.add(perm);
        Group gr = new Group("HostmasterLead", perms);
        Set<Group> permGrs = new HashSet<Group>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    @Test
    public void authenticateUser() throws Exception {
        AuthenticatedUser u = authenticate("IH4-IEDR", "123456", false);         		
        assertTrue("IH4-IEDR".equals(u.getUsername()));
        verify(loginAttemptDAO);
    }

    private AuthenticatedUser authenticate(String user, String password, boolean validateExpiration) throws InvalidUsernameException, InvalidPasswordException, PasswordExpiredException, IllegalArgumentException, AuthenticationException {
    	return authService.authenticate(user, password, validateExpiration, "1.1.1.1", false, null, true, "crs");
	}

	@Test(expectedExceptions = PasswordExpiredException.class)
    public void authenticateUserPasswordExpired() throws Exception {
        authenticate("IH4-IEDR", "123456", true);
    }

    @Test(expectedExceptions = InvalidUsernameException.class)
    public void authenticateUserBadUsername() throws Exception {
        authenticate("USER-NOT-IN-DB", "Passw0rd!", false);
    }

    @Test(expectedExceptions = InvalidPasswordException.class)
    public void authenticateUserBadPassword() throws Exception {
        authenticate("IH4-IEDR", "password not in db", false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateUserNullUsername() throws Exception {
        authenticate(null, "Passw0rd!", false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateUserNullPassword() throws Exception {
        authenticate("IH4-IEDR", null, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void authenticateUserNullUsernameAndPassword() throws Exception {
        authenticate(null, null, false);
    }

    @Test
    public void authenticateUserUsernameToTrim() throws Exception {
        AuthenticatedUser u = authenticate(" IH4-IEDR  ", "123456", false);
        assertTrue("IH4-IEDR".equals(u.getUsername()));
        verify(loginAttemptDAO);
    }

    @Test
    public void authenticateUserUsernameCaseInsensitive() throws Exception {
        AuthenticatedUser u = authenticate("ih4-iEdR", "123456", false);
        assertTrue("IH4-IEDR".equals(u.getUsername()));
        verify(loginAttemptDAO);
    }

}