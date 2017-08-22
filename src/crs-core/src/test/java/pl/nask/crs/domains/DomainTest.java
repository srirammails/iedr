package pl.nask.crs.domains;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainTest {
	@Test
	public void testGetAdminContactConvinientMethods() {
		// having domain with empty admin contact list
		String admin1 = "admin1";
		String admin2 = "admin2";
		
		Domain d = newDomain(null, null, null, null);

		AssertJUnit.assertNull("getFirstAdminContact", d.getFirstAdminContact());
		AssertJUnit.assertNull("getFirstAdminContactNic", d.getFirstAdminContactNic());		
		AssertJUnit.assertNull("getSecondAdminContact", d.getSecondAdminContact());
		AssertJUnit.assertNull("getSecondAdminContactNic", d.getSecondAdminContactNic());
		
		d = newDomain(null, null, admin1, null);
		
		AssertJUnit.assertEquals("getFirstAdminContact", contact(admin1), d.getFirstAdminContact());
		AssertJUnit.assertEquals("getFirstAdminContactNic", admin1, d.getFirstAdminContactNic());		
		AssertJUnit.assertNull("getSecondAdminContact", d.getSecondAdminContact());
		AssertJUnit.assertNull("getSecondAdminContactNic", d.getSecondAdminContactNic());
		
		d = newDomain(null, null, "admin1", "admin2");
		
		AssertJUnit.assertEquals("getFirstAdminContact", contact(admin1), d.getFirstAdminContact());
		AssertJUnit.assertEquals("getFirstAdminContactNic", admin1, d.getFirstAdminContactNic());		
		AssertJUnit.assertEquals("getSecondAdminContact", contact(admin2), d.getSecondAdminContact());
		AssertJUnit.assertEquals("getSecondAdminContactNic", admin2, d.getSecondAdminContactNic());
	}
	
	@Test
	public void testGetTechContactConvinientMethods() {
		// having domain with empty tech contact list
		Domain d = newDomain(null, null, null, null);
		
		AssertJUnit.assertNull("getTechContact", d.getTechContact());
		AssertJUnit.assertNull("getTechContactNic", d.getTechContactNic());
		
		d = newDomain(null, "techC", null, null);
		
		AssertJUnit.assertEquals("getTechContact", contact("techC"), d.getTechContact());
		AssertJUnit.assertEquals("getTechContactNic", "techC", d.getTechContactNic());
	}
	
	@Test
	public void testGetBillingContactConvinientMethods() {
		// having domain with empty tech contact list
		Domain d = newDomain(null, null, null, null);
		
		AssertJUnit.assertNull("getBillingContact", d.getBillingContact());
		AssertJUnit.assertNull("getBillingContactNic", d.getBillingContactNic());
		
		d = newDomain("billC", null, null, null);
		
		AssertJUnit.assertEquals("getBillingContact", contact("billC"), d.getBillingContact());
		AssertJUnit.assertEquals("getBillingContactNic", "billC", d.getBillingContactNic());
	}
	

	private Domain newDomain(String billingC, String techC, String admin1C, String admin2C) {
		Date anyDate = new Date();
		return new Domain(
				"name.ie", 
				"holder", 
				"holderClass", 
				"holderCategory", 
				contact("creator"), 
				account(), 
				anyDate, 
				anyDate, 
				"remark", 
				anyDate, 
				false, 
				contacts(techC), 
				contacts(billingC), 
				contacts(admin1C, admin2C), 
				nameservers());
	}

	private List<Contact> contacts(String... contacts) {
		List<Contact> ret = new ArrayList<Contact>();
		for (String c: contacts) {
			if (c != null) {
				ret.add(contact(c));
			}
		}
		return ret;
	}

	private List<Nameserver> nameservers() {
		return new ArrayList<Nameserver>();
	}

	private Account account() {	
		return new Account(1);
	}

	private Contact contact(String nh) {
		return new Contact(nh);
	}
}
