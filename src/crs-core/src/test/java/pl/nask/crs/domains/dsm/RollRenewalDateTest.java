package pl.nask.crs.domains.dsm;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.RollRenewalDate;
import pl.nask.crs.domains.dsm.events.PaymentSettledEvent;

public class RollRenewalDateTest {
	
	@Test
	public void testWithPeriod1() {
		Date current = new Date();
		int period = 1;
		
		PaymentSettledEvent event = new PaymentSettledEvent(period);
		
		RollRenewalDate action = new RollRenewalDate();
		Domain domain = Helper.createDomain();
		domain.setRenewDate(current);
		Date anyDate = new Date();
		domain.setDeletionDate(anyDate );
		domain.setSuspensionDate(anyDate);
		
		action.invoke(null, domain, event);
		
		Assert.assertNotNull(domain.getRenewDate());
		AssertJUnit.assertEquals(DateUtils.addYears(current,  period), domain.getRenewDate());
		
		AssertJUnit.assertNull("suspension date should be cleared by the action", domain.getSuspensionDate());
		AssertJUnit.assertNull("deletion date should be cleared by the action", domain.getDeletionDate());
	}
	
	
}
