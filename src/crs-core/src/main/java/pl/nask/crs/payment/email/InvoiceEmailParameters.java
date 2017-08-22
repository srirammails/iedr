package pl.nask.crs.payment.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceEmailParameters implements EmailParameters {

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    private NicHandle billingNH;
    private Date invoiceDate;
    private String username;

    public InvoiceEmailParameters(NicHandle billingNH, Date invoiceDate, String username) {
        this.billingNH = billingNH;
        this.invoiceDate = invoiceDate;
        this.username = username;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return billingNH.getNicHandleId();
    }

    public String getDomainName()
    {
        return null; // Invoice is unrelated to a particular domain
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum params = ParameterNameEnum.forName(name);
        switch (params) {
            case BILL_C_NAME:
                return billingNH.getName();
            case BILL_C_CO_NAME:
            	return billingNH.getCompanyName();
            case BILL_C_EMAIL:
                return billingNH.getEmail();
            case INVOICE_DATE:
                return invoiceDate == null ? null : FORMATTER.format(invoiceDate);
            default:
                //throw new IllegalArgumentException("Wrong parameter name: " + params);
                return null;
        }
    }
}
