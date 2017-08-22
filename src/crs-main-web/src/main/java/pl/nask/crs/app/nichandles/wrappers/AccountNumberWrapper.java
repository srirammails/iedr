package pl.nask.crs.app.nichandles.wrappers;

/**
 * @author Marianna Mysiorska
 */
public class AccountNumberWrapper {

    Long accountNumber;

    public AccountNumberWrapper(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
