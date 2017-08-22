package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class IpSyntaxException extends ValidationException {

    private String ipAddress;

    public IpSyntaxException(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid ip syntax: %s", ipAddress);
    }
}
