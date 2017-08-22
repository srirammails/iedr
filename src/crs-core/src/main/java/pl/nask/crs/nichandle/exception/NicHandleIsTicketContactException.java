package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIsTicketContactException extends NicHandleException {

    private String nicHandleId;

    public NicHandleIsTicketContactException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsTicketContactException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsTicketContactException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsTicketContactException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}

