package pl.nask.crs.api.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountVO {

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
    protected ContactVO billingContact;
    protected Date creationDate;
    protected Date statusChangeDate;
    protected Date changeDate;
    // Feature #2373 - flags for "signed agreement" and "edit ticket"
    protected boolean agreementSigned;
    protected boolean ticketEdit;

    public AccountVO() {
	}
    
	public AccountVO(Account account) {
		this.id = account.getId();
		this.name = account.getName();
		this.status = account.getStatus();
		this.address = account.getAddress();
		this.country = account.getCountry();
		this.county = account.getCounty();
		this.webAddress = account.getWebAddress();
		this.invoiceFreq = account.getInvoiceFreq();
		this.nextInvMonth = account.getNextInvMonth();
		this.phone = account.getPhone();
		this.fax = account.getFax();
		this.tariff = account.getTariff();
		this.remark = account.getRemark();
		setBillingContact(account.getBillingContact());
		this.creationDate = account.getCreationDate();
		this.statusChangeDate = account.getStatusChangeDate();
		this.changeDate = account.getChangeDate();
		this.agreementSigned = account.isAgreementSigned();
		this.ticketEdit = account.isTicketEdit();

	}

	public void setBillingContact(Contact billingContact) {
		if (billingContact != null)
			this.billingContact = new ContactVO(billingContact);
	}

}
