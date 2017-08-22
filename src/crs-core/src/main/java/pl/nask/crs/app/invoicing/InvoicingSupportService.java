package pl.nask.crs.app.invoicing;

import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
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
public interface InvoicingSupportService {

    void setTransactionStartedSettlement(long transactionId) throws TransactionNotFoundException;

    void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) throws TransactionNotFoundException, TransactionInvalidStateForSettlement;

    void generateInvoice(String nicHandleId, List<Transaction> transactions, AuthenticatedUser user) throws NicHandleNotFoundException, TransactionNotFoundException, InvoiceNotFoundException, ExportException;

    boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId) throws TransactionNotFoundException, NotAdmissiblePeriodException, PaymentException, NicHandleNotFoundException;
}
