package pl.nask.crs.app.nichandles.wrappers;

import java.util.Set;

public class NewNicHandle {

	private String name;
	private String companyName;
	private String email;
	private String address;
	private String county;
	private String country;
	private Long accountNumber;
	private Set<String> phones;
	private Set<String> faxes;
	private String vatNo;

	public NewNicHandle() {
		
	}
	
	public NewNicHandle(String name, String companyName, String email,
			String address, String county, String country, Long accountNumber,
			Set<String> phones, Set<String> faxes, String vatNo) {
				this.name = name;
				this.companyName = companyName;
				this.email = email;
				this.address = address;
				this.county = county;
				this.country = country;
				this.accountNumber = accountNumber;
				this.phones = phones;
				this.faxes = faxes;
				this.vatNo = vatNo;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public Set<String> getFaxes() {
		return faxes;
	}

	public String getAddress() {
		return address;
	}

	public String getCounty() {
		return county;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCountry() {
		return country;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public void setFaxes(Set<String> faxes) {
		this.faxes = faxes;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	
}
