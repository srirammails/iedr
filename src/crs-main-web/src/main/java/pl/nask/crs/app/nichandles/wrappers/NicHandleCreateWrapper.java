package pl.nask.crs.app.nichandles.wrappers;

import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.nichandle.NicHandle;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleCreateWrapper {
    
    private String name;
    private String companyName;
    private Long accountNumber;
    private String email;
    private String address;
    private String county;
    private String country;
    private String hostmastersRemark;
    private Set<String> phones = new HashSet<String>();
    private Set<String> faxes = new HashSet<String>();
    private String vatNo;

    private PhoneWrapper phonesWrapper;
    private PhoneWrapper faxesWrapper;
    public NicHandleCreateWrapper() {
        phonesWrapper = new PhoneWrapper(phones);
        faxesWrapper = new PhoneWrapper(faxes);
    }
    public PhoneWrapper getPhonesWrapper() {
        return phonesWrapper;
    }

    public PhoneWrapper getFaxesWrapper() {
        return faxesWrapper;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getFaxes() {
        return faxes;
    }

    public void setFaxes(Set<String> faxes) {
        this.faxes = faxes;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public void fillFrom(NicHandle nicHandle) {
        name = nicHandle.getName();
        companyName = nicHandle.getCompanyName();
        accountNumber = nicHandle.getAccount().getId();
        email = nicHandle.getEmail();
        address = nicHandle.getAddress();
        county = nicHandle.getCounty();
        country = nicHandle.getCountry();
        vatNo = nicHandle.getVat().getVatNo();
        
        phones = nicHandle.getPhones() != null ? nicHandle.getPhones() : phones;
        faxes = nicHandle.getFaxes() != null ? nicHandle.getFaxes() : faxes;
        phonesWrapper = new PhoneWrapper(phones);
        faxesWrapper = new PhoneWrapper(faxes);
    }
	public NewNicHandle makeNewNicHandle() {
		return new NewNicHandle(name, companyName, email, address, county, country, accountNumber, phones, faxes, vatNo);
		
	}
}
