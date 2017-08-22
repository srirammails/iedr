package pl.nask.crs.contacts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class ContactNotActiveException extends Exception{

    private String nicHandleId;

    public ContactNotActiveException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public ContactNotActiveException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public ContactNotActiveException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public ContactNotActiveException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}
