package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface CommonTestMethods {

    void registrationRequest();
    void transferRequest();
    void registerGIBODomain();
    void isTransferPossible();

    void cancelTicket();
    void cancelNotOwnTicket();

    void modifyDomain();
    void modifyDomainNameservers();
    void modifyNotOwnDomain();
    void modifyNotOwnDomainNameservers();

    void reauthoriseCCTransaction();

    void zoneCommit();
    void zonePublished();
    void zoneUnpublished();
}
