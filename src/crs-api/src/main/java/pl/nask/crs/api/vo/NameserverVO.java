package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.nameservers.Nameserver;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NameserverVO {

	private String name;

	private String ipAddress;

	public NameserverVO() {
	}

	public NameserverVO(String name, String ip) {
		this.name = name;
		this.ipAddress = ip;
	}
	
	public NameserverVO(Nameserver ns) {
		this.name = ns.getName();
		this.ipAddress = ns.getIpAddressAsString();
	}

	public String getName() {
		return name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
