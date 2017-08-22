package pl.nask.crs.domains.nameservers;

import pl.nask.crs.commons.utils.Validator;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class Nameserver {

    private String name;

    private IPAddress ipAddress;

    private Integer order;

    public Nameserver(String name) {
        this(name, null, null);
    }

    public Nameserver(String name, IPAddress ipAddress) {
        this(name, ipAddress, null);
    }

    public Nameserver(String name, IPAddress ipAddress, Integer order) {
        Validator.assertNotEmpty(name, "host name");
        this.name = name;
        this.ipAddress = ipAddress;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public String getIpAddressAsString() {
        return ipAddress == null ? null : ipAddress.getAddress();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Nameserver other = (Nameserver) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		return true;
	}
	
    
}