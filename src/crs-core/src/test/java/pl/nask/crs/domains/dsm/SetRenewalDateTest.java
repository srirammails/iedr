package pl.nask.crs.domains.dsm;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import static pl.nask.crs.domains.dsm.Helper.createDomain;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.actions.SetRenewalDate;
import pl.nask.crs.domains.dsm.events.CreateGIBODomain;

public class SetRenewalDateTest {

	@Test
	public void setWithPeriodOfOne() {
		DsmAction action = new SetRenewalDate().getAction("1");		
		Domain domain = createDomain();
		Date newDate = DateUtils.addYears(new Date(), 1);
		action.invoke(null, domain, new CreateGIBODomain(newDate));
		
		AssertJUnit.assertTrue(DateUtils.isSameDay(newDate, domain.getRenewDate()));
	}

	@Test
	public void setWithPeriodOfTwo() {
		DsmAction action = new SetRenewalDate().getAction("2");
		Domain domain = createDomain();
		Date newDate = DateUtils.addYears(new Date(), 2);
		action.invoke(null, domain, new CreateGIBODomain(newDate));
		AssertJUnit.assertTrue(DateUtils.isSameDay(newDate, domain.getRenewDate()));
	}
}
