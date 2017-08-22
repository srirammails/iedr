package pl.nask.crs.contacts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class ContactCannotChangeException extends Exception{

    private String from;
    private String to;

    public ContactCannotChangeException(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public ContactCannotChangeException(String message, String from, String to) {
        super(message);
        this.from = from;
        this.to = to;
    }

    public ContactCannotChangeException(String message, Throwable cause, String from, String to) {
        super(message, cause);
        this.from = from;
        this.to = to;
    }

    public ContactCannotChangeException(Throwable cause, String from, String to) {
        super(cause);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
