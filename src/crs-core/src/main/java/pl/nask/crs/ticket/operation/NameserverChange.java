package pl.nask.crs.ticket.operation;

import pl.nask.crs.commons.utils.Validator;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NameserverChange implements DomainFieldChange {

    private SimpleDomainFieldChange<String> name;

    private SimpleDomainFieldChange<String> ipAddress;

    public NameserverChange(SimpleDomainFieldChange<String> name, SimpleDomainFieldChange<String> ipAddress) {
        Validator.assertNotNull(name, "host name change");
        Validator.assertNotNull(ipAddress, "ip address change");
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public SimpleDomainFieldChange<String> getName() {
        return name;
    }

    public SimpleDomainFieldChange<String> getIpAddress() {
        return ipAddress;
    }

    public void populateFailureReason(NameserverChange change) {
        name.populateFailureReason(change.getName());
        ipAddress.populateFailureReason(change.getIpAddress());
    }

    public void populateValue(NameserverChange change) {
        nullEmptyValues(change);
        name.populateValue(change.getName());
        ipAddress.populateValue(change.getIpAddress());
    }

    private void nullEmptyValues(NameserverChange change) {
        boolean isEmptyIpAdressNewValue = Validator.isEmpty(change.getIpAddress().getNewValue());
        boolean isEmptyNameNewValue = Validator.isEmpty(change.getName().getNewValue());
        if (isEmptyIpAdressNewValue) {
            change.getIpAddress().setNewValue(null);
        }
        if (isEmptyNameNewValue) {
            change.getName().setNewValue(null);
        }
    }

    public boolean isFailed() {
        return name.isFailed() || ipAddress.isFailed();
    }

    public boolean isModification() {
        return name.isModification() || ipAddress.isModification();
    }
}
