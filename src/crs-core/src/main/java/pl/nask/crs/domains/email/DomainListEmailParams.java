package pl.nask.crs.domains.email;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.StringUtils;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;

public class DomainListEmailParams implements EmailParameters {
    private String username;
    private Contact losingRegistrar;
    private Contact adminC;
    private List<Domain> domainList;

    public DomainListEmailParams(String username, Contact losingRegistrar, Contact adminC, List<Domain> domainList) {
        this.username = username;
        this.losingRegistrar = losingRegistrar;
        this.adminC = adminC;
        this.domainList = domainList;
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.DOMAIN_LIST,
                ParameterNameEnum.DOMAIN_TABLE_WITH_AUTHCODES,
                ParameterNameEnum.ADMIN_C_EMAIL,
                ParameterNameEnum.BILL_C_EMAIL,
                ParameterNameEnum.AUTHCODE_EXP_DATE);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param){
            case DOMAIN_LIST:
                return formatDomainList();
            case DOMAIN_TABLE_WITH_AUTHCODES:
                return formatTablewithAuthcodes();
            case BILL_C_EMAIL:
                return losingRegistrar.getEmail();
            case ADMIN_C_EMAIL:
                return adminC.getEmail();
            case AUTHCODE_EXP_DATE:
                return DateFormatUtils.format(getExpirationDate(), "dd-MM-yyyy");
            default:
                return null;
        }
    }

    @Override
    public String getLoggedInNicHandle() {
        return username;
    }

    @Override
    public String getAccountRelatedNicHandle() {
        return losingRegistrar.getName();
    }

    @Override
    public String getDomainName() {
        return null; // email is related to a domain list
    }

    private String formatDomainList() {
        return StringUtils.join(domainList, ", ");
    }

    private String formatTablewithAuthcodes() {
        TableFormatter formatter = new TableFormatter(Locale.getDefault());
        formatter.addColumn("Domain Name", 60, TableFormatter.leftAlignedStringFormat(60), true);
        formatter.addColumn("Authcode", 60, TableFormatter.leftAlignedStringFormat(60), true);
        for (Domain domain: domainList) {
            formatter.addDataLine(new Object[] {domain.getName(), domain.getAuthCode()});
        }
        return formatter.toString();
    }

    // For bulk export email (E_ID = 176) all the domains should share the same authcode expiration date
    private Date getExpirationDate() {
        return domainList.get(0).getAuthCodeExpirationDate();
    }

}
