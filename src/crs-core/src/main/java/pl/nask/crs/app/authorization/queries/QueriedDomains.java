package pl.nask.crs.app.authorization.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class QueriedDomains {

	private List<Domain> domains;
	private List<String> domainNames;
	
	private QueriedDomains() {
		
	}
	
	public QueriedDomains(String... domainNames) {
		this.domainNames = Arrays.asList(domainNames);
		this.domains = null;
	}
	
	public static QueriedDomains instanceForDomainList(List<Domain> domains) {
		QueriedDomains instance = new QueriedDomains();
		instance.domains = domains;
		instance.domainNames = domainNames(domains);
		return instance;
	}
	
	public static QueriedDomains instanceForDomainNames(List<String> domains) {
		QueriedDomains instance = new QueriedDomains();		
		instance.domainNames = domains;
		return instance;
	}
	
	public QueriedDomains(Domain... domain) {
		this.domains = Arrays.asList(domain);
		this.domainNames = domainNames(domains);
	}

	public List<Domain> getDomains() {
		return domains;
	}

	private static List<String> domainNames(List<Domain> domains) {
		List<String> l = new ArrayList<String>(domains.size());
		for (Domain d: domains) {
			l.add(d.getName());
		}
		return l;
	}

	public List<String> getDomainNames() {
		return domainNames;
	}

	public static QueriedDomains instanceFor(Object obj) {

        if (obj instanceof String) {
        	return new QueriedDomains((String) obj);
        } else if (obj instanceof String[]) {
        	return new QueriedDomains((String[]) obj);
        } else if (obj instanceof Domain) {
        	return new QueriedDomains((Domain) obj);
        } else if (obj instanceof Domain[]) {
        	return new QueriedDomains((Domain[]) obj);
        } else if (obj instanceof List) {
        	if (((List)obj).size() != 0 && ((List) obj).get(0) instanceof Domain) {
        		return instanceForDomainList((List<Domain>) obj);
        	} else {
        		return instanceForDomainNames((List<String>) obj);
        	}
        } else {
        	throw new IllegalArgumentException("");
        }
	}

}
