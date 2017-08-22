package pl.nask.crs.domains.dsm;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GenericApplicationConfig;
import pl.nask.crs.commons.config.HardcodedGenericConfig;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.actions.SetGIBORetryTimeout;
import pl.nask.crs.domains.dsm.events.GIBOPaymentFailure;
import pl.nask.crs.domains.nameservers.Nameserver;

public class SetGIBORetryTimeoutTest {
	@Test
	public void setWithPeriodOfOne() {
		SetGIBORetryTimeout action = new SetGIBORetryTimeout();
		ApplicationConfig appConfig = new GenericApplicationConfig(new HardcodedGenericConfig());
		action.setGlobalConfig(appConfig );
		Domain domain = createDomain();
		action.invoke(null, domain, GIBOPaymentFailure.getInstance());
		
		AssertJUnit.assertTrue(DateUtils.isSameDay(DateUtils.addHours(new Date(), 24), domain.getGiboRetryTimeout()));
	}
	
	private Domain createDomain() {
		return new Domain("name", "holder", "holderClass", "holderCategory", new Contact("creator"), new Account(1),
				new Date(), new Date(), "remark", new Date(), false, new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Nameserver>(), null);
	}
}
