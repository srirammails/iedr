package pl.nask.crs.app.triplepass.email;

import java.math.BigDecimal;
import java.util.List;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class FinanacialCheckEmailParameters implements EmailParameters {

    private NicHandle billingNH;
    private String domainName;
    private DomainOperation.DomainOperationType operationType;
    private TransactionDetails transactionDetails;
    private BigDecimal transactionValue;
    private String username;

    public FinanacialCheckEmailParameters(String username, NicHandle billingNH, String domainName, DomainOperation.DomainOperationType operationType, TransactionDetails transactionDetails, BigDecimal transactionValue) {
        this.billingNH = billingNH;
        this.domainName = domainName;
        this.username = username;
        this.operationType = operationType;
        this.transactionDetails = transactionDetails;
        this.transactionValue = MoneyUtils.getRoudedAndScaledValue(transactionValue);
    }

    public String getLoggedInNicHandle()
    {
        return this.username;
    }

    public String getAccountRelatedNicHandle()
    {
        return this.billingNH.getNicHandleId();
    }

    public String getDomainName()
    {
        return this.domainName; 
    }


    @Override
    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameter = ParameterNameEnum.forName(name);
        switch (parameter) {
            case BILL_C_NAME:
                return billingNH.getName();
            case BILL_C_NIC:
                return billingNH.getNicHandleId();
            case DOMAIN:
                return domainName;
            case BILL_C_EMAIL:
                return billingNH.getEmail();
            case TRANSACTION_DETAIL:
                return html ? transactionDetails.toHtmlString() : transactionDetails.toString();
            case TRANSACTION_VALUE:
                return MoneyUtils.getRoudedAndScaledValue(transactionValue, 2).toString();
            case TICKET_TYPE:
            	return operationType.getFullName();
            default:
                return null;
        }
    }
}
