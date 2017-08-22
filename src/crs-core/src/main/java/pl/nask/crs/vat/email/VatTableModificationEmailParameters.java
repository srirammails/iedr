package pl.nask.crs.vat.email;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.vat.Vat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class VatTableModificationEmailParameters implements EmailParameters {

    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private final Vat vat;
    private String username;

    public VatTableModificationEmailParameters(Vat vat, String username) {
        this.vat = vat;
        this.username = username;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return null;
    }

    public String getDomainName()
    {
        return null; // Vat payment unrelated to a particular domain
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case VAT_TABLE_CHANGE_DETAIL:
                return getFormattedVat();
            default:
                return null;
        }
    }

    private String getFormattedVat() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("VAT Category: ").append(vat.getCategory()).append("\n");
        sb.append("New VAT rate: ").append(MoneyUtils.getRoudedAndScaledValue(BigDecimal.valueOf(vat.getVatRate() * 100), 0)).append("%\n");
        sb.append("Valid from: ").append(sf.format(vat.getFromDate())).append("\n");
        return sb.toString();
    }


}
