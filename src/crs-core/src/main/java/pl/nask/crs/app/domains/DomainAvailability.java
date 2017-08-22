package pl.nask.crs.app.domains;

public class DomainAvailability {

	private final String domainName;
	private final boolean domainCreated;
	private final boolean regTicketCreated;
    private final String userName;
    private final boolean managedByUser;
    private final boolean internalError;


	public DomainAvailability(String domainName, boolean domainCreated, boolean regTicketCreated, String userName, boolean managedByUser, boolean internalError) {
		this.domainName = domainName;
		this.domainCreated = domainCreated;
		this.regTicketCreated = regTicketCreated;
        this.userName = userName;
        this.managedByUser = managedByUser;
        this.internalError = internalError;
	}

	public String getDomainName() {
		return domainName;
	}

	public boolean isDomainCreated() {
		return domainCreated;
	}

	public boolean isRegTicketCreated() {
		return regTicketCreated;
	}
 
	public boolean isAvailable() {
		return !(domainCreated || regTicketCreated || internalError);
	}

    public String getUserName() {
        return userName;
    }

    public boolean isManagedByUser() {
        return managedByUser;
    }

    public boolean isInternalError() {
        return internalError;
    }
}
