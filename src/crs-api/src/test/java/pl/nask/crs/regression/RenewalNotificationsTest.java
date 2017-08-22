package pl.nask.crs.regression;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;

public class RenewalNotificationsTest extends AbstractEmailsTest {
	@Autowired
	DomainAppService domainAppService;
	
	@Autowired
	ApplicationConfig config;
	
	@Autowired
	DomainDAO domainDao;
	
	private Set<ParameterNameEnum> populatedParams = new HashSet<ParameterNameEnum>(Arrays.asList(
			ParameterNameEnum.BILL_C_EMAIL, 
			ParameterNameEnum.BILL_C_NIC, 
			ParameterNameEnum.BILL_C_NAME,
			ParameterNameEnum.DOMAIN,
			ParameterNameEnum.RENEWAL_DATE
			));
	
	private Map<ParameterNameEnum, String> populatedValues;

	
	@Test
	public void paramsShouldBePopulatedInTemplate26() throws Exception {
		// make sure all notifications were send (just to test a specific domain)
		// prevent from sending notifications for directDomain.ie (set the renew date to a far future)
		Domain d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		domainDao.update(d, 17);		
		domainAppService.runNotificationProcess(null);
		
		// now, set the renew date to today and test the expectations
		d = domainDao.get("directDomain.ie");
		int period = config.getRenewalNotificationPeriods().get(0);
		d.setRenewDate(DateUtils.getCurrDate(period));
		// make sure, that the admin contact differs from the billing contact
		d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
		domainDao.update(d, 17);
		preparePopulatedValues(DateUtils.getCurrDate(period));
		createExpectations(26, populatedValues);
		domainAppService.runNotificationProcess(null);
	}
	
	@Test
	public void renewalNotificationShouldBeSentForOneMatchingPeriodOnly() throws Exception {
		// make sure all notifications were send (just to test a specific domain)
		// prevent from sending notifications for directDomain.ie (set the renew date to a far future)
		Domain d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		domainDao.update(d, 17);		
		domainAppService.runNotificationProcess(null);
		
		// now, set the renew date to today and test the expectations
		d = domainDao.get("directDomain.ie");
		int period = 5;
		d.setRenewDate(DateUtils.getCurrDate(period));
		// make sure, that the admin contact differs from the billing contact
		d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
		domainDao.update(d, 17);
		preparePopulatedValues(DateUtils.getCurrDate(period));
		createExpectations(26, populatedValues, 1, 1);
		domainAppService.runNotificationProcess(null);
	}
	
	@Test
	public void renewalNotificationShouldNotBeSentTwiceIfBillAndAdminAreTheSame() throws Exception {
		// make sure all notifications were send (just to test a specific domain)
		// prevent from sending notifications for directDomain.ie (set the renew date to a far future)
		Domain d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		domainDao.update(d, 17);		
		domainAppService.runNotificationProcess(null);
		
		// now, set the renew date to today and test the expectations
		d = domainDao.get("directDomain.ie");
		int period = 5;
		d.setRenewDate(DateUtils.getCurrDate(period));
		// make sure, that the admin contact is the same as the billing contact
		d.setAdminContacts(d.getBillingContacts());
		domainDao.update(d, 17);
		preparePopulatedValues(DateUtils.getCurrDate(period));
		createExpectations(26, populatedValues, 1, 1);
		domainAppService.runNotificationProcess(null);
	}
	
	@Test
	public void paramsShouldBePopulatedInTemplate74() throws Exception {
		// prevent from sending notifications for directDomain.ie and directDomain3.ie (set the renew date to a far future)
		Domain d = domainDao.get("directDomain3.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		domainDao.update(d, 17);
		
		d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		// make sure, that the admin contact differs from the billing contact
		d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
		domainDao.update(d, 17);
		
		// make sure all notifications were send (just to test a specific domain)
		// the process may have to be run multiple times
		domainAppService.runNotificationProcess(null);
		preparePopulatedValues(DateUtils.getCurrDate(0));
		createExpectations(74, populatedValues);
		// make sure that the notification about domain expiry will be sent
		d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(0));
		domainDao.update(d, 17);		
		domainAppService.runNotificationProcess(null);
	}
	
	// refs https://drotest4.nask.net.pl:3000/issues/14264
	// Bug in RenewalNotification job - sending to domains with a past renew date
	@Test
	public void renewalNotificationShouldNotBeSentIfDomainRenewalDateIsInThePast() throws Exception {
		// prevent from sending notifications for directDomain.ie and directDomain3.ie (set the renew date to a far future)
		Domain d = domainDao.get("directDomain3.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		domainDao.update(d, 17);

		d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(400));
		// make sure, that the admin contact differs from the billing contact
		d.setAdminContacts(Arrays.asList(new Contact("AAG061-IEDR")));
		domainDao.update(d, 17);

		// make sure all notifications were send (just to test a specific domain)
		// the process may have to be run multiple times
		domainAppService.runNotificationProcess(null);

		preparePopulatedValues(DateUtils.getCurrDate(-20));
		createExpectations(26, populatedValues, 0, 0);
		// make sure that the notification about domain expiry will be sent
		d = domainDao.get("directDomain.ie");
		d.setRenewDate(DateUtils.getCurrDate(-20)); // set the renewal date to the past
		domainDao.update(d, 17);		
		domainAppService.runNotificationProcess(null);
	}

	private void preparePopulatedValues(Date expectedRenDt) {
		populatedValues = new HashMap<ParameterNameEnum, String>();
		{
		populatedValues.put(ParameterNameEnum.BILL_C_EMAIL, "NHEmail000019@server.kom");
		populatedValues.put(ParameterNameEnum.BILL_C_NIC, "AAU809-IEDR");
		populatedValues.put(ParameterNameEnum.BILL_C_NAME, "Paul Janssens");
		populatedValues.put(ParameterNameEnum.DOMAIN, "directDomain.ie");
		populatedValues.put(ParameterNameEnum.RENEWAL_DATE, new SimpleDateFormat("dd-MM-yyyy").format(expectedRenDt));
		populatedValues.put(ParameterNameEnum.DAYS_TO_RENEWAL, "" + DateUtils.diffInDays(expectedRenDt, new Date()));
		}
	}
}
