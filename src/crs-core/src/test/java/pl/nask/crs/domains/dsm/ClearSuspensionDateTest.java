package pl.nask.crs.domains.dsm;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.ClearSuspensionDate;
import pl.nask.crs.domains.dsm.events.RemoveFromVoluntaryNRP;

public class ClearSuspensionDateTest {

	
	@Test
	public void clearSuspensionDateAction() {
		ClearSuspensionDate action = new ClearSuspensionDate();
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(new Date());
		domain.setDeletionDate(new Date());
		action.invoke(null , domain, null);
		Assert.assertNull(domain.getSuspensionDate());
		Assert.assertNotNull(domain.getDeletionDate());
	}
	
	@Test
	public void clearSuspensionDateActionForRemoveFromVNRPEvent() {
		ClearSuspensionDate action = new ClearSuspensionDate();
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(new Date());
		domain.setDeletionDate(new Date());
		action.invoke(null , domain, new RemoveFromVoluntaryNRP());
		Assert.assertNull(domain.getSuspensionDate());
		Assert.assertNull(domain.getDeletionDate());
	}
}
