package pl.nask.crs.commons.exceptions;

public class InvalidAuthCodeException extends Exception {

    private final String domainName;

    public InvalidAuthCodeException(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
