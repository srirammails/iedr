package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TestMethods extends PaymentTestMethods, CommonTestMethods, BulkTransferTestMethods, DomainTestMethods, TicketTestMethods, AccountTestMethods, NicHandleTestMethods {

    void findTotalDomains();
    void findTotalDomainsPerDate();
    void findTotalDomainsPerClass();

    void getEntries();
    void updateEntry();
    void createEntry();
    void getEntry();
}
