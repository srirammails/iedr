package pl.nask.crs.contacts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class ContactNotFoundException extends Exception{

    private String nicHandleId;

    public ContactNotFoundException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public ContactNotFoundException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public ContactNotFoundException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public ContactNotFoundException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }
}
