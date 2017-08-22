package pl.nask.crs.accounts;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;

import java.util.Date;

/**
 * It represents a reseller account.
 *
 * @author Patrycja Wegrzynowicz
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */
public class Account {

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_SUSPENDED = "Suspended";
    private static final String STATUS_DELETED = "Deleted";

    protected long id;
    protected String name;
    protected String status;
    protected String address;
    protected String county;
    protected String country;
    protected String webAddress;
    protected String invoiceFreq;
    protected String nextInvMonth;
    protected String phone;
    protected String fax;
    protected String tariff;
    protected String remark;
    protected Contact billingContact;
    protected Date creationDate;
    protected Date statusChangeDate;
    protected Date changeDate;
    // Feature #2373 - flags for "signed agreement" and "edit ticket"
    protected boolean agreementSigned;
    protected boolean ticketEdit;
    protected String vatCategory;

    public Account(long id) {
        this(id, null);
    }

    public Account(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(long id, String name, Contact contact, String status, String address, String county, String country, String webAddress, String invoiceFreq, String nextInvMonth, String phone, String fax, String tariff, String remark, Date creationDate, Date statusChangeDate, Date changeDate, boolean agreementSigned, boolean editTicket, String vatCategory) {
    	Validator.assertNotNull(id, "id");
    	if (!agreementSigned && ticketEdit)
    		throw new IllegalArgumentException("ticketEdit cannot be set to true if agreementSigned is not set to true");
        this.agreementSigned = agreementSigned;
		this.ticketEdit = editTicket;
        this.billingContact = contact;
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
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.changeDate = changeDate;
        this.vatCategory = vatCategory;
    }

    public Account(String name, Contact contact, String status, String address, String county, String country, String webAddress, String invoiceFreq, String nextInvMonth, String phone, String fax, String tariff, String remark, Date creationDate, Date statusChangeDate, Date changeDate, boolean agreementSigned, boolean editTicket) {
    	if (!agreementSigned && ticketEdit)
    		throw new IllegalArgumentException("ticketEdit cannot be set to true if agreementSigned is not set to true");
        this.billingContact = contact;
		this.agreementSigned = agreementSigned;
		this.ticketEdit = editTicket;
        this.id = -1;
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
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.changeDate = changeDate;        
    }

    public Contact getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(Contact billingContact) {
        this.billingContact = billingContact;
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

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public long getId() {
        return id;
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

    public boolean setStatus(String status) throws IllegalArgumentException {
        Validator.assertNotNull(status, "account status");
        if (!status.equals(this.status)) {
            this.status = status;
            this.statusChangeDate = new Date();
            return true;
        }
        return false;
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

    public boolean isActive() {
        return STATUS_ACTIVE.equals(status);
    }

    public boolean isSuspended() {
        return STATUS_SUSPENDED.equals(status);
    }

    public boolean isDeleted() {
        return STATUS_DELETED.equals(status);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (id != account.id) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    public void updateRemark(String hostmasterHandle) {
        remark += " by " + hostmasterHandle + " on " + new Date();
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

	public void validateFlags() {
		if (!agreementSigned && ticketEdit)
    		throw new IllegalStateException("ticketEdit cannot be set to true if agreementSigned is not set to true");		
	}

    public String getVatCategory() {
        return vatCategory;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%s, name=%s]", id, name);
    }

	public void setVatCategory(String vatCategory) {		
		this.vatCategory = vatCategory;
	}
}