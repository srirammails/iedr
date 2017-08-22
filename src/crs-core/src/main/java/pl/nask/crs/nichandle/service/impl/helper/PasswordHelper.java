package pl.nask.crs.nichandle.service.impl.helper;

import java.util.Random;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;

/**
 * @author Marianna Mysiorska
 */
public final class PasswordHelper {

	private PasswordHelper() {
		
	}
	
    private static final String LETTERS = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
    
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 16; 

    public static void validatePassword(String p1, String p2) throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException {
        if (Validator.isEmpty(p1) || Validator.isEmpty(p2))
            throw new EmptyPasswordException();
        if (!p1.equals(p2))
            throw new PasswordsDontMatchException();
        if (p1.length() < MIN_PASSWORD_LENGTH)
            throw new PasswordTooEasyException();
        if (p1.length() > MAX_PASSWORD_LENGTH)
            throw new PasswordTooEasyException();
        if (!p1.matches(".*[0-9]+.*"))
            throw new PasswordTooEasyException();
        if (!p1.matches(".*[A-Z]+.*"))
            throw new PasswordTooEasyException();
        if (!p1.matches(".*[a-z]+.*"))
            throw new PasswordTooEasyException();
        if (!p1.matches(".*[a-z]+.*"))
            throw new PasswordTooEasyException();
        if (!p1.matches(".*[^QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm].*"))
        	throw new PasswordTooEasyException();
    }

    /**
     * generates random password of defined length composed with the characters from the LETTERS using java.util.Random
     *
     * @return generated random password
     */
    public static String generateNewPassword(int length){
        Random random = new Random();
        int arrayLength = LETTERS.length();
        StringBuilder password = new StringBuilder();
        int at;
             for(int i = 1; i <= length; i++){
                 at=random.nextInt(arrayLength);
                 password.append(LETTERS.charAt(at));
             }
             return password.toString();
    }

}
