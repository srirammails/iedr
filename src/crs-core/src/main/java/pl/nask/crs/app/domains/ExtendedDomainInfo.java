package pl.nask.crs.app.domains;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import pl.nask.crs.domains.Domain;

/**
 * 
 * @author Artur Gniadzik
 *
 */
public class ExtendedDomainInfo {

	private final Domain domain;
	private boolean tickets;
	private boolean documents;
	private Collection<String> relatedDomainNames;    
    private Collection<String> pendingDomainNames;

	public ExtendedDomainInfo(Domain domain) {
		this.domain = domain;
	}

	public void setDocuments(boolean documents) {
		this.documents = documents;
	}

	public void setTickets(boolean tickets) {
		this.tickets = tickets;
	}

	@SuppressWarnings("unchecked")
	public void setRelatedDomainNames(Collection<String> relatedDomainNames) {
		this.relatedDomainNames = relatedDomainNames == null ? Collections.EMPTY_SET : new TreeSet<String>(relatedDomainNames);
	}

	@SuppressWarnings("unchecked")
	public void setPendingDomainNames(Collection<String> pendingDomainNames) {
		this.pendingDomainNames = pendingDomainNames == null ? Collections.EMPTY_SET : new TreeSet<String>(pendingDomainNames);		
	}

	public Domain getDomain() {
		return domain;
	}

	// FIXME: document me!
	public boolean isDocuments() {
		return documents;
	}

	// FIXME: document me!
	public boolean isTickets() {
		return tickets;
	}

	// FIXME: document me! what are "pending domain names" ?
	public Collection<String> getPendingDomainNames() {
		return pendingDomainNames;
	}

	// FIXME: document me! what are "related domain names" ?
	public Collection<String> getRelatedDomainNames() {
		return relatedDomainNames;
	}
}
