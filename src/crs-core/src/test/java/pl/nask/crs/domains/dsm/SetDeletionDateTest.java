package pl.nask.crs.domains.dsm;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GenericApplicationConfig;
import pl.nask.crs.commons.config.HardcodedGenericConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.ClearDeletionDate;
import pl.nask.crs.domains.dsm.actions.SetDeletionDate;

public class SetDeletionDateTest {
	ApplicationConfig cfg = new GenericApplicationConfig(new HardcodedGenericConfig());
	
	@Test
	public void setDeletionDate() {
		SetDeletionDate action = new SetDeletionDate();
		action.setGlobalConfig(cfg);
		Domain domain = Helper.createDomain();
		Date currDt = new Date();
		domain.setSuspensionDate(currDt);
		
		action.invoke(null, domain, null);
		
		AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), DateUtils.addDays(currDt, cfg.getNRPConfig().getNrpSuspendedPeriod())));
	}
	
	@Test(expectedExceptions=IllegalStateException.class)
	public void setDeletionDateFailed() {
		SetDeletionDate action = new SetDeletionDate();
		action.setGlobalConfig(cfg);
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(null);
		
		action.invoke(null, domain, null);
	}
	
	@Test
	public void clearDeletionDate() {
		ClearDeletionDate action = new ClearDeletionDate();
		Domain domain = Helper.createDomain();
		domain.setDeletionDate(new Date());
		action.invoke(null, domain, null);
		
		Assert.assertNull(domain.getDeletionDate());
	}
}

