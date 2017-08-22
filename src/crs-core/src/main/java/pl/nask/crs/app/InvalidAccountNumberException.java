package pl.nask.crs.app;

import pl.nask.crs.user.permissions.PermissionDeniedException;

/**
 * Thrown if the lack of permission is caused by the illegal account number.
 * 
 * @author Artur Gniadzik
 *
 */
public class InvalidAccountNumberException extends PermissionDeniedException {
	
}
