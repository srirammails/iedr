package pl.nask.crs.domains.dsm;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GenericApplicationConfig;
import pl.nask.crs.commons.config.HardcodedGenericConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateCurrent;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateRenewal;

public class SetSuspensionDateTest {
	ApplicationConfig cfg = new GenericApplicationConfig(new HardcodedGenericConfig());
	
	@Test
	public void setSuspensionDateCurrentAction() {
		SetSuspensionDateCurrent action = new SetSuspensionDateCurrent();
		action.setGlobalConfig(cfg );
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(null);
		domain.setDeletionDate(null);
		action.invoke(null , domain, null);
		Date now = new Date();
		AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getSuspensionDate(), DateUtils.addDays(now , cfg.getNRPConfig().getNrpMailedPeriod() + 1)));
		AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), DateUtils.addDays(now, cfg.getNRPConfig().getNrpMailedPeriod() + 1 + cfg.getNRPConfig().getNrpSuspendedPeriod())));
	}
	
	@Test
	public void setSuspensionDateRenewalAction() {
		SetSuspensionDateRenewal action = new SetSuspensionDateRenewal();
		action.setGlobalConfig(cfg );
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(null);
		domain.setDeletionDate(null);
		Date renewalDate = new Date();
		domain.setRenewDate(renewalDate);
		action.invoke(null , domain, null);
		Date expectedSuspensionDate = DateUtils.addDays(renewalDate, cfg.getNRPConfig().getNrpMailedPeriod() + 1);
		AssertJUnit.assertTrue("Expected suspensionDate to be: " + expectedSuspensionDate + " but got " + domain.getSuspensionDate(), DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate ));
		AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), DateUtils.addDays(renewalDate, cfg.getNRPConfig().getNrpMailedPeriod() + 1 + cfg.getNRPConfig().getNrpSuspendedPeriod())));
	}
	
	@Test
	public void setSuspensionDateRenewalWithFixedDate() {
		SetSuspensionDateRenewal action = new SetSuspensionDateRenewal();
		action.setGlobalConfig(cfg );
		Domain domain = Helper.createDomain();
		domain.setSuspensionDate(null);
		domain.setDeletionDate(null);
		Date renewalDate = new Date("2013/09/28");
		domain.setRenewDate(renewalDate);
		action.invoke(null , domain, null);
		Date expectedSuspensionDate = new Date("2013/10/13"); // ren_dt + nrp_mailed (14) + 1
		Date expectedDeletionDate = new Date("2013/10/27");  // susp_dt + nrp_deleted
		AssertJUnit.assertTrue("Expected suspensionDate to be: " + expectedSuspensionDate + " but got " + domain.getSuspensionDate(), DateUtils.isSameDay(domain.getSuspensionDate(), expectedSuspensionDate ));
		AssertJUnit.assertTrue(DateUtils.isSameDay(domain.getDeletionDate(), expectedDeletionDate));
	}
}
