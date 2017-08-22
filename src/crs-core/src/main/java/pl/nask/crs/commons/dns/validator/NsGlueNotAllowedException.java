package pl.nask.crs.commons.dns.validator;

/**
 * 
 * @author Artur Gniadzik
 *
 */
public class NsGlueNotAllowedException extends Exception {

	private String domainName;
	private String nsName;
	private String ipAddress;

	public NsGlueNotAllowedException() {}
	
	public NsGlueNotAllowedException(String domainName, String nsName, String ipAddress) {
		this.domainName = domainName;
		this.nsName = nsName;
		this.ipAddress = ipAddress;
	}

	public String getDomainName() {
		return domainName;
	}

	public String getNsName() {
		return nsName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setNsName(String nsName) {
		this.nsName = nsName;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	

	
}
