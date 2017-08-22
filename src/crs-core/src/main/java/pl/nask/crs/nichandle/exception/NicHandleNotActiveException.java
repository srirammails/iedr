package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleNotActiveException extends NicHandleException {

    private String nicHandleId;

    public NicHandleNotActiveException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleNotActiveException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleNotActiveException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleNotActiveException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}
