package pl.nask.crs.config;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class RevisionInfoTest {
	@Test
	public void testRevisionNumberInitialized() {
		String revision = VersionInfo.getRevision();
		Assert.assertNotNull(revision);
		AssertJUnit.assertFalse("revision equals to ${prefix.revision}", "${prefix.revision}".equals(revision));
	}
}
