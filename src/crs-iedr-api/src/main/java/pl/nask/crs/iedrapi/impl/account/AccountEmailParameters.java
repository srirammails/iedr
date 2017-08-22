package pl.nask.crs.iedrapi.impl.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.nask.crs.api.vo.DomainVO;
import pl.nask.crs.api.vo.NicHandleVO;


/**
 * @author: Marcin Tkaczyk
 */
public class AccountEmailParameters {

    private List<DomainVO> domains;
    private String name;
    private String handler;
    private Float amount;
    private String orderId;
    private String country;
    private String bank;
    private String subject;
    private String email;
    private String bcc;

    private Map<String, Date> tranDates;
  
    public AccountEmailParameters(List<DomainVO> domains, NicHandleVO nh, Float amount, String orderId, String country, String bank, String subject, String bcc, Map<String, Date> tranDates) {
        this.domains = domains;
        this.name = nh.getName();
        this.handler = nh.getNicHandleId();
        this.amount = amount;
        this.orderId = orderId;
        this.country = country;
        this.bank = bank;
        this.subject = subject;
        this.email = nh.getEmail();
        this.bcc = bcc;
        this.tranDates = tranDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public List<DomainVO> getDomains() {
        return domains;
    }

    public void setDomains(List<DomainVO> domains) {
        this.domains = domains;
    }

    public Map<String, Date> getTranDates() {
        return tranDates;
    }

    public void setTranDates(Map<String, Date> tranDates) {
        this.tranDates = tranDates;
    }
}
