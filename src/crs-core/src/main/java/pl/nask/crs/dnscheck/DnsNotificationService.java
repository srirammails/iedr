package pl.nask.crs.dnscheck;

import java.util.Set;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DnsNotificationService {

    void createNotification(DnsNotification dnsNotification);

    void sendNotifications();

    /**
     * Removes all previous notifications about checks of given domain except about passed nameservers
     * @param domainName domain that was checked
     * @param failedNameservers Set of nameservers which are still failing and should be reported
     */
    void removeAllNotificationsExceptFor(String domainName, Set<String> failedNameservers);
}
