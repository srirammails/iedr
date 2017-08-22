package pl.nask.crs.contacts.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.contacts.Contact;

/**
 * @author Patrycja Wegrzynowicz
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 * @author Kasia Fulara
 */
public class ContactSearchCriteria implements SearchCriteria<Contact> {

    private String name;
    private String nicHandle;
    private Integer account;
    private String type;
    private String companyName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() == 0) {
            this.name = null;
        } else {
            this.name = name;
        }
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}