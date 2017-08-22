package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIsAccountBillingContactException extends NicHandleException {

    private String nicHandleId;
    private Long accountId;

    public NicHandleIsAccountBillingContactException(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsAccountBillingContactException(String message, String nicHandleId) {
        super(message);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsAccountBillingContactException(String message, Throwable cause, String nicHandleId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsAccountBillingContactException(Throwable cause, String nicHandleId) {
        super(cause);
        this.nicHandleId = nicHandleId;
    }

    public NicHandleIsAccountBillingContactException(String nicHandleId, Long accountId) {
        this.nicHandleId = nicHandleId;
        this.accountId = accountId;
    }

    public NicHandleIsAccountBillingContactException(String message, String nicHandleId, Long accountId) {
        super(message);
        this.nicHandleId = nicHandleId;
        this.accountId = accountId;
    }

    public NicHandleIsAccountBillingContactException(String message, Throwable cause, String nicHandleId, Long accountId) {
        super(message, cause);
        this.nicHandleId = nicHandleId;
        this.accountId = accountId;
    }

    public NicHandleIsAccountBillingContactException(Throwable cause, String nicHandleId, Long accountId) {
        super(cause);
        this.nicHandleId = nicHandleId;
        this.accountId = accountId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
