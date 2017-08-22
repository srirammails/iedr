package pl.nask.crs.commons.config;

import org.springframework.dao.DuplicateKeyException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.config.ConfigEntry.ConfigValueType;

import javax.annotation.Resource;

import java.util.List;

public class DaoConfigTest extends AbstractTest {
	@Resource
	IBatisDaoBasedConfig dao;
	
	@Test
	public void testInsertStringValue() {
		testInsertEntry(new ImmutableConfigEntry("key", "value", ConfigValueType.STRING));
	}
	
	@Test
	public void testInsertIntValue() {
		testInsertEntry(new ImmutableConfigEntry("key", "1", ConfigValueType.INT));
	}
	
	@Test
	public void testInsertBooleanValue() {
		testInsertEntry(new ImmutableConfigEntry("key", "1", ConfigValueType.BOOLEAN));
	}
	
	@Test
	public void testLongStringValue() {
		testInsertEntry(new ImmutableConfigEntry("key", "1", ConfigValueType.LONG));
	}
	
	@Test(expectedExceptions=DuplicateKeyException.class)
	public void testInsertDuplicatedKey() {
		testInsertEntry(new ImmutableConfigEntry("key", "value", ConfigValueType.STRING));
		testInsertEntry(new ImmutableConfigEntry("key", "value", ConfigValueType.STRING));
	}

	@Test
	public void testGetStringEntry() {
		testGetEntry("testEntry", "value", ConfigValueType.STRING, "value");
		
	}

	@Test
	public void testGetBooleanEntry() {
		testGetEntry("testBooleanEntry", "1", ConfigValueType.BOOLEAN, Boolean.TRUE);
	}
	
	@Test
	public void testGetIntEntry() {
		testGetEntry("testIntEntry", "1", ConfigValueType.INT, 1);
	}
	
	@Test
	public void testGetLongEntry() {
		testGetEntry("testLongEntry", "1", ConfigValueType.LONG, 1L);
	}
	
	@Test
	public void testUpdateEntry() {
		dao.updateEntry(new ImmutableConfigEntry("testEntry", "value2", ConfigValueType.STRING));
		testGetEntry("testEntry", "value2", ConfigValueType.STRING, "value2");
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testUpdateEntryWrongType() {
		dao.updateEntry(new ImmutableConfigEntry("testEntry", "value2", ConfigValueType.LONG));		
		testGetEntry("testEntry", "value2", ConfigValueType.LONG, "value2");
	}
	
	@Test
	public void testListEntries() { 
		List<ConfigEntry> entries = dao.getAllEntries();
		AssertJUnit.assertTrue("Minimum 4 entries should be przesent in the database", entries.size() >= 4);
	}
	
	@Test(enabled = false)
	private void testGetEntry(String key, String value, ConfigValueType type, Object typedValue) {
		ConfigEntry entry = dao.getEntry(key);
		AssertJUnit.assertNotNull("entry is null", entry);
		AssertJUnit.assertEquals("Entry key", key, entry.getKey());
		AssertJUnit.assertEquals("Entry value", value, entry.getValue());
		AssertJUnit.assertEquals("Entry type", type, entry.getType());
		AssertJUnit.assertEquals("Entry typed value", typedValue, entry.getTypedValue());		
	}
	
	@Test(enabled = false)
	private void testInsertEntry(ConfigEntry entry) {
		dao.putEntry(entry);
	}
}

