package pl.nask.crs.accounts.exceptions;

public abstract class AccountException extends Exception {
	private long accountId;

    public AccountException(long accountId) {
    	super(msg(accountId));
        this.accountId = accountId;        
    }

    protected static String msg(long accountId) {
    	return "No such account (id=" + accountId + ")";
	}

	public AccountException(String message, long accountId) {
        super(message);
        this.accountId = accountId;
    }

    public AccountException(String message, Throwable cause, long accountId) {
        super(message, cause);
        this.accountId = accountId;
    }

    public AccountException(Throwable cause, long accountId) {
        super(msg(accountId), cause);
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
