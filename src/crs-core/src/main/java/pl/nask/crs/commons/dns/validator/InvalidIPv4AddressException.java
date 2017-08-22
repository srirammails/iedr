package pl.nask.crs.commons.dns.validator;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InvalidIPv4AddressException extends InvalidIPAddressException {

    public InvalidIPv4AddressException(String address) {
        super(address);
    }
}
