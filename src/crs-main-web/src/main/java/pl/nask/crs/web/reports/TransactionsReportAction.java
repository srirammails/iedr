package pl.nask.crs.web.reports;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.TransactionInfo;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.Collections;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionsReportAction extends AuthenticatedUserAwareAction {

    private PaymentAppService paymentAppService;

    private String nicHandleId;

    public TransactionsReportAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public List<TransactionInfo> getReports() {
        try {
            return paymentAppService.getReadyADPTransactionsReport(getUser(), nicHandleId);
        } catch (DepositNotFoundException e) {
            addActionError("ERROR: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
