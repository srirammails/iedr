package pl.nask.crs.user.googleauthenticator;

import org.apache.commons.codec.binary.Base32;

import java.util.Random;

public class SecretGenerator {

	/**
	 * Generates a secret for a user. Each user should have unique and confidential secret. Generated secret is 16
	 * characters long and is a valid Base32 string.
	 *
	 * @return Generated secret.
	 */
	public static String generateSecret() {
		//since Base32 encoding of x bytes generate 8x/5 characters, we will use 10 bytes for the secret key
		int secretSize = 10;
		byte[] buffer = new byte[secretSize];
		new Random().nextBytes(buffer);
		return new Base32().encodeAsString(buffer);
	}
}
