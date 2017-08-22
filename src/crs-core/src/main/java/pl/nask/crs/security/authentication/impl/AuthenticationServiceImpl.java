package pl.nask.crs.security.authentication.impl;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GAConfig;
import pl.nask.crs.commons.config.LoginLockoutConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.Cause;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.authentication.googleauthenticator.AuthenticationCodesVerifier;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

/**
 * Class serves to authenticate a user in the system. Uses the list of
 * HashAlgorithm interface to check a password (check against every algorithm
 * from the list until the password matches) Uses the UserDAO interface to get
 * system information about a user.
 *
 * @author Marianna Mysiorska
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;
    private final List<HashAlgorithm> algorithms;
    protected final ApplicationConfig applicationConfig;
    private final LoginAttemptDAO loginAttemptDAO;

    public AuthenticationServiceImpl(UserDAO userDAO, List<HashAlgorithm> algorithms, ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        this.algorithms = algorithms;
        this.userDAO = userDAO;
        this.applicationConfig = applicationConfig;
        this.loginAttemptDAO = loginAttemptDAO;
    }

    /**
     * Method to authentitace a user in the system.
     *
     * @param username username given by the user who wants to authenticate in the system
     * @param password password given by the user who wants to authenticate in the system
     * @param validateExpiration validate expiration flag
     * @param remoteAddress ip address of user who wants to authenticate in the system
     * @param useLoginLock login lock flag
     * @param code google authentication code
     * @return AuthenticatedUser which represents authenticated user in the system
     * @throws AuthenticationException  general exception
     * @throws InvalidUsernameException when the username is not found in the system
     * @throws pl.nask.crs.security.authentication.InvalidPasswordException when given password doesn't match the username's password in the system
     * @throws PasswordExpiredException when the password has expired
     * @throws IllegalArgumentException when the username or the password is null
     */
    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration, String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws AuthenticationException, InvalidPasswordException, InvalidUsernameException, PasswordExpiredException, IllegalArgumentException {

        Validator.assertNotNull(username, "username");
        Validator.assertNotNull(password, "password");

        username = username.trim().toUpperCase();
        User user = userDAO.get(username);

        LoginAttempt lastAttempt = loginAttemptDAO.getLastAttemptByNic(username);
        int failedAttemptsCount = lastAttempt == null ? 0 : lastAttempt.getFailureCount();
        Date attemptDate = lastAttempt == null ? null : lastAttempt.getDate();
        loginLock(useLoginLock, user, failedAttemptsCount, attemptDate);

        if (user == null) {
            loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance(username, remoteAddress, Cause.INVALID_NIC, ++failedAttemptsCount));
            throw new InvalidUsernameException(username);
        }

        AuthenticatedUser u = authenticateWithPassword(user, password, validateExpiration, remoteAddress, failedAttemptsCount, systemDiscriminator);

        if (useTfa && user.isUseTwoFactorAuthentication()) {
            Validator.assertNotNull(code, "google code");
            if (Validator.isEmpty(user.getSecret())) {
                throw new GoogleAuthenticationException("Empty secret");
            }
            GAConfig gaConfig = applicationConfig.getGoogleAuthenticationConfig();
            if (!AuthenticationCodesVerifier.verifyCode(user.getSecret(), code, gaConfig.getPastIntervals(), gaConfig.getFutureIntervals())) {
                loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance(username, remoteAddress, Cause.INVALID_GA_PIN, ++failedAttemptsCount));
        	    throw new GoogleAuthenticationException("Invalid pin");
            }
        }

        loginAttemptDAO.createAttempt(LoginAttempt.newSuccessInstance(username, remoteAddress));
        return u;
    }

    private void loginLock(boolean useLoginLock, User user, int failedAttemptsCount, Date attemptDate) throws LoginLockException {
        if (useLoginLock && failedAttemptsCount > 0 && (user == null || !user.isUseTwoFactorAuthentication())) {
            int lockoutTimeInSeconds = getLockoutPeriod(failedAttemptsCount);

            Date currentDate = new Date();
            Date lockoutExpirationDate = DateUtils.addSeconds(attemptDate, lockoutTimeInSeconds);

            if (currentDate.before(lockoutExpirationDate)) {
                long secondsLeft = (lockoutExpirationDate.getTime() - currentDate.getTime()) / 1000;
                throw new LoginLockException(secondsLeft);
            }
        }
    }

    private int getLockoutPeriod(int failedAttemptsCount) {
        LoginLockoutConfig config = applicationConfig.getLoginLockoutConfig();
        int lockoutTimeInSeconds = config.getInitialLockoutPeriod();
        if (failedAttemptsCount > 1) {
            int lockoutIncreaseFactor = config.getLockoutIncreaseFactor();
            int maximumLockoutPeriod = config.getMaximumLockoutPeriod();
            for (int i = 1; i < failedAttemptsCount; i++) {
                lockoutTimeInSeconds *= lockoutIncreaseFactor;
                if (lockoutTimeInSeconds > maximumLockoutPeriod) {
                    lockoutTimeInSeconds = maximumLockoutPeriod;
                    break;
                }
            }
        }
        return lockoutTimeInSeconds;
    }

    private AuthenticatedUser authenticateWithPassword(User user, String password, boolean validateExpiration, String remoteAddress, int failedAttemptsCount, String systemDiscriminator)
            throws NoLoginPermissionException, PasswordExpiredException, InvalidPasswordException {
    	for (HashAlgorithm algorithm : algorithms) {
    		String encodedPassword = algorithm.hashString(password, user.getSalt());
    		if (encodedPassword.equals(user.getPassword())) {
    			if (!LoginPermissionChecker.checkLoginPermission(user, systemDiscriminator))
    				throw new NoLoginPermissionException(user.getUsername());
    			if (validateExpiration && !isPasswordActive(user.getUsername()))
    				throw new PasswordExpiredException();
    			return new AuthenticatedUserImpl(user.getUsername());
    		}
    	}
        loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance(user.getUsername(), remoteAddress, Cause.INVALID_PASSWORD, ++failedAttemptsCount));
    	throw new InvalidPasswordException();                    
	}

	protected boolean isPasswordActive(String userName) {
        boolean ret = false;
        Date lastChange = userDAO.getLastPasswordChangeDate(userName);
        // TODO if there is no entries in AccessHist password should be change?
        if (lastChange == null)
            return false;
        int period = applicationConfig.getPasswordExpiryPeriod();
        Calendar c = Calendar.getInstance();
        c.setTime(lastChange);
        c.add(Calendar.DATE, period);
        ret = new Date().before(c.getTime());
        return ret;
    }
}
