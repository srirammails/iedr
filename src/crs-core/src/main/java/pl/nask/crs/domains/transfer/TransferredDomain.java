package pl.nask.crs.domains.transfer;

import java.util.Date;

public class TransferredDomain {
	private String domainName;
	private Date transferDate;
	private String hostmasterNh;
	
	public TransferredDomain() {
	}
	
	public TransferredDomain(String domainName, Date transferDate) {
		super();
		this.domainName = domainName;
		this.transferDate = transferDate;
	}

	public String getDomainName() {
		return domainName;
	}
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public Date getTransferDate() {
		return transferDate;
	}
	
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	
	public void setHostmasterNh(String hostmasterNh) {
		this.hostmasterNh = hostmasterNh;
	}
	
	public String getHostmasterNh() {
		return hostmasterNh;
	}
	
}
