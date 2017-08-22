package pl.nask.crs.domains.nameservers;

import pl.nask.crs.commons.utils.Validator;

/**
 * todo: validation/normalization of an IP address
 *
 * @author Patrycja Wegrzynowicz
 */
public class IPAddress {
	
	
	/**
	 * returns an instance of this class for the ip address given as an argument 
	 * @param address
	 * @return IPAddress instance or null, it the argument is null;
	 */
	public static IPAddress instanceFor(String address) {
		if (address == null) {
			return new IPAddress("");			
		} else {
			return new IPAddress(address);
		}
	}

    enum Type {
        IPv4, IPv6
    }

    private String address;

    public IPAddress(String address) {
        setAddress(address);
    }

    public Type getType() {
        return address.contains(":") ? Type.IPv6 : Type.IPv4;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Validator.assertNotNull(address, "ip address");
        this.address = address;
    }

}