package pl.nask.crs.user.dao;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.ConvertingUserDAO;
import pl.nask.crs.user.permissions.FullAccessPermission;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.search.UserSearchCriteria;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;


/**
 * @author Marianna Mysiorska
 */
@ContextConfiguration(locations = {"/users-config.xml", "/users-config-test.xml", "/test-config.xml", "/commons-base-config.xml"})
public class UserIBatisDAOTest extends AbstractTransactionalTestNGSpringContextTests {
    static final Logger log = Logger.getLogger(UserIBatisDAOTest.class);

    private static UserSearchCriteria criteria;

    @Autowired
    ConvertingUserDAO userDAO;

    @Resource
    HashAlgorithm saltedHashAlg;

    @Test
    public void getUser() {
        System.err.println("getUser");
        User u = userDAO.get("IH4-IEDR");
        //TODO: AssertJUnit.assertEquals("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG", u.getPassword());
        //TODO: AssertJUnit.assertEquals("wMskkVE8gIIZ/0HFaVeBtu", u.getSalt());
        //TODO: AssertJUnit.assertEquals("yx5haibkacqvpll5", u.getSecret());
        assertEquals(6, u.getPermissionGroups().size());
        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        Set<Permission> perms = new HashSet<Permission>();
        perms.add(fullAccess);
        Group registrar = new Group("Registrar", perms);
        Group finance = new Group("Finance", perms);
        Group hostmaster = new Group("Hostmaster", perms);
        Group hostmasterLead = new Group("HostmasterLead", perms);
        Group batch = new Group("Batch", perms);
        Group technicalLead = new Group("TechnicalLead", perms);
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(registrar));
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(finance));
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(hostmaster));
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(hostmasterLead));
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(batch));
        AssertJUnit.assertTrue(u.getPermissionGroups().contains(technicalLead));
        assertEquals("IH4-IEDR", u.getUsername());
    }

    @Test
    public void getUserBadUsername() {
        User u = userDAO.get("bad username");
        AssertJUnit.assertNull(u);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getUserNullUsername() {
        User u = userDAO.get(null);
        AssertJUnit.assertNull(u);
    }

    @Test
    public void getUserCaseInsensitive() {
        log.info("getUserCaseInsensitive()");
        User u = userDAO.get("ih4-iEDr");
        assertEquals("IH4-IEDR", u.getUsername());
        // TODO: AssertJUnit.assertEquals("9uEqdZSZtPSqK0C07C5v48iOhUr3vtG", u.getPassword());
        // TODO: AssertJUnit.assertEquals("wMskkVE8gIIZ/0HFaVeBtu", u.getSalt());
        // TODO: AssertJUnit.assertEquals("yx5haibkacqvpll5", u.getSecret());
        assertEquals(6, u.getPermissionGroups().size());
        log.info("success: getUserCaseInsensitive()");
    }

    @Test
    public void find() {
        SearchResult<User> result = userDAO.find(criteria);
        assertEquals(7, result.getResults().size());
    }

    @Test
    public void findLimited() {
        LimitedSearchResult<User> result = userDAO.find(criteria, 1, 2);
        assertEquals(2, result.getResults().size());
        User u = result.getResults().get(0);
        assertEquals("AAA967-IEDR", u.getUsername());
        //TODO: AssertJUnit.assertEquals("SmyZx8i/1FlXRFNhcnfrznCeZ7PYA/i", u.getPassword());
        assertEquals(1, u.getPermissionGroups().size());
    }

    @Test
    public void changePassword() {
        // hashing is not done in sql statement any more - DAO expects hashed
        // password to be supplied
        User user = userDAO.get("IH4-IEDR");
        String salt1 = saltedHashAlg.getSalt();
        String password1 = saltedHashAlg.hashString("newPassword", salt1);
        user.setPassword(password1);
        user.setSalt(salt1);
        userDAO.changePassword(user);
        User user2 = userDAO.get("IH4-IEDR");
        String password2 = user2.getPassword();
        String salt2 = user2.getSalt();
        assertEquals(password1, password2);
        assertEquals(salt1, salt2);
    }

    @Test
    public void createUser() {
        // hashing is not done in sql statement any more - DAO expects hashed
        // password to be supplied
        User user = new User();
        user.setUsername("A007-NASK");
        String salt = saltedHashAlg.getSalt();
        user.setPassword(saltedHashAlg.hashString("password123", salt));
        user.setSalt(salt);
        Set<Permission> perms = new HashSet<Permission>();
        // perms.add(new ReadOnlyPermission("A007-NASK"));
        Group gr = new Group("Hostmaster", perms);
        Set<Group> groups = new HashSet<Group>();
        groups.add(gr);
        user.setPermissionGroups(groups);
        userDAO.create(user);
        User user2 = userDAO.get("A007-NASK");
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getSalt(), user2.getSalt());
        assertEquals(1, user.getPermissionGroups().size());
    }

    @Test
    public void changePermissions(){
    	String testerUserNh = "AAB069-IEDR";
        User user = userDAO.get(testerUserNh);
        HashSet<Permission> emptySet = new HashSet<Permission>();
        Group existingGroup = new Group("Batch", emptySet);
        assertEquals(user.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user.getPermissionGroups().contains(existingGroup));
        HashSet<Group> newPermissionGroup = new HashSet<Group>();
        Permission fullAccess = new FullAccessPermission("query", "fullAccess");
        HashSet<Permission> fullAccessSet = new HashSet<Permission>();
        fullAccessSet.add(fullAccess);
        Group newGroup = new Group("Registrar", "Reseller", emptySet); 
        newPermissionGroup.add(newGroup);
        user.setPermissionGroups(newPermissionGroup);
        userDAO.changePermissions(user);
        User user2 = userDAO.get(testerUserNh);
        assertEquals(user2.getPermissionGroups().size(), 1);
        AssertJUnit.assertTrue(user2.getPermissionGroups().contains(newGroup));
    }

    @Test
    public void getInternalLoginUserTest() {
        List<InternalLoginUser> users = userDAO.getInternalUsers();
        assertEquals(1, users.size());
    }

    @Test
    public void testChangeSecret() throws Exception {
        User u = userDAO.get("IH4-IEDR");
        //Brittle - AssertJUnit.assertEquals("yx5haibkacqvpll5", u.getSecret());
        userDAO.changeSecret("IH4-IEDR", "somesecret");
        u = userDAO.get("IH4-IEDR");
        assertEquals("somesecret", u.getSecret());
        // Another change ? 
        userDAO.changeSecret("IH4-IEDR", "newsecret");
        u = userDAO.get("IH4-IEDR");
        assertEquals("newsecret", u.getSecret());
    }

    @Test
    public void testPassResetToken() {
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        userDAO.addPasswordReset("IH4-IEDR", "hash", aDate, "0.0.0.0");
        PasswordResetToken token = userDAO.getToken("hash");
        assertEquals("IH4-IEDR", token.getNicHandle());
        assertEquals("0.0.0.0", token.getRemoteIp());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), token.getExpires());
    }
}
