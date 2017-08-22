package pl.nask.crs.domains.transfer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BulkTransferRequest {

	private long id;
	private long losingAccount;
	private String losingAccountName;
	private long gainingAccount;
	private String gainingAccountName;
	private String remarks;
	private Date completionDate;
	private String hostmasterNh;
	
	private List<TransferredDomain> requestedDomains;

	public BulkTransferRequest() {
	}
	
	public BulkTransferRequest(long losingAccount, long gainingAccount, String remarks) {
		this.losingAccount = losingAccount;
		this.gainingAccount = gainingAccount;
		this.remarks = remarks;	
	}

	public long getLosingAccount() {
		return losingAccount;
	}

	public void setLosingAccount(long losingAccount) {
		this.losingAccount = losingAccount;
	}

	public long getGainingAccount() {
		return gainingAccount;
	}

	public void setGainingAccount(long gainingAccount) {
		this.gainingAccount = gainingAccount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<TransferredDomain> getRequestedDomains() {
		return requestedDomains;
	}

	public void setRequestedDomains(List<TransferredDomain> requestedDomains) {
		this.requestedDomains = requestedDomains;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getLosingAccountName() {
		return losingAccountName;
	}

	public void setLosingAccountName(String losingAccountName) {
		this.losingAccountName = losingAccountName;
	}

	public String getGainingAccountName() {
		return gainingAccountName;
	}

	public void setGainingAccountName(String gainingAccountName) {
		this.gainingAccountName = gainingAccountName;
	}

	/*
	 * note, that the cost of retainAll depends on the cost of contains() method of the argument. 
	 * This is why HashSet is used as an argument for retailAll.
	 * And since retainAll modifies collection, a copy is needed here 
	 *
	 */
	public Set<String> containsDomains(List<String> domains) {
		Set<String> requestedDomainsSet = getDomainNames();
		Set<String> domainsCopy = new HashSet<String>(domains);
		domainsCopy.retainAll(requestedDomainsSet);
		
		return domainsCopy;
	}

	public Set<String> getDomainNames() {
		Set<String> requestedDomainsSet = new HashSet<String>();
		for (TransferredDomain d: requestedDomains) {
			requestedDomainsSet.add(d.getDomainName());
		}
		return requestedDomainsSet;
	}

	public boolean isClosed() {
		return completionDate != null;
	}

	public boolean isFullyCompleted() {
		if (requestedDomains == null)
			throw new IllegalStateException("Not fully initialized");
		for (TransferredDomain d: requestedDomains) {
			if (d.getTransferDate() == null)
				return false;
		}
		
		return true;
	}
	
	public void setHostmasterNh(String hostmasterNh) {
		this.hostmasterNh = hostmasterNh;
	}
	
	public String getHostmasterNh() {
		return hostmasterNh;
	}
}
