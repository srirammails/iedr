package pl.nask.crs.app.utils;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.contacts.Contact;

/**
 * @author: Marianna Mysiorska
 */
public class ContactHelper {

	private ContactHelper() {}
	
    public static String makeContactInfo(Contact contact){
        StringBuilder result = new StringBuilder("");
        if (contact == null)
            return result.toString();
        if (contact.getName() != null)
            result.append(StringEscapeUtils.escapeHtml(contact.getName()));
        if (contact.getEmail() != null)
            result.append("<br/>").append(StringEscapeUtils.escapeHtml(contact.getEmail()));
        if (contact.getCompanyName() != null)
            result.append("<br/>").append(StringEscapeUtils.escapeHtml(contact.getCompanyName()));
        if (contact.getCountry() != null)
            result.append("<br/>").append(StringEscapeUtils.escapeHtml(contact.getCountry()));
        return result.toString();
    }

}
