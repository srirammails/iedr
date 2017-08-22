package pl.nask.crs.security.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.UserNotSwitchedException;
import pl.nask.crs.security.authentication.WsAuthenticationService;
import pl.nask.crs.security.authentication.impl.WsAuthenticationServiceImpl;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.LoginPermission;
import pl.nask.crs.user.permissions.Permission;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class WsAuthenticationServiceImplTest {

    private WsAuthenticationService authService;
    private UserDAO mockDAO;
    private HashAlgorithm mockAlgorithm;
    private ApplicationConfig mockAppConfig;
    private LoginAttemptDAO loginAttemptDAO;

    @BeforeMethod
	public void init() {
        mockDAO = createMock(UserDAO.class);
        mockAlgorithm = createMock(HashAlgorithm.class);
        mockAppConfig = createMock(ApplicationConfig.class);
        loginAttemptDAO = createMock(LoginAttemptDAO.class);
        authService = new WsAuthenticationServiceImpl(mockDAO, Arrays.asList(mockAlgorithm), mockAppConfig, loginAttemptDAO);
        expect(mockAppConfig.getUserSessionTimeout()).andStubReturn(2);
        expect(mockAppConfig.getPasswordExpiryPeriod()).andStubReturn(10);
        expect(mockDAO.get("IH4-IEDR")).andStubReturn(createSuperUser());
        expect(mockDAO.get("G1-IEDR")).andStubReturn(createUser());
        expect(mockDAO.get("G2-IEDR")).andStubReturn(null);
        expect(mockDAO.get("G3-IEDR")).andStubReturn(createUserWithNoLoginPermissions());
        expect(mockDAO.get("USER-NOT-IN-DB")).andStubReturn(null);
        expect(mockDAO.getLastPasswordChangeDate((String) anyObject())).andStubReturn(new Date());
        expect(mockAlgorithm.hashString("123456", "wMskkVE8gIIZ/0HFaVeBtu")).andStubReturn("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG");
        expect(loginAttemptDAO.createAttempt((LoginAttempt) anyObject())).andReturn(1L).times(1);
        expect(loginAttemptDAO.getLastAttemptByNic((String) anyObject())).andReturn(LoginAttempt.newSuccessInstance("IH4-IEDR", "1.1.1.1")).times(1);
        replay(mockDAO);
        replay(mockAlgorithm);
        replay(mockAppConfig);
        replay(loginAttemptDAO);
    }

    protected User createSuperUser() {
        User u = new User();
        u.setUsername("IH4-IEDR");
        u.setPassword("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG");
        u.setSalt("wMskkVE8gIIZ/0HFaVeBtu");
        Permission perm = new FullAccessPermission("fullAccess", "fullAccess");
        Set<Permission> perms = new HashSet<Permission>();
        perms.add(perm);
        Group gr = new Group("HostmasterLead", perms);
        Set<Group> permGrs = new HashSet<Group>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    protected User createUser() {
        User u = new User();
        u.setUsername("G1-IEDR");
        u.setPassword("69bac2841e83eb64");
        Permission perm = new LoginPermission(LoginPermission.WS);
        Set<Permission> perms = new HashSet<Permission>();
        perms.add(perm);
        Group gr = new Group("Direct", perms);
        Set<Group> permGrs = new HashSet<Group>();
        permGrs.add(gr);
        u.setPermissionGroups(permGrs);
        return u;
    }

    protected User createUserWithNoLoginPermissions() {
        User u = new User();
        u.setUsername("G1-IEDR");
        u.setPassword("69bac2841e83eb64");
        Set<Group> permGrs = new HashSet<Group>();
        u.setPermissionGroups(permGrs);
        return u;
    }

    @Test
    public void switchUserTest() throws Exception {
        AuthenticatedUser user = null;
        boolean isSwitched = false;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        AssertJUnit.assertEquals("IH4-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertFalse(isSwitched);

        user = authService.switchUser(user, "G1-IEDR");
        AssertJUnit.assertEquals("G1-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);

        user = authService.unswitch(user);
        AssertJUnit.assertEquals("IH4-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertFalse(isSwitched);
    }

    @Test(expectedExceptions = InvalidUsernameException.class)
    public void switchToInvalidUserTest() throws Exception {
        AuthenticatedUser user = null;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        user = authService.switchUser(user, "G2-IEDR");
    }    

    @Test(expectedExceptions = UserNotSwitchedException.class)
    public void unswitchWhenNoSwitchTest() throws Exception {
        AuthenticatedUser user = null;
        user = authService.authenticate("IH4-IEDR", "123456", false, "1.1.1.1", false, null, true, "ws");
        user = authService.unswitch(user);
    }

    @Test
    public void authenticateAndSwitchUserTest() throws Exception {
        AuthenticatedUser user = authService.authenticateAndSwitchUser("IH4-IEDR", "G1-IEDR", "123456", false, "1.1.1.1", false);
        AssertJUnit.assertNotNull(user);
        boolean isSwitched = authService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);
    }
}
