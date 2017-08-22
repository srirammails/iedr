package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.domains.DomainAvailability;

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainAvailabilityVO {

	private boolean available;
	private boolean domainCreated;
	private boolean regTicketCreated;
    private boolean managedByUser;

	public DomainAvailabilityVO() {
	}
	
	public DomainAvailabilityVO(DomainAvailability checkAvailability) {
		this.available = checkAvailability.isAvailable();
		this.domainCreated = checkAvailability.isDomainCreated();
		this.regTicketCreated = checkAvailability.isRegTicketCreated();
        this.managedByUser = checkAvailability.isManagedByUser();
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isDomainCreated() {
		return domainCreated;
	}

	public void setDomainCreated(boolean domainCreated) {
		this.domainCreated = domainCreated;
	}

	public boolean isRegTicketCreated() {
		return regTicketCreated;
	}

	public void setRegTicketCreated(boolean regTicketCreated) {
		this.regTicketCreated = regTicketCreated;
	}

    public boolean isManagedByUser() {
        return managedByUser;
    }

    public void setManagedByUser(boolean managedByUser) {
        this.managedByUser = managedByUser;
    }
}
