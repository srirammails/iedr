package pl.nask.crs.price.email;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class PricingEmailParameters implements EmailParameters {

    private final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private final DomainPrice domainPrice;
    private  String username;

    public PricingEmailParameters(DomainPrice domainPrice, String username) {
        this.domainPrice = domainPrice;
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
        return null; // Pricing unrelated to a particular domain
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case PRODUCT_TABLE:
                return getFormattedPrice();
            case PRODUCT_VALID_FROM:
            	return sf.format(domainPrice.getValidFrom());
            default:
                return null;
        }
    }

    private String getFormattedPrice() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("Product id: ").append(domainPrice.getId()).append("\n");
        sb.append("Description: ").append(domainPrice.getDescription()).append("\n");
        sb.append("Price: ").append(domainPrice.getPrice()).append("\n");
        sb.append("Duration: ").append(domainPrice.getDuration()).append("\n");
        sb.append("Valid from: ").append(sf.format(domainPrice.getValidFrom())).append("\n");
        sb.append("Valid to: ").append(sf.format(domainPrice.getValidTo())).append("\n");
        sb.append("For registration: ").append(domainPrice.isForRegistration()).append("\n");
        sb.append("For renewal: ").append(domainPrice.isForRenewal()).append("\n");
        sb.append("For direct: ").append(domainPrice.isDirect()).append("\n");
        return sb.toString();
    }
}
