package pl.nask.crs.accounts.exceptions;

/**
 * @author Marianna Mysiorska
 */
public class AccountNotFoundException extends AccountException {

	public AccountNotFoundException(long accountId) {
		super(accountId);
	}

	public AccountNotFoundException(String message, long accountId) {
		super(message, accountId);
	}

	public AccountNotFoundException(String message, Throwable cause,
			long accountId) {
		super(message, cause, accountId);
	}

	public AccountNotFoundException(Throwable cause, long accountId) {
		super(cause, accountId);
	}
}
