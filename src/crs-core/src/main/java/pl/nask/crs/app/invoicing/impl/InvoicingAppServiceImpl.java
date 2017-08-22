package pl.nask.crs.app.invoicing.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.app.invoicing.email.InvalidatedInvoiceEmailParams;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoicingAppServiceImpl implements InvoicingAppService {

    private final static Logger LOG = Logger.getLogger(InvoicingAppServiceImpl.class);

    private final ServicesRegistry servicesRegistry;

    private final AppServicesRegistry appServicesRegistry;

    private final InvoicingSupportService invoicingSupportService;

    public InvoicingAppServiceImpl(ServicesRegistry servicesRegistry, AppServicesRegistry appServicesRegistry, InvoicingSupportService invoicingSupportService) {
        this.servicesRegistry = servicesRegistry;
        this.appServicesRegistry = appServicesRegistry;
        this.invoicingSupportService = invoicingSupportService;
    }

    @Override
    public void runInvoicing(AuthenticatedUser user) {
        markTransactionsStartedSettlement();
        settleTransactions(user);
        generateInvoices(user);
    }

    private List<Transaction> getTransactionToMarkSettlementStarted() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(false);
        criteria.setSettlementEnded(false);
        criteria.setReadyForSettlement(true);
        return appServicesRegistry.getPaymentAppService().findAllTransactions(criteria, null);
    }

    private void markTransactionsStartedSettlement() {
        List<Transaction> transactions = getTransactionToMarkSettlementStarted();
        for (Transaction transaction : transactions) {
            setTransactionStartedSettlement(transaction.getId());
        }
    }

    private void setTransactionStartedSettlement(long transactionId) {
        try {
            invoicingSupportService.setTransactionStartedSettlement(transactionId);
        } catch (TransactionNotFoundException e) {
            LOG.error("Transaction not found, transactionId=" + transactionId, e);
        }
    }

    private void settleTransactions(AuthenticatedUser user) {
        List<Transaction> transactions = getSortedTransactions(getTransactionToSettleCriteria());
        for (Transaction transaction : transactions) {
            settleTransaction(user, new OpInfo("Invoicing"), transaction.getId());
        }
    }

    private void settleTransaction(AuthenticatedUser user, OpInfo opInfo, long transactionId) {
        try {
            invoicingSupportService.settleTransaction(user, opInfo, transactionId);
        } catch (Exception e) {
            LOG.error("Exception during transaction settlement occured, transactionId=" + transactionId, e);
        }
    }

    private List<Transaction> getSortedTransactions(TransactionSearchCriteria criteria) {
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("financiallyPassedDate", true));
        return appServicesRegistry.getPaymentAppService().findAllTransactions(criteria, sortBy);
    }

    private TransactionSearchCriteria getTransactionToSettleCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(true);
        criteria.setSettlementEnded(false);
        criteria.setReadyForSettlement(true);
        return criteria;
    }

    private void generateInvoices(AuthenticatedUser user) {
        List<Transaction> transactions = getSortedTransactions(getTransactionsPendingInvoicingCriteria());
        Map<String, List<Transaction>> nhToADPTransactions = new HashMap<String, List<Transaction>>();
        Map<String, List<Transaction>> nhToCCTransactions = new HashMap<String, List<Transaction>>();
        prepareNHToTransactionMaps(transactions, nhToADPTransactions, nhToCCTransactions);
        generateInvoicesForNH(nhToADPTransactions, user);
        generateInvoicesForNH(nhToCCTransactions, user);
    }

    private void generateInvoicesForNH(Map<String, List<Transaction>> nhToTransactions, AuthenticatedUser user) {
        for (Map.Entry<String, List<Transaction>> entry : nhToTransactions.entrySet()) {
            createInvoice(entry.getKey(), entry.getValue(), user);
        }
    }

    private TransactionSearchCriteria getTransactionsPendingInvoicingCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setSettlementEnded(true);
        criteria.setInvoiceAssociated(false);
        return criteria;
    }

    private void prepareNHToTransactionMaps(List<Transaction> transactions, final Map<String, List<Transaction>> ADPMap, final Map<String, List<Transaction>> CCMap) {
        for (Transaction transaction : transactions) {
            List<Transaction> nhTransactions = null;
            if (Validator.isEmpty(transaction.getReservations())) {
            	LOG.warn("Transaction without reservations (skipping), transactionId=" + transaction.getId() );
            	if (transaction.isSettlementEnded()) {
            		throw new IllegalArgumentException("Transaction is settled but cannot add it to the invoice (no reservations)!, transactionId=" + transaction.getId());
            	}
            }
            
            if (transaction.getPaymentMethod() == null) {
            	throw new NullPointerException("PaymentMethod is null in transaction (transactionId=" + transaction.getId());
            } else {
            	switch (transaction.getPaymentMethod()) {
            	case ADP:
            		nhTransactions = ADPMap.get(transaction.getBillNicHandleId());
            		if (nhTransactions == null) {
            			nhTransactions = new ArrayList<Transaction>();
            			nhTransactions.add(transaction);
            			ADPMap.put(transaction.getBillNicHandleId(), nhTransactions);
            		} else {
            			nhTransactions.add(transaction);
            		}
            		break;
            	case CC:
            		nhTransactions = CCMap.get(transaction.getBillNicHandleId());
            		if (nhTransactions == null) {
            			nhTransactions = new ArrayList<Transaction>();
            			nhTransactions.add(transaction);
            			CCMap.put(transaction.getBillNicHandleId(), nhTransactions);
            		} else {
            			nhTransactions.add(transaction);
            		}
            		break;
            	default:
            		throw new IllegalArgumentException("Invalid payment method: " + transaction.getPaymentMethod() + ", transactionId=" + transaction.getId());
            	}
            }
        }
    }

    private void createInvoice(String nicHandleId, List<Transaction> transactions, AuthenticatedUser user) {
        try {
            invoicingSupportService.generateInvoice(nicHandleId, transactions, user);
        } catch (Exception e) {
            LOG.error("Exception during invoice creating occured, nicHandleId=" + nicHandleId, e);
        }
    }

    @Override
    public void runTransactionInvalidation(AuthenticatedUser user) {
        List<Transaction> regXferTransactions = appServicesRegistry.getPaymentAppService().findAllTransactions(getTransactionToInvalidateCriteria(), null);
        Map<String, List<String>> nhToNotifyWithDomains = new HashMap<String, List<String>>();
        for (Transaction transaction : regXferTransactions) {
            if (invalidateTransactionIfNeeded(user, transaction.getId())) {
                prepareNHToNotifyWithDomainsMap(transaction, nhToNotifyWithDomains);
            }
        }
        sendNotificationEmails(nhToNotifyWithDomains, user);
    }

    private void prepareNHToNotifyWithDomainsMap(Transaction transaction, final Map<String, List<String>> map) {
        List<String> nhDomains = map.get(transaction.getBillNicHandleId());
        if (nhDomains == null) {
            nhDomains = new ArrayList<String>();
            for (Reservation reservation : transaction.getReservations()) {
                nhDomains.add(reservation.getDomainName());
            }
            map.put(transaction.getBillNicHandleId(), nhDomains);
        } else {
            for (Reservation reservation : transaction.getReservations()) {
                nhDomains.add(reservation.getDomainName());
            }
        }
    }

    private boolean invalidateTransactionIfNeeded(AuthenticatedUser user, long transactionId) {
        try {
            return invoicingSupportService.invalidateTransactionIfNeeded(user, transactionId);
        } catch (Exception e) {
            LOG.error("Exception during transaction invalidation, transactionId=" + transactionId, e);
        }
        return false;
    }

    private TransactionSearchCriteria getTransactionToInvalidateCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setCancelled(false);
        criteria.setSettlementStarted(false);
        criteria.setSettlementEnded(false);
        // imply that only registration,transfer transaction are selected (renewal transaction are never notReadyForSettlement)
        criteria.setReadyForSettlement(false);
        return criteria;
    }

    private void sendNotificationEmails(Map<String, List<String>> nhToNotifyWithDomains, AuthenticatedUser user) {
        try {
            for (Map.Entry<String, List<String>> entry : nhToNotifyWithDomains.entrySet()) {
                String username = (user == null) ? null : user.getUsername();
                NicHandle nicHandle = servicesRegistry.getNicHandleSearchService().getNicHandle(entry.getKey());
                InvalidatedInvoiceEmailParams params = new InvalidatedInvoiceEmailParams(nicHandle, entry.getValue(), username);
                servicesRegistry.getEmailTemplateSender().sendEmail(EmailTemplateNamesEnum.INVALIDATED_TRANSACTION.getId(), params);
            }
        } catch (Exception e) {
            LOG.warn("Problem with notification email occured.", e);
        }
    }
}
