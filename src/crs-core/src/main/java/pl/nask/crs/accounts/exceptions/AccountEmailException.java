package pl.nask.crs.accounts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class AccountEmailException extends Exception{
    
    public AccountEmailException() {
    }

    public AccountEmailException(String message) {
        super(message);
    }

    public AccountEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountEmailException(Throwable cause) {
        super(cause);
    }
}
