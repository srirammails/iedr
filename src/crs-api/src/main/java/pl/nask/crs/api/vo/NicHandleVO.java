package pl.nask.crs.api.vo;

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandle.NHStatus;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NicHandleVO {

    private String nicHandleId;
    private String name;
    private Long accountId;
    private String companyName;
    private String address;
    private Set<String> phones;
    private Set<String> faxes;
    private String county;
    private String country;
    private String email;
    private NHStatus status;
    private Date statusChangeDate;
    private Date registrationDate;
    private Date changeDate;
    private boolean billCInd;
    private String nicHandleRemark;
    private String creator;
    private String vatNo;

    public NicHandleVO() {
	}


	public NicHandleVO(NicHandle nicHandle) {
		nicHandleId = nicHandle.getNicHandleId();
		name = nicHandle.getName();
		accountId = nicHandle.getAccount() == null ? null : nicHandle.getAccount().getId();
		companyName = nicHandle.getCompanyName();
		address = nicHandle.getAddress();
		phones = nicHandle.getPhones();
		faxes = nicHandle.getFaxes();
		county = nicHandle.getCounty();
		country = nicHandle.getCountry();
		email = nicHandle.getEmail();
		status = nicHandle.getStatus();
		statusChangeDate = nicHandle.getStatusChangeDate();
		registrationDate = nicHandle.getRegistrationDate();
		changeDate = nicHandle.getChangeDate();
		billCInd = nicHandle.isBillCInd();
		nicHandleRemark = nicHandle.getNicHandleRemark();
		creator = nicHandle.getCreator();
		vatNo = nicHandle.getVat() == null ? null : nicHandle.getVat().getVatNo();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isBillCInd() {
		return billCInd;
	}

	public String getNicHandleRemark() {
		return nicHandleRemark;
	}


	public void setNicHandleRemark(String nicHandleRemark) {
		this.nicHandleRemark = nicHandleRemark;
	}


	public String getVatNo() {
		return vatNo;
	}


	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}


	public String getNicHandleId() {
		return nicHandleId;
	}


	public NHStatus getStatus() {
		return status;
	}


	public Date getStatusChangeDate() {
		return statusChangeDate;
	}


	public Date getRegistrationDate() {
		return registrationDate;
	}


	public Date getChangeDate() {
		return changeDate;
	}


	public String getCreator() {
		return creator;
	}
}
