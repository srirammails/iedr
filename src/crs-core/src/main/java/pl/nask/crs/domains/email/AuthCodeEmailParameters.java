package pl.nask.crs.domains.email;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;

public class AuthCodeEmailParameters implements EmailParameters {
    private String username;
    private Domain domain;
    private String emailAddress;

    public AuthCodeEmailParameters(String username, Domain domain) {
        this.username = username;
        this.domain = domain;
    }

    public AuthCodeEmailParameters(String username, Domain domain, String emailAddress) {
        this(username, domain);
        this.emailAddress = emailAddress;
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.DOMAIN,
            ParameterNameEnum.ADMIN_C_EMAIL,
            ParameterNameEnum.BILL_C_EMAIL,
            ParameterNameEnum.NIC_EMAIL,
            ParameterNameEnum.AUTHCODE,
            ParameterNameEnum.AUTHCODE_EXP_DATE);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param){
            case DOMAIN:
                return domain.getName();
            case AUTHCODE:
                return domain.getAuthCode();
            case AUTHCODE_EXP_DATE:
                Date expDate = domain.getAuthCodeExpirationDate();
                if (expDate == null) {
                    return "";
                }
                return DateFormatUtils.format(domain.getAuthCodeExpirationDate(), "dd-MM-yyyy");
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case ADMIN_C_EMAIL:
                return domain.getFirstAdminContact().getEmail();
            case NIC_EMAIL:
                return emailAddress;
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
        return domain.getBillingContactNic();
    }

    @Override
    public String getDomainName() {
        return domain.getName();
    }

}
