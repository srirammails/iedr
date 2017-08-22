package pl.nask.crs.commons.dns.validator;

public class NsGlueRequiredException extends Exception {

	private String domainName;
	private String nsName;
	private String ipAddress;

	public NsGlueRequiredException() {}
	
	public NsGlueRequiredException(String domainName, String nsName, String ipAddress) {
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

	
	
}
