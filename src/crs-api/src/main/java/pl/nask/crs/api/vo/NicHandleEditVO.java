package pl.nask.crs.api.vo;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.Vat;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NicHandleEditVO {
	@XmlElement(required=true)
	private String name;
	// optional
	private String companyName;
	@XmlElement(required=true)
	private String email;
	@XmlElement(required=true)
	private String address;
	@XmlElement(required=true)
	private String county;
	@XmlElement(required=true)
	private String country;
	@XmlElement(required=true)
	private Long accountNumber;
	@XmlElement(required=true, nillable=false)
	private Set<String> phones;
	// optional
	@XmlElement(nillable=false)
	private Set<String> faxes;
	// optional 
	private String vatNo;

    public NicHandleEditVO() {
	}
	
	public NicHandleEditVO(NicHandleVO vo) {
	    name = vo.getName();
	    companyName = vo.getCompanyName();
	    email = vo.getEmail();
	    address = vo.getAddress();
	    county = vo.getCounty();
	    country = vo.getCountry();
	    accountNumber = vo.getAccountId();
	    phones = vo.getPhones();
	    faxes = vo.getFaxes();
	    vatNo = vo.getVatNo();
	}
	
	public NewNicHandle toNewNicHandle() {
		return new NewNicHandle(name, companyName, email, address, county, country, accountNumber, phones, faxes, vatNo);
	}

	public void copyTo(NicHandle nh) {
		nh.setName(name);
		nh.setCompanyName(companyName);
		nh.setEmail(email);
		nh.setAddress(address);
		nh.setCounty(county);
		nh.setCountry(country);
		if (accountNumber != null)
			nh.setAccount(new Account(accountNumber));
		else 
			nh.setAccount(null);
		
		if (phones != null)
			phones.remove(null);
		nh.setPhones(phones);
		
		if (faxes != null)
			faxes.remove(null);		
		nh.setFaxes(faxes);
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

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public Set<String> getFaxes() {
        return faxes;
    }

    public String getVatNo() {
        return vatNo;
    }
}
