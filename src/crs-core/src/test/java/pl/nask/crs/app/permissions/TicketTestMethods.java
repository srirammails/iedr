package pl.nask.crs.app.permissions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TicketTestMethods {

    void viewTicket();
    void viewNotOwnTicket();
    void historyTicket();
    void historyNotOwnTicket();
    void reviseTicket();
    void reviseNotOwnTicket();
    void editTicket();
    void editNotOwnTicket();

    void checkOutTicket();
    void checkInTicket();
    void alterStatusTicket();
    void reassignTicket();
    void acceptTicket();
    void acceptTicket2();
    void rejectTicket();
    void rejectTicket2();

    void updateTicket();
    void updateNotOwnTicket();
    void updateTicket2();
    void updateNotOwnTicket2();
    void simpleUpdateTicket();
    void simpleUpdateNotOwnTicket();
}
