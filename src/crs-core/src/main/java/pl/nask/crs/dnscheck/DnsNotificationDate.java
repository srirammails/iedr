package pl.nask.crs.dnscheck;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationDate {

    private String nicHandle;

    private Date nextNotificationDate;

    public DnsNotificationDate() {}

    public DnsNotificationDate(String nicHandle, Date nextNotificationDate) {
        this.nicHandle = nicHandle;
        this.nextNotificationDate = nextNotificationDate;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Date getNextNotificationDate() {
        return nextNotificationDate;
    }

    public void setNextNotificationDate(Date nextNotificationDate) {
        this.nextNotificationDate = nextNotificationDate;
    }
}
