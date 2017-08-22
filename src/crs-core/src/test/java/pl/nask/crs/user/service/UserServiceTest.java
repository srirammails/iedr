package pl.nask.crs.user.service;

import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.ConvertingUserDAO;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;

@ContextConfiguration(locations = { "/users-config.xml", "/users-config-test.xml", "/test-config.xml", "/commons-base-config.xml" })
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    static final Logger log = Logger.getLogger(UserServiceTest.class);

    @Autowired
    ConvertingUserDAO userDAO;

    @Resource
    HashAlgorithm hashAlg;

    @Autowired
    UserService userService;

//TODO: CRS-72
    // service method expects plain password to be used
//    @Test
//    public void testChangePassword() {
//        log.info("start: testChangePassword()");
//        // get user with password encoded with oldPassword
//        User user = userDAO.get("ih4-iEDr");
//        // No Need: AssertJUnit.assertEquals(hashAlg.hashString("123456", user.getSalt()), user.getPassword());
//
//        // change password - salted should be used...
//        user.setPassword("Passw0rd!");
//        try {
//            userService.changePassword("ih4-iEDr", "Passw0rd!", "ih4-iEDr");
//        } catch (PasswordAlreadyExistsException e1) {
//            Assert.fail("First password change should not fail");
//        }
//
//        user = userDAO.get("ih4-iEDr");
//        AssertJUnit.assertEquals(hashAlg.hashString("Passw0rd!", user.getSalt()), user.getPassword());
//
//        // second password change should fail!
//        try {
//            userService.changePassword("ih4-iEDr", "Passw0rd!", "ih4-iEDr");
//            Assert.fail("Second password change should fail");
//        } catch (PasswordAlreadyExistsException e) {
//            // exception expected
//        }
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        try {
//            userService.changePassword("ih4-iEDr", "Passw0rd!", "newPass", "ih4-iEDr");
//        } catch (PasswordAlreadyExistsException e) {
//            Assert.fail("Password change with old pass should pass");
//        } catch (InvalidOldPasswordException e) {
//            Assert.fail("Password change with old pass should pass");
//        }
//
//        user = userDAO.get("ih4-iEDr");
//        AssertJUnit.assertEquals(hashAlg.hashString("newPass", user.getSalt()), user.getPassword());
//
//        try {
//            userService.changePassword("ih4-iEDr", "wrong pass", "1234", "ih4-iEDr");
//        } catch (PasswordAlreadyExistsException e) {
//            Assert.fail("Password change with old pass should pass");
//        } catch (InvalidOldPasswordException e) {
//            //expected exception
//        }
//
//        log.info("success: testChangePassword()");
//    }

    @Test
    public void testChangePermissionGroups(){
    	String testedUserNh = "AAB069-IEDR";
        User user = userDAO.get(testedUserNh);
        HashSet<Permission> fullAccessSet = new HashSet<Permission>();
        HashSet<Group> permissionGroup = new HashSet<Group>();
        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        fullAccessSet.add(fullAccess);
        Group customerService = new Group("Batch", fullAccessSet);
        permissionGroup.add(customerService);
        AssertJUnit.assertEquals(user.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user.getPermissionGroups().contains(customerService));

        HashSet<Group> newPermissionGroup = new HashSet<Group>();
        HashSet<Permission> emptySet = new HashSet<Permission>();
        Group reseller = new Group("Registrar", "Reseller", emptySet);
        newPermissionGroup.add(reseller);

        userService.changePermissionGroups(user, permissionGroup, newPermissionGroup, "TEST-IEDR");

        User user2 = userDAO.get(testedUserNh);
        AssertJUnit.assertEquals(user2.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user2.getPermissionGroups().contains(reseller));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullUser(){
        userService.changePermissionGroups(null, null, null, null);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullGroup(){
        User user = userDAO.get("AAA442-IEDR");
        userService.changePermissionGroups(user, null, null, "TEST-IEDR");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullGroup2(){
        User user = userDAO.get("AAA442-IEDR");
        HashSet<Group> groups = new HashSet<Group>();
        userService.changePermissionGroups(user, groups, null, "TEST-IEDR");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testChangePermissionGroupsNullUsername(){
        User user = userDAO.get("AAA442-IEDR");
        HashSet<Group> groups = new HashSet<Group>();
        userService.changePermissionGroups(user, groups, groups, null);
    }

    @Test
    public void testChangePermissionGroupsEmptyGroups(){
        User user = userDAO.get("AAA442-IEDR");
        HashSet<Group> groups = new HashSet<Group>();
        userService.changePermissionGroups(user, groups, groups, "TEST-IEDR");
    }
    
    @Test
    public void testChangeUseTfa() {
        // TODO: Fix brittle test as it depends on previous state of the account in db
    	String nic = "AAA442-IEDR";
    	User user = userDAO.get(nic);   // Default in DB at the moment is NOT to use two factor
    	boolean originalTfaFlag = user.isUseTwoFactorAuthentication();
    	
    	String secret = userService.changeTfa(nic, !originalTfaFlag);
    	AssertJUnit.assertEquals(!originalTfaFlag, userDAO.get(nic).isUseTwoFactorAuthentication());
        Assert.assertNotNull(secret);

    	secret = userService.changeTfa(nic, originalTfaFlag);
    	AssertJUnit.assertEquals(originalTfaFlag, userDAO.get(nic).isUseTwoFactorAuthentication());
        Assert.assertNull(secret);
    }

}
