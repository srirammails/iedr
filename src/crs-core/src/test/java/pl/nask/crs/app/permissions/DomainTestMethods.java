package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DomainTestMethods {

    void view();
    void viewNotOwn();
    void viewPlain();
    void viewPlainNotOwn();
    void edit();
    void editNotOwn();
    void save();
    void saveNotOwn();
    void search();
    // domain reports
    void searchFull();
    void findDeletedDomains();
    void checkDomainExists();
    void isEventValid();
    void checkAvailability();
    void forceDSMEvent();
    void forceDSMState();
    void getDsmStates();
    void updateHolderType();
    void enterWipo();
    void exitWipo();
    void revertToBillable();
    void checkPayAvailable();
    void modifyRenewalMode();

    void placeDomainInVoluntaryNRP();
    void removeDomainFromVoluntaryNRP();
    void placeNotOwnDomainInVoluntaryNRP();
    void removeNotOwnDomainFromVoluntaryNRP();

}
