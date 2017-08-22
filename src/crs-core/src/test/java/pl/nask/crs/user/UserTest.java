package pl.nask.crs.user;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserTest {
	
	@Test
	public void testHasGroupString() {
		Set<Group> groups = new HashSet<Group>();
		groups.add(new Group("Registrar", Collections.EMPTY_SET));
		groups.add(new Group("Batch", Collections.EMPTY_SET));
		User u = new User("name", "sss", "salt", "secret", "name", groups, Collections.EMPTY_MAP, false);
		AssertJUnit.assertTrue(u.hasGroup("Registrar"));
		AssertJUnit.assertFalse(u.hasGroup("Technical"));
	}	
	
	@Test
	public void testHasGroupLevel() {
		Set<Group> groups = new HashSet<Group>();
		groups.add(new Group("Registrar", Collections.EMPTY_SET));
		groups.add(new Group("Batch", Collections.EMPTY_SET));
		User u = new User("name", "sss", "salt", "secret", "name", groups, Collections.EMPTY_MAP, false);
		AssertJUnit.assertTrue(u.hasGroup(Level.Registrar));
		AssertJUnit.assertFalse(u.hasGroup("Technical"));
	}	
}
