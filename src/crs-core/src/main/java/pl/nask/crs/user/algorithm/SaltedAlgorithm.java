package pl.nask.crs.user.algorithm;

import org.mindrot.jbcrypt.BCrypt;
import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.utils.Validator;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SaltedAlgorithm implements HashAlgorithm {

    /*
    BCrypt internal property indicating the number of iterations for key stretching added to salt,
    2^10 - default for BCrypt.gensalt())
     */
    private final static String ITERATION_STRING = "$2a$10$";

    @Override
    public String hashString(String text, String salt) {
        Validator.assertNotEmpty(text, "password");
        Validator.assertNotEmpty(salt, "salt");
        String internalSalt = toInternalSalt(salt);
        String internalHash = BCrypt.hashpw(text, internalSalt);
        return internalHash.replace(ITERATION_STRING, "").replace(salt, "");
    }

    private String toInternalSalt(String salt) {
        return ITERATION_STRING + salt;
    }

    private String toSalt(String internalSalt) {
        return internalSalt.replace(ITERATION_STRING, "");
    }

    public String getSalt() {
        return toSalt(BCrypt.gensalt());
    }

    @Override
    public String hashString(String text) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        String pass = "123456";
        SaltedAlgorithm algorithm = new SaltedAlgorithm();
//        String salt = algorithm.getSalt();
        String salt = "5rvBKX0kRgHBXQ1MMjiFXu";
        System.out.println("salt: " + salt + " size:" + salt.length());
        String hashedPass = algorithm.hashString(pass, salt);
        System.out.println("new = " + hashedPass);
        System.out.println("---------------------");
    }

}
