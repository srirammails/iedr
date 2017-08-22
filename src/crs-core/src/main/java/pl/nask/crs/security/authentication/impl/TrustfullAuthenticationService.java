package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.security.Cause;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.security.authentication.InvalidPasswordException;
import pl.nask.crs.security.authentication.InvalidUsernameException;
import pl.nask.crs.security.authentication.NoLoginPermissionException;
import pl.nask.crs.security.authentication.PasswordExpiredException;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

/**
 * Authenticates every existing user. Allows to use password.
 *
 * @author Artur Gniadzik
 */
public class TrustfullAuthenticationService implements AuthenticationService {

	private UserDAO userDao;
	private LoginAttemptDAO loginAttemptDAO;

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

    public void setLoginAttemptDAO(LoginAttemptDAO loginAttemptDAO) {
        this.loginAttemptDAO = loginAttemptDAO;
    }

    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration, String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws InvalidUsernameException, InvalidPasswordException,
            PasswordExpiredException, IllegalArgumentException,
            AuthenticationException {
        if (username == null || "".equals(username))
            username = "IDL2-IEDR";
        if ("wrong".equals(username)) {
            LoginAttempt lastAttempt = loginAttemptDAO.getLastAttemptByNic(username);
            int failedAttemptsCount = lastAttempt == null ? 0 : lastAttempt.getFailureCount();
            loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance("wrong", "1.1.1.1", Cause.INVALID_NIC, ++failedAttemptsCount));
            throw new InvalidUsernameException(username);
        }

        username = username.trim().toUpperCase();
        User u = userDao.get(username);
        if (u == null) {
            throw new InvalidUsernameException(username);
        }

        if (!LoginPermissionChecker.checkLoginPermission(u, systemDiscriminator)) {
            throw new NoLoginPermissionException(u.getUsername());
        }

        loginAttemptDAO.createAttempt(LoginAttempt.newSuccessInstance("ok", "1.1.1.1"));
        return new AuthenticatedUserImpl(username);
    }

}
