package pl.nask.crs.nichandle.service.impl.helper;

import org.testng.annotations.Test;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;

/**
 * Tests for PasswordHelper
 * 
 * @author Artur Gniadzik
 *
 */
public class PasswordHelperTest {

	
	@Test
	public void validatePasswordComplex() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("qwe123qweE123.", "qwe123qweE123.");
	}
	
	@Test
	public void validatePasswordSimple() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("123qweQW.", "123qweQW.");
	}
	
	@Test(expectedExceptions=PasswordTooEasyException.class)
	public void validatePasswordTooEasyNoSpecialChar() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("123qweQW", "123qweQW");
	}
	
	@Test(expectedExceptions=PasswordTooEasyException.class)
	public void validatePasswordTooShort() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("qwe", "qwe");
	}
		
	@Test(expectedExceptions=PasswordsDontMatchException.class)
	public void validatePasswordDontMatch() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("qwe1", "qwe");
	}
	
	@Test(expectedExceptions=PasswordTooEasyException.class)
	public void validatePasswordTooEasy1() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("1qweqwee", "1qweqwee");
	}
	
	@Test(expectedExceptions=PasswordTooEasyException.class)
	public void validatePasswordTooEasy2() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("Qqweqwee", "Qqweqwee");
	}
	
	@Test(expectedExceptions=PasswordTooEasyException.class)
	public void validatePasswordTooEasy3() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("QWEQWEQ1", "QWEQWEQ1");
	}
	
	@Test
	public void validatePasswordWithDot() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("11111Qa1.", "11111Qa1.");
	}
	
	@Test
	public void validatePasswordWithUnderscore() throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
		PasswordHelper.validatePassword("11111Qa1_", "11111Qa1_");
	}
}
