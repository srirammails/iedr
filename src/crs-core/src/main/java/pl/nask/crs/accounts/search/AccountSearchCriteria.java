package pl.nask.crs.accounts.search;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.SearchCriteria;

/**
 * Search criteria for Account objects
 * 
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 */
public class AccountSearchCriteria implements SearchCriteria<Account> {

    private String nicHandle;
    private Long id;
    private String name;
    private String domainName;
    private String negativeStatus;

    /**
     * set to search for not deleted accounts
     */
    public void notDeleted() {
        this.negativeStatus = "Deleted";
    }

    /**
     *
     * @return status flag which must be NOT set 
     */
    public String getNegativeStatus() {
        return negativeStatus;
    }

    /**
     * 
     * @return prefix of domain name registered by the reseller
     */
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * 
     * @return account name prefix
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return billing contact nichandle prefix
     */
    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    /**
     * 
     * @return the account id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
