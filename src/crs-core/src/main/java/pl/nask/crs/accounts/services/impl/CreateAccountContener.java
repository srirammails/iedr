package pl.nask.crs.accounts.services.impl;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;

import java.util.Date;
/**
 * @author Marianna Mysiorska
 */
public class CreateAccountContener {

    private static final String DEFAULT_CREATE_STATUS = "Active";

    private Account account;
    private boolean accessGroups = true;

    public CreateAccountContener(String name, String webAddress, String billingContactNicHandle, String hostmastersRemark, boolean agreementSigned, boolean ticketEdit){
        Validator.assertNotEmpty(name, "account name");
        Validator.assertNotEmpty(webAddress, "web address");
        Validator.assertNotEmpty(billingContactNicHandle, "billing contact nic handle");
        Contact billingContact = new Contact(billingContactNicHandle);
        Date nowDate = new Date();
        account = new Account(name, billingContact, DEFAULT_CREATE_STATUS, "address", "county", "country", webAddress, "if", "im", "11111", "11111", "tariff", hostmastersRemark, nowDate, nowDate, nowDate, agreementSigned, ticketEdit);
    }

    public Account getAccount() {
        return account;
    }

    void setAccount(Account account) {
        this.account = account;
    }

    public boolean isAccessGroups() {
        return accessGroups;
    }

    public void setAccessGroups(boolean accessGroups) {
        this.accessGroups = accessGroups;
    }
}
