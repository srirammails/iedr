package pl.nask.crs.api.vo;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.domains.Domain;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedDomainInfoVO {
	private DomainVO domain;
	private boolean tickets;
	private boolean documents;
	private Collection<String> relatedDomainNames;    
    private Collection<String> pendingDomainNames;
	
    public ExtendedDomainInfoVO() {
	}
	
	public ExtendedDomainInfoVO(ExtendedDomainInfo domainInfo) {
		setDomain(domainInfo.getDomain());
		this.tickets = domainInfo.isTickets();
		this.documents = domainInfo.isDocuments();
		this.relatedDomainNames = domainInfo.getRelatedDomainNames();
		this.pendingDomainNames = domainInfo.getPendingDomainNames();
	}

	public DomainVO getDomain() {
		return domain;
	}

	void setDomain(Domain domain) {
		this.domain = new DomainVO(domain);
	}

	public boolean isTickets() {
		return tickets;
	}

		public boolean isDocuments() {
		return documents;
	}

	public Collection<String> getRelatedDomainNames() {
		return relatedDomainNames;
	}

	public Collection<String> getPendingDomainNames() {
		return pendingDomainNames;
	}
}
