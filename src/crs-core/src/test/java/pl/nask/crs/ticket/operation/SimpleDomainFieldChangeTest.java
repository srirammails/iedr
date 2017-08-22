package pl.nask.crs.ticket.operation;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * Tests the behaviour of the SimpleDomainFieldChange
 * @author Artur Gniadzik
 *
 */
public class SimpleDomainFieldChangeTest {	
	@Test
	public void testSameStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>("a", "a");
		AssertJUnit.assertFalse(c.isModification());
	}
	
	@Test
	public void testDifferentStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>("a", "b");
		AssertJUnit.assertTrue(c.isModification());
	}
	
	@Test
	public void testEmptyAndNullStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>(null, "");
		AssertJUnit.assertFalse(c.isModification());
	}
	
	@Test
	public void testNotEmptyAndNullStringValues2() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>(null, "sss");
		AssertJUnit.assertTrue(c.isModification());
	}

	
	@Test
	public void testNotEmptyAndNullStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>("a", null);
		AssertJUnit.assertTrue(c.isModification());
	}
	
	@Test
	public void testNotEmptyAndEmptyStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>("a", "");
		AssertJUnit.assertTrue(c.isModification());
	}
	
	@Test
	public void testEmptyStringValues() {
		SimpleDomainFieldChange c = new SimpleDomainFieldChange<String>(" ", "");
		AssertJUnit.assertFalse(c.isModification());
	}
	
}
