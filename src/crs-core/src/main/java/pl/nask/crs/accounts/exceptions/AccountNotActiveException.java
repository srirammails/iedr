package pl.nask.crs.accounts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class AccountNotActiveException extends AccountException {

	public AccountNotActiveException(long accountId) {
		super(accountId);
	}

	public AccountNotActiveException(String message, long accountId) {
		super(message, accountId);
	}

	public AccountNotActiveException(String message, Throwable cause, long accountId) {
		super(message, cause, accountId);
	}

	public AccountNotActiveException(Throwable cause, long accountId) {
		super(cause, accountId);
	}

    
}
