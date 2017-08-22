package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Date;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.nameservers.Nameserver;

public class Helper {
	public static Domain createDomain() {
		return new Domain("name", "holder", "holderClass", "holderCategory", new Contact("creator"), new Account(1),
				new Date(), new Date(), "remark", new Date(), false, new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Contact>(), new ArrayList<Nameserver>(), null);
	}
}
