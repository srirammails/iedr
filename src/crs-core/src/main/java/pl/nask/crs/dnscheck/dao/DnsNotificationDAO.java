package pl.nask.crs.dnscheck.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DnsNotificationDAO extends GenericDAO<DnsNotification, Integer>{

    List<DnsNotification> getByDomainName(String domainName);

    List<DnsNotification> getByNH(String nicHandleId);

    List<DnsNotification> getAll();

    DnsNotificationDate getNotificationDate(String nicHandleId);

    void createNotificatioDate(DnsNotificationDate notificationDate);

    void update(DnsNotificationDate notificationDate);

    /**
     * Deletes all notifications for given domain except for those about passed nameservers
     * @param domainName domain name for which notifications should be deleted
     * @param nsNames List of nameservers for which notifications should be kept
     */
    void deleteAllNotificationsExceptFor(String domainName, List<String> nsNames);
}
