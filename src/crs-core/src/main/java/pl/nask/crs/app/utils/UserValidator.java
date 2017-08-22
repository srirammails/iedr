package pl.nask.crs.app.utils;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.app.NoAuthenticatedUserException;

/**
 * 
 * 
 * @author Kasia Fulara, Artur Gniadzik
 */
public class UserValidator {

	private UserValidator() {}

	
    public static void validateLoggedIn(AuthenticatedUser user) throws NoAuthenticatedUserException {
        if (user == null) {
            throw new NoAuthenticatedUserException();
        }
    }
}
