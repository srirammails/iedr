package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class PasswordTooEasyException extends NicHandleException{
	
	public PasswordTooEasyException() {
		super("Password must be 8-16 characters long, contain letters in upper and lower case, digit and a non-alphanumeric character (like :_-.#@|!$%^&*)");
	}
}
