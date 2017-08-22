package pl.nask.crs.commons.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleAssignedToDomainException extends Exception {

    private String nicHandleId;

    public NicHandleAssignedToDomainException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleAssignedToDomainException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleAssignedToDomainException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleAssignedToDomainException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}
