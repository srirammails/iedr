package pl.nask.crs.domains;

/**
 * represents the status of the domain. Since DSM this status is considered as deprecated and should be used only to represent state of the domains, which does not have a proper DSM state mapped.
 * 
 * @author Artur Gniadzik
 *
 */
public enum DomainStatus {
    Active("Active"), Deleted("Deleted"), Suspended("Suspended"), PRA("Post-Registration Audit");

    private String statusName;

    DomainStatus(String name) {
        this.statusName = name;
    }

    public String getStatusName() {
        return statusName;
    }
}