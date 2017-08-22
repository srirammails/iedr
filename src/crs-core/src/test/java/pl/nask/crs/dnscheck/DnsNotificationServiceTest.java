package pl.nask.crs.dnscheck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationServiceTest extends AbstractTest {

    @Autowired
    DnsNotificationService dnsNotificationService;

    @Autowired   
    DnsNotificationDAO dnsNotificationDAO;

    @Test
    public void test() {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals("Pending notifications before test", 4, notifications.size());

        dnsNotificationService.sendNotifications();

        notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals("Pending notifications after sending", 0, notifications.size());
    }
}
