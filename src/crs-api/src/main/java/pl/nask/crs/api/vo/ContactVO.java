package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.contacts.Contact;

@XmlType
public class ContactVO {


    private String nicHandle;

    private String name;

    private String email;

    private String companyName;

    private String country;

	
	public ContactVO() {
	}

	public ContactVO(Contact contact) {
		this.nicHandle = contact.getNicHandle();
		this.name = contact.getName();
		this.email = contact.getEmail();
		this.companyName = contact.getCompanyName();
		this.country = contact.getCountry();
	}

    public ContactVO(String nicHandle, String name) {
        this.nicHandle = nicHandle;
        this.name = name;
    }

    public String getNicHandle() {
		return nicHandle;
	}

	public void setNicHandle(String nicHandle) {
		this.nicHandle = nicHandle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
