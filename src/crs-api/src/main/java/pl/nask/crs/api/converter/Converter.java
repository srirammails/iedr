package pl.nask.crs.api.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.commons.Period;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;

public final class Converter {
	private Converter() {}
	
	public static List<NameserverVO> toNameserverVOList(List<Nameserver> nameservers) {
		if (nameservers != null) {		
			ArrayList<NameserverVO> newNameservers = new ArrayList<NameserverVO>(nameservers.size());
			for (Nameserver ns: nameservers) {
				if (ns != null)
					newNameservers.add(new NameserverVO(ns));
				else
					newNameservers.add(null);
			}
			return newNameservers;
		} else {
			return null;
		}
	}

	public static List<String> convertContacts(List<Contact> contacts) {
		if (contacts != null) {
			return ContactUtils.contactsAsStrings(contacts);	
		} else {
			return null;
		}
		
	}

    public static Map<String, Period> convertDomainsWithPeriodToMap(List<DomainWithPeriodVO> domains) {
        HashMap<String, Period> ret = new HashMap<String, Period>();
        for (DomainWithPeriodVO domain : domains) {
            ret.put(domain.getDomainName(), Period.fromYears(domain.getPeriodInYears()));
        }
        return ret;
    }
    
    public static String nameserverName(List<Nameserver> nameservers, int i) {
    	if (nameservers.size() <= i) {
    		return null;
    	} else {
    		if (nameservers.get(i) != null)
    			return nameservers.get(i).getName();
    	}
    	
    	return null;
	}
    
    public static String nameserverIp(List<Nameserver> nameservers, int i) {
    	if (nameservers.size() <= i) {
    		return null;
    	} else {
    		if (nameservers.get(i) != null)
    			return nameservers.get(i).getIpAddressAsString();
    	}
    	
    	return null;
	}

    public static List<Nameserver> toNameserversList(List<NameserverVO> nameservers) {
   		ArrayList<Nameserver> res = new ArrayList<Nameserver>();
   		if (nameservers != null) {
   			for (int i=0; i < nameservers.size(); i++) {
   				NameserverVO ns = nameservers.get(i);
   				res.add(new Nameserver(ns.getName(), new IPAddress(ns.getIpAddress() == null ? "" : ns.getIpAddress()), i));
   			}
   		}
   		return res;
   	}
}
