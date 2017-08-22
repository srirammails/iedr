package pl.nask.crs.domains.dsm;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmActionsRegistry;

public class DsmActionRegistryTest {

	@Test
	public void testParamlessAction() {
		DsmActionsRegistry reg = new DsmActionsRegistry(null);
		reg.initActionFactories();
		DsmAction action = reg.actionFor("ClearDeletionDate", null);
		AssertJUnit.assertNotNull(action);
		Assert.assertNull(action.getActionParam());
	}
	
	@Test
	public void testActionWithParam() {
		DsmActionsRegistry reg = new DsmActionsRegistry(null);
		reg.initActionFactories();
		DsmAction action = reg.actionFor("SetRenewalDate", "0");
		AssertJUnit.assertNotNull(action);
		Assert.assertNotNull(action.getActionParam());
		AssertJUnit.assertEquals("0", action.getActionParam());
	}
}
