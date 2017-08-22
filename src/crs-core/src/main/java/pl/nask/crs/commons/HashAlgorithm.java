package pl.nask.crs.commons;

/**
 * Interface to hash objects using an algorithm.
 *
 * @author Marianna Mysiorska
 */
public interface HashAlgorithm {

    /**
     * Method to hash given string using some algorithm.
     *
     * @param text string to hash
     * @return hashed string, never null
     * @throws IllegalArgumentException if null is passed
     */
    @Deprecated
    public String hashString(String text);

    public String hashString(String text, String salt);

    public String getSalt();
}
