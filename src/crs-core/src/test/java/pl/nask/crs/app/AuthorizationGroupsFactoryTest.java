package pl.nask.crs.app;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class AuthorizationGroupsFactoryTest extends AbstractTestNGSpringContextTests {

    @Resource
    AuthorizationGroupsFactory authGroupFactory;

    @Test
    public void shouldHaveAllGroupsInjected() {
        AssertJUnit.assertEquals("groups.size", 10, authGroupFactory.getAllGroups().size());
    }

    @Test
    public void shouldHaveAllPermissionsInjected() {
        System.out.println(authGroupFactory.getAllPermissionNames());
        // if the permission is added or removed from the sprint context, this test will fail
        AssertJUnit.assertEquals("permissions.size\n", 141, authGroupFactory.getAllPermissionNames().size());
    }

    @Test
    public void shouldGroupsMatchLevels() {
        List<Group> groups = authGroupFactory.getAllGroups();
        for (Group g: groups) {
            AssertJUnit.assertNotNull("group.level", g.getLevel());
        }

        for (Level l: Level.values()) {
            AssertJUnit.assertNotNull("group for access level", authGroupFactory.getGroups(l.getLevel()));
        }
    }
}
