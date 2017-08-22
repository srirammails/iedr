package pl.nask.crs.app.accounts.wrappers;

import pl.nask.crs.accounts.services.impl.CreateAccountContener;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Marianna Mysiorska
 */
public class AccountCreateWrapper {

    private String name;
    private String address;
    private String county;
    private String country;
    private String webAddress;
    private String invoiceFreq;
    private String nextInvMonth;
    private String phone;
    private String fax;
    private String tariff;
    private String billingContactNicHandle;
    private String billingContactName;
    private String billingContactEmail;
    private boolean agreementSigned;
    private boolean ticketEdit;

    private static final List<String> months;
    static{
        months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
    }

    public AccountCreateWrapper(){
        this.invoiceFreq = "Monthly";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBillingContactNicHandle() {
        return billingContactNicHandle;
    }

    public void setBillingContactNicHandle(String billingContactNicHandle) {
        this.billingContactNicHandle = billingContactNicHandle;
    }

    public List<String> getMonths(){
        return months;
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

	public CreateAccountContener createContener(String hostmastersRemark){
        return new CreateAccountContener(name, webAddress, billingContactNicHandle, hostmastersRemark, agreementSigned, ticketEdit );
    }
    
}
