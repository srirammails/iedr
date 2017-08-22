package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;


/**
 * @author: Marcin Tkaczyk
 */
public class OldPaymentEmailParameters {

    private Map<Domain, BigDecimal> domains;
    private String nicHandleName;
    private String nicHandleId;
    private BigDecimal amount;
    private String orderId;
    private String country;
    private String bank;
    private String subject;
    private String email;
    private String bcc;
    private Map<String, Date> tranDates;
    private String accountName;
    private BigDecimal closeBalance;

    public OldPaymentEmailParameters(Map<Domain, BigDecimal> domains, NicHandle nh, BigDecimal amount, String orderId, String country, String bank, String subject, String bcc, Map<String, Date> tranDates) {
        this.domains = domains;
        this.nicHandleName = nh.getName();
        this.nicHandleId = nh.getNicHandleId();
        this.amount = amount;
        this.orderId = orderId;
        this.country = country;
        this.bank = bank;
        this.subject = subject;
        this.email = nh.getEmail();
        this.bcc = bcc;
        this.tranDates = tranDates;
    }

    public OldPaymentEmailParameters(NicHandle nicHandle, String orderId, BigDecimal amount, BigDecimal closeBalance, String subject) {
        this.nicHandleId = nicHandle.getNicHandleId();
        this.amount = amount;
        this.orderId = orderId;
        this.email = nicHandle.getEmail();
        this.accountName = nicHandle.getAccount().getName();
        this.closeBalance = closeBalance;
        this.subject = subject;
    }

    public String getNicHandleName() {
        return nicHandleName;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCountry() {
        return country;
    }

    public String getBank() {
        return bank;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmail() {
        return email;
    }

    public String getBcc() {
        return bcc;
    }

    public Map<Domain, BigDecimal> getDomains() {
        return domains;
    }

    public Map<String, Date> getTranDates() {
        return tranDates;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getCloseBalance() {
        return closeBalance;
    }
}
