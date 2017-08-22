package pl.nask.crs.app.invoicing.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoicingSupportServiceImpl implements InvoicingSupportService {

    private final static Logger LOG = Logger.getLogger(InvoicingSupportServiceImpl.class);
    private final ServicesRegistry servicesRegistry;
    private InvoiceExporter exportersChain;

    public InvoicingSupportServiceImpl(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

    public void setExporterChain(InvoiceExporter exportersChain) {
        this.exportersChain = exportersChain;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException {
        servicesRegistry.getPaymentService().setTransactionStartedSettlement(transactionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) throws TransactionNotFoundException, TransactionInvalidStateForSettlement {
        servicesRegistry.getPaymentService().settleTransaction(user, opInfo, transactionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateInvoice(String nicHandleId, List<Transaction> transactions, AuthenticatedUser user) throws NicHandleNotFoundException,
            TransactionNotFoundException, InvoiceNotFoundException, ExportException {
    	LOG.info("Create db invoice for NicHandle=" + nicHandleId);
        int invoiceId = servicesRegistry.getPaymentService().createInvoiceAndAssociateWithTransactions(nicHandleId, transactions);
        LOG.info("db invoice created for NicHandle=" + nicHandleId + ", invoiceId=" + invoiceId);
        Invoice invoice = servicesRegistry.getPaymentService().getInvoice(invoiceId);
        String invoiceNumber = invoice.getInvoiceNumber();
        LOG.info("Exporting invoice, number=" + invoiceNumber + " ,id=" + invoiceId);
        exportersChain.export(invoice);
        LOG.info("Exporting invoice, number=" + invoiceNumber + " ,id=" + invoiceId + " completed.");
        LOG.info("Updating export info in the db invoice, number=" + invoiceNumber +", id=" + invoiceId);
        servicesRegistry.getPaymentService().updateInvoice(invoice);
        LOG.info("Sending invoice summary email");
        servicesRegistry.getPaymentService().sendInvoicingSummaryEmail(invoiceNumber, user);
        LOG.info("Finished generating invoice number=" + invoiceNumber + ", id=" + invoiceId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException {
        return servicesRegistry.getPaymentService().invalidateTransactionsIfNeeded(user, transactionId);
    }
}
