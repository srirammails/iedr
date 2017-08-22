package pl.nask.crs.accounts.dao.ibatis.objects;

import pl.nask.crs.accounts.Account;

import java.util.Date;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalAccount {

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_SUSPENDED = "Suspended";
    private static final String STATUS_DELETED = "Deleted";

    private long id;
    private String name;
    private String status;
    private String address;
    private String county;
    private String country;
    private String webAddress;
    private String invoiceFreq;
    private String nextInvMonth;
    private String phone;
    private String fax;
    private String tariff;
    private String remark;
    private String billingContactId;
    private String billingContactName;
    private String billingContactEmail;
    private String billingContactCompanyName;
    private String billingContactCountry;
    private Date creationDate;
    private Date statusChangeDate;
    private Date changeDate;
    // Feature #2373 - flags for "signed agreement" and "edit ticket"
    protected boolean agreementSigned;
    protected boolean ticketEdit;
    protected String vatCategory;

    public InternalAccount() {
        this.id = -1;
    }

    public InternalAccount(long id, String name, String status, String address, String county, String country, String webAddress, String invoiceFreq, String nextInvMonth, String phone, String fax, String tariff, String remark, String billingContactId, String billingContactName, String billingContactEmail, String billingContactCompanyName, String billingContactCountry, Date creationDate, Date statusChangeDate, Date changeDate, boolean agreementSigned, boolean ticketEdit, String vatCategory) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.address = address;
        this.county = county;
        this.country = country;
        this.webAddress = webAddress;
        this.invoiceFreq = invoiceFreq;
        this.nextInvMonth = nextInvMonth;
        this.phone = phone;
        this.fax = fax;
        this.tariff = tariff;
        this.remark = remark;
        this.billingContactId = billingContactId;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.billingContactCompanyName = billingContactCompanyName;
        this.billingContactCountry = billingContactCountry;
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.changeDate = changeDate;
		this.agreementSigned = agreementSigned;
		this.ticketEdit = ticketEdit;
        this.vatCategory = vatCategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getInvoiceFreq() {
        return invoiceFreq;
    }

    public void setInvoiceFreq(String invoiceFreq) {
        this.invoiceFreq = invoiceFreq;
    }

    public String getNextInvMonth() {
        return nextInvMonth;
    }

    public void setNextInvMonth(String nextInvMonth) {
        this.nextInvMonth = nextInvMonth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBillingContactId() {
        return billingContactId;
    }

    public void setBillingContactId(String billingContactId) {
        this.billingContactId = billingContactId;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getBillingContactCompanyName() {
        return billingContactCompanyName;
    }

    public void setBillingContactCompanyName(String billingContactCompanyName) {
        this.billingContactCompanyName = billingContactCompanyName;
    }

    public String getBillingContactCountry() {
        return billingContactCountry;
    }

    public void setBillingContactCountry(String billingContactCountry) {
        this.billingContactCountry = billingContactCountry;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
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
