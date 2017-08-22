package pl.nask.crs.nichandle.dao.ibatis.objects;

import pl.nask.crs.nichandle.NicHandle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Timestamp;

/**
 * @author Marianna Mysiorska
 */


public class InternalNicHandle {

    private String nicHandleId;
    private String name;
    private Long accountNumber;    
    private String accountName;
    private boolean agreementSigned;
    private boolean ticketEdit;
    private String companyName;
    private String address;
    private List<Telecom> telecoms = new ArrayList<Telecom>();
    private String county;
    private String country;
    private String email;
    private NicHandle.NHStatus status;
    private Date statusChangeDate;
    private Date registrationDate;
    private Timestamp changeDate;
    private boolean billCInd;
    private String nicHandleRemark;
    private String creator;
    private String vatNo;
    private String vatCategory;

    public InternalNicHandle(){}

    public InternalNicHandle(String nicHandleId, String name, Long accountNumber, String accountName, String companyName, String address, List<Telecom> telecoms, String county, String country, String email, NicHandle.NHStatus status, Date statusChangeDate, Date registrationDate, Timestamp changeDate, boolean billCInd, String nicHandleRemark, String creator, String vatNo, String vatCategory) {
        this.nicHandleId = nicHandleId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.companyName = companyName;
        this.address = address;
        this.telecoms.addAll(telecoms);
        this.county = county;
        this.country = country;
        this.email = email;
        this.status = status;
        this.statusChangeDate = statusChangeDate;
        this.registrationDate = registrationDate;
        this.changeDate = changeDate;
        this.billCInd = billCInd;
        this.nicHandleRemark = nicHandleRemark;
        this.creator = creator;
        this.vatNo = vatNo;
        this.vatCategory = vatCategory;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public List<Telecom> getTelecoms() {
        return telecoms;
    }

    public void setTelecoms(List<Telecom> telecoms) {
        this.telecoms.clear();
        if (telecoms != null) {
            this.telecoms.addAll(telecoms);
        }
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

    public NicHandle.NHStatus getStatus() {
        return status;
    }

    public void setStatus(NicHandle.NHStatus status) {
        this.status = status;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isBillCInd() {
        return billCInd;
    }

    public void setBillCInd(boolean billCInd) {
        this.billCInd = billCInd;
    }

    public String getNicHandleRemark() {
        return nicHandleRemark;
    }

    public void setNicHandleRemark(String nicHandleRemark) {
        this.nicHandleRemark = nicHandleRemark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public boolean vatNoNotEmpty(){
        return (vatNo != null && !vatNo.trim().equals(""));
    }

	public boolean isAgreementSigned() {
		return agreementSigned;
	}

	public void setAgreementSigned(boolean agreementSigned) {
		this.agreementSigned = agreementSigned;
	}

	public boolean isTicketEdit() {
		return ticketEdit;
	}

	public void setTicketEdit(boolean ticketEdit) {
		this.ticketEdit = ticketEdit;
	}

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }
}
