package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleDeletedException extends NicHandleException{

    private String nicHandleId;

    public NicHandleDeletedException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleDeletedException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleDeletedException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleDeletedException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}
