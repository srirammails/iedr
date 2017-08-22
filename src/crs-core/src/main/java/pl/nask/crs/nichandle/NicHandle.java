package pl.nask.crs.nichandle;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


/**
 * @author Marianna Mysiorska
 */


public class NicHandle {

    public static enum NHStatus {
        Active, Deleted, Suspended, Renew, New
    }

    private String nicHandleId;
    private String name;
    private Account account;
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
    private Timestamp changeDate;
    private boolean billCInd;
    private String nicHandleRemark;
    private String creator;
    private Vat vat;
    protected String vatCategory;

    public NicHandle(String nicHandleId, String name, Account account, String companyName, String address, Set<String> phones, Set<String> faxes, String county, String country, String email, NHStatus status, Date statusChangeDate, Date registrationDate, Timestamp changeDate, boolean billCInd, String nicHandleRemark, String creator, Vat vat, String vatCategory) {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(name, "name");
        Validator.assertNotNull(account, "account");
        Validator.assertNotNull(companyName, "company name");
        Validator.assertNotNull(address, "address");
        Validator.assertNotNull(county, "county");
        Validator.assertNotNull(country, "country");
        Validator.assertNotNull(email, "email");
        Validator.assertNotNull(status, "status");
        Validator.assertNotNull(statusChangeDate, "status change date");
        Validator.assertNotNull(changeDate, "change date");
        this.nicHandleId = nicHandleId;
        this.name = name;
        this.account = account;
        this.companyName = companyName;
        this.address = address;
        this.phones = phones;
        this.faxes = faxes;
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
        this.vat = vat;
        this.vatCategory = vatCategory;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public Set<String> getFaxes() {
        return faxes;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
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

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public boolean isBillCInd() {
        return billCInd;
    }

    public String getNicHandleRemark() {
        return nicHandleRemark;
    }

    public String getCreator() {
        return creator;
    }

    public Vat getVat() {
        return vat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public void setFaxes(Set<String> faxes) {
        this.faxes = faxes;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean setStatus(NHStatus status) throws IllegalArgumentException {
        Validator.assertNotNull(status, "nic handle status");
        if (this.status != status) {
            this.status = status;
            this.statusChangeDate = new Date();
            return true;
        }
        return false;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public void setChangeDate(Date date) {
        this.changeDate = new Timestamp(date.getTime());
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    public void setNicHandleRemark(String nicHandleRemark) {
        this.nicHandleRemark = nicHandleRemark;
    }

    public void updateRemark(String hostmasterHandle) {
        nicHandleRemark += " by " + hostmasterHandle + " on " + new Date();
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    @Override
    public String toString() {
    	return nicHandleId;
    }

	public String getPhonesAsString() {
		return setAsString(getPhones());
	}

	private String setAsString(Set<String> set) {
		return CollectionUtils.toString(set, true, ", ");
	}
	
	public String getFaxesAsString() {
		return setAsString(getFaxes());
	}
}
