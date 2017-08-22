package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface BulkTransferTestMethods {

    void createBulkTransferProcess();
    void addDomains();
    void findTransfers();
    void getTransferRequest();
    void removeDomain();
    void closeTransferRequest();
    void forceCloseTransferRequest();
    void transferAll();
    void transferValid();
}
