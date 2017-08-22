package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface PaymentTestMethods {

    void pay();

    void accessXmlInvoice();
    void accessPdfInvoice();
    void accessNotOwnInvoice();
    void sendEmailWithInvoices();
    void sendEmailWithNotOwnInvoices();
    void getInvoiceInfo();
    void getNotOwnInvoiceInfo();

    void viewUserDeposit();
    void getDepositLimits();
    void topUpDeposit();
    void getTopUpHistory();
    void findUserHistoricalDeposits();

    void getNotSettledReservations();
    void getNotSettledReservationsTotals();
    void findHistoricalReservations();
    void getSettledTransactionHistory();
    void getTransactionToReauthorise();
    void findHistoricalTransactions();
    void findUserInvoices();

    void getProductPrice();
    void getDomainPricing();
    void getVatRate();

    void getPrice();
    void findAllPrices();
    void addPrice();
    void modifyPrice();
    void addVatRate();
    void invalidateVat();
    void getValidVat();

    //reports
    void findDeposits();
    void findDepositWithHistory();
    void getReadyADPTransactionsReport();
    void findInvoices();

    //manage deposits
    void viewDeposit();
    void correctDeposit();
    void depositFundsOffline();
    
    void getTransactionInfoById();
    void getTransactionInfoByOrderId();

}
