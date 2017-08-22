package pl.nask.crs.contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactUtils {
	public static List<String> contactsAsStrings(List<Contact> contacts) {
		List<String> res = new ArrayList<String>();
		if (contacts != null) {
			for (Contact c: contacts) {
				if (c == null) {
					res.add(null);
				} else {
					res.add(c.getNicHandle());
				}
			}
		}
		return res;
	}

	public static List<Contact> stringsAsContacts(List<String> contacts) {
		List<Contact> ret = new ArrayList<Contact>();
		if (contacts != null) {
			for (String c: contacts) {
				ret.add(new Contact(c));
			}
		}
		
		return ret;
	}
}
