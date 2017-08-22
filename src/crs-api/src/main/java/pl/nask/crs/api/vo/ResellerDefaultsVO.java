package pl.nask.crs.api.vo;

import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaults;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.ArrayList;
import java.util.List;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ResellerDefaultsVO {

    private String nicHandleId;
    private String techContactId;
    private List<String> nameservers;
    private Integer dnsNotificationPeriod;
    private EmailInvoiceFormat emailInvoiceFormat;


    public ResellerDefaultsVO() {}

    public ResellerDefaultsVO(ResellerDefaults resellerDefaults) {
        this.nicHandleId = resellerDefaults.getNicHandleId();
        this.techContactId = resellerDefaults.getTechContactId();
        this.nameservers = new ArrayList<String>(resellerDefaults.getNameservers());
        this.dnsNotificationPeriod = resellerDefaults.getDnsNotificationPeriod();
        this.emailInvoiceFormat = resellerDefaults.getEmailInvoiceFormat();
    }

}
