package pl.nask.crs.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kasia Fulara
 */
public final class EmailValidator {
	private EmailValidator() {}
	
    public static void validateEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException("Empty email");
        email = email.toLowerCase();
        email = email.trim();
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        if (!m.matches())
            throw new IllegalArgumentException("Wrong email: " + email);
    }
}
