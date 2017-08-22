package pl.nask.crs.security.authentication.googleauthenticator;


import pl.nask.crs.user.googleauthenticator.SecretGenerator;

public class AuthenticationCodesVerifier {
	/**
	 * Verifies if code is valid for given secret at the moment of calling this method.
	 *
	 * @param secret A secret belonging to the user who calculated the code. It should be 16 characters long.
	 * @param code   A code to verify
	 * @return true if code valid, false otherwise
	 */
	public static boolean verifyCode(String secret, String code) {
		long currentInterval = System.currentTimeMillis() / 1000 / 30;
		return verifyCode(secret, code, currentInterval);
	}

	/**
	 * Verifies if code is valid for given secret during the given interval. Interval is the number of 30 second
	 * periods since the start of epoch (1 January 1970).
	 *
	 * @param secret   A secret belonging to the user who calculated the code. It should be 16 characters long.
	 * @param code     A code to verify
	 * @param interval A interval to check
	 * @return true if code valid, false otherwise
	 */
	public static boolean verifyCode(String secret, String code, long interval) {
		AuthenticationCodesCalculator authenticationCodesCalculator = new AuthenticationCodesCalculator();
		String expectedCode = authenticationCodesCalculator.calculateCode(secret, interval);
		return expectedCode.equals(code);
	}

	/**
	 * Verifies if code is valid for given secret at the moment of calling this method. The code is considered valid
	 * if it is valid in current interval or in any of given past or future intervals. Interval is the number of 30
	 * second periods since the start of epoch (1 January 1970).
	 *
	 * @param secret          A secret belonging to the user who calculated the code. It should be 16 characters long.
	 * @param code            A code to verify
	 * @param pastIntervals   how many intervals in the past check for matching code
	 * @param futureIntervals how many intervals in the future check for matching code
	 * @return true if code valid, false otherwise
	 */
	public static boolean verifyCode(String secret, String code, int pastIntervals, int futureIntervals) {
		long currentInterval = System.currentTimeMillis() / 1000 / 30;
		return verifyCode(secret, code, currentInterval, pastIntervals, futureIntervals);
	}

	/**
	 * Verifies if code is valid for given secret during the given interval. The code is considered valid if it is
	 * valid in the given interval or in any of given past or future intervals. Interval is the number of 30 second
	 * periods since the start of epoch (1 January 1970).
	 *
	 * @param secret          A secret belonging to the user who calculated the code. It should be 16 characters long.
	 * @param code            A code to verify
	 * @param interval        An interval to check
	 * @param pastIntervals   How many intervals in the past check for matching code
	 * @param futureIntervals How many intervals in the future check for matching code
	 * @return true if code valid, false otherwise
	 */
	public static boolean verifyCode(String secret, String code, long interval, int pastIntervals, int futureIntervals) {
		for (long i = interval - pastIntervals; i <= interval + futureIntervals; i++) {
			if (verifyCode(secret, code, i)) {
				return true;
			}
		}
		return false;
	}
}
