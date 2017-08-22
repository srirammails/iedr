package pl.nask.crs.security.authentication.googleauthenticator;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AuthenticationCodesCalculator {

	private static final int PASS_CODE_LENGTH = 6;

	/**
	 * Calculates a code for a given secret at the moment of calling this method.
	 *
	 * @param secret A secret belonging to the user who calculates a code. It should be 16 characters long.
	 * @return a calculated code
	 */
	public String calculateCode(String secret) {
		long currentInterval = System.currentTimeMillis() / 1000 / 30;
		return calculateCode(secret, currentInterval);
	}

	/**
	 * Calculates a code for a given secret and a given interval. Interval is the number of 30 second periods
	 * since the start of the epoch (1 January 1970).
	 *
	 * @param secret   A secret belonging to the user who calculates a code. It should be 16 characters long.
	 * @param interval An interval for which a code should be calculated.
	 * @return a calculated code
	 */
	public String calculateCode(String secret, long interval) {
		byte[] decodedSecret = decodeSecret(secret);

		byte[] data = new byte[8];
		for (int i = 8; i-- > 0; interval >>>= 8) {
			data[i] = (byte) interval;
		}

		SecretKeySpec signKey = new SecretKeySpec(decodedSecret, "HMACSHA1");
		Mac mac;
		try {
			mac = Mac.getInstance("HMACSHA1");
			mac.init(signKey);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Exception while calculating code: " + e.getMessage());
		} catch (InvalidKeyException e) {
			throw new RuntimeException("Exception while calculating code: " + e.getMessage());
		}
		byte[] hash = mac.doFinal(data);

		int offset = hash[hash.length - 1] & 0xF;

		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes, we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return padValue(truncatedHash, PASS_CODE_LENGTH);
	}

	private byte[] decodeSecret(String encoded) {
		Base32 b = new Base32();
		return b.decode(encoded.toUpperCase());
	}

	private String padValue(long value, int toLength) {
		String result = Long.toString(value);
		for (int i = result.length(); i < toLength; i++) {
			result = "0" + result;
		}
		return result;
	}

    public static void main(String[] args) {
        AuthenticationCodesCalculator calculator = new AuthenticationCodesCalculator();
        System.out.println(calculator.calculateCode("KKGWCZDQNG3DRDCR"));
    }
}
