package pl.nask.crs.web.reports;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.DomainInfo;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceInfoAction extends AuthenticatedUserAwareAction {

    private PaymentAppService paymentAppService;

    private String invoiceNumber;

    public InvoiceInfoAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<DomainInfo> getInvoiceInfos() {
        return paymentAppService.getInvoiceInfo(getUser(), invoiceNumber);
    }
}
