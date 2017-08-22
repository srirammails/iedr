package pl.nask.crs.defaults;

import pl.nask.crs.commons.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class ResellerDefaults {

    private String nicHandleId;
    private String techContactId;
    private List<String> nameservers = new ArrayList<String>();
    private Integer dnsNotificationPeriod;
    private EmailInvoiceFormat emailInvoiceFormat;

    public ResellerDefaults() {}

    public ResellerDefaults(String nicHandleId, String techContactId, List<String> newNameservers, Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat) {
        this.nicHandleId = nicHandleId;
        this.techContactId = techContactId;
        this.nameservers.addAll(newNameservers);
        this.dnsNotificationPeriod = dnsNotificationPeriod;
        this.emailInvoiceFormat = emailInvoiceFormat;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public String getTechContactId() {
        return techContactId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public void setTechContactId(String techContactId) {
        this.techContactId = techContactId;
    }

    public void setNameservers(List<String> newNameservers) {
        if (newNameservers == null)
            return;
        nameservers.clear();
        nameservers.addAll(newNameservers);
    }

    public Integer getDnsNotificationPeriod() {
        return dnsNotificationPeriod;
    }

    public void setDnsNotificationPeriod(Integer dnsNotificationPeriod) {
        this.dnsNotificationPeriod = dnsNotificationPeriod;
    }

    public EmailInvoiceFormat getEmailInvoiceFormat() {
        return emailInvoiceFormat;
    }

    public void setEmailInvoiceFormat(EmailInvoiceFormat emailInvoiceFormat) {
        this.emailInvoiceFormat = emailInvoiceFormat;
    }

    public List<String> getNameservers() {
        return nameservers;
    }

    @Override
    public String toString() {
        return String.format("ResellerDefaults[nicHandleId=%s, techContact=%s, nameservers=[%s], dnsPeriod=%s, emailInvoiceType=%s]", nicHandleId, techContactId, CollectionUtils.toString(nameservers, ", "), dnsNotificationPeriod, emailInvoiceFormat);
    }
}
