package pl.nask.crs.app.accounts.wrappers;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Marianna Mysiorska
 */
public class AccountWrapper {

    private Account account;
    private String newBillingContactId;
    private String newBillingContactName;
    private String newBillingContactEmail;

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

    public AccountWrapper(Account account) {
        this.account = account;
        this.newBillingContactId = account.getBillingContact().getNicHandle();
        this.newBillingContactName = account.getBillingContact().getName();
        this.newBillingContactEmail = account.getBillingContact().getEmail();
    }

    public String getNewBillingContactId() {
        return newBillingContactId;
    }

    public void setNewBillingContactId(String newBillingContactId) {
        this.newBillingContactId = newBillingContactId;
    }

    public String getNewBillingContactName() {
        return newBillingContactName;
    }

    public void setNewBillingContactName(String newBillingContactName) {
        this.newBillingContactName = newBillingContactName;
    }

    public String getNewBillingContactEmail() {
        return newBillingContactEmail;
    }

    public void setNewBillingContactEmail(String newBillingContactEmail) {
        this.newBillingContactEmail = newBillingContactEmail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<String> getMonths(){
        return months;
    }
}
