package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

import java.util.List;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NameserverWrapper {

    private String name = "";
    private String ipv4 = "";
    private int nameFr = 0;
    private int ipv4Fr = 0;
    private boolean nameModified = true;
    private boolean ipModified = true;

    public NameserverWrapper() {
    }

    public NameserverWrapper(NameserverChange ns) {
        final SimpleDomainFieldChange<String> nameChange = ns.getName();
        if (nameChange != null) {
            name = nameChange.getNewValue();
            final FailureReason nameFailureReason = nameChange.getFailureReason();
            nameFr = nameFailureReason == null ? 0 : nameFailureReason.getId();
            nameModified = nameChange.isModification();
        }
        final SimpleDomainFieldChange<String> ipv4Change = ns.getIpAddress();
        if (ipv4Change != null) {
            ipv4 = ipv4Change.getNewValue();
            final FailureReason ipv4FailureReason = ipv4Change.getFailureReason();
            ipv4Fr = ipv4FailureReason == null ? 0 : ipv4FailureReason.getId();
            ipModified = ipv4Change.isModification();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public int getNameFr() {
        return nameFr;
    }

    public void setNameFr(int nameFr) {
        this.nameFr = nameFr;
    }

    public int getIpv4Fr() {
        return ipv4Fr;
    }

    public void setIpv4Fr(int ipv4Fr) {
        this.ipv4Fr = ipv4Fr;
    }

    public boolean isNameModification() {
        return nameModified;
    }

    public boolean isIpModification() {
        return ipModified;
    }
}
