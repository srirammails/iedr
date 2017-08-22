package pl.nask.crs.dnscheck;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import static pl.nask.crs.commons.utils.CollectionUtils.forAll;
import static pl.nask.crs.commons.utils.CollectionUtils.exists;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationDAOTest extends AbstractTest {

    @Resource
    DnsNotificationDAO dnsNotificationDAO;

    @Test
    public void createTest() {
        DnsNotification dnsNotification = new DnsNotification("APITEST-IEDR", "email@q.q", "xdomain.ie", "ns1.ie", "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(dnsNotification);

        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName("xdomain.ie");
        AssertJUnit.assertEquals(1, notifications.size());

        DnsNotification n =  notifications.get(0);
        AssertJUnit.assertNotNull(n);
        AssertJUnit.assertEquals("APITEST-IEDR", n.getNicHandleId());
        AssertJUnit.assertEquals("email@q.q", n.getEmail());
        AssertJUnit.assertEquals("xdomain.ie", n.getDomainName());
        AssertJUnit.assertEquals("ns1.ie", n.getNsName());
        AssertJUnit.assertEquals("error message 1!1!1", n.getCheckOutput());
        AssertJUnit.assertEquals("11:12", n.getTimeOfCheck());
        AssertJUnit.assertEquals("[11:12]\nerror message 1!1!1", n.getErrorMessage());
    }

    @Test
    public void getByNHTest() {
        List<DnsNotification> notifications = dnsNotificationDAO.getByNH("NH1-IEDR");
        AssertJUnit.assertEquals(2, notifications.size());
    }

    @Test
    public void getAllTest() {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals(4, notifications.size());
    }

    @Test
    public void deleteTest() {
        DnsNotification notification = dnsNotificationDAO.getByDomainName("domain3.ie").get(0);
        dnsNotificationDAO.deleteById(notification.getId());
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals(3, notifications.size());
    }

    @Test
    public void notificationDateTest() {
        DnsNotificationDate notificationDate = dnsNotificationDAO.getNotificationDate("NH2-IEDR");
        Date newDate = DateUtils.endOfDay(new Date());
        notificationDate.setNextNotificationDate(newDate);
        dnsNotificationDAO.update(notificationDate);
        DnsNotificationDate updatedConfig = dnsNotificationDAO.getNotificationDate("NH2-IEDR");
        AssertJUnit.assertEquals(org.apache.commons.lang.time.DateUtils.truncate(newDate, Calendar.SECOND), updatedConfig.getNextNotificationDate());

        notificationDate = dnsNotificationDAO.getNotificationDate("X-IEDR");
        AssertJUnit.assertNull(notificationDate);
        notificationDate = new DnsNotificationDate("X-IEDR", new Date());
        AssertJUnit.assertEquals("X-IEDR", notificationDate.getNicHandle());
        Assert.assertNotNull(notificationDate.getNextNotificationDate());
    }

    @Test
    public void createNotificationDateTest() throws Exception {
        DnsNotificationDate newNotificationDate = new DnsNotificationDate("TEST", new Date());
        dnsNotificationDAO.createNotificatioDate(newNotificationDate);
        DnsNotificationDate fromDB = dnsNotificationDAO.getNotificationDate("TEST");
        Assert.assertNotNull(fromDB);
    }

    @Test
    public void deleteAllForDomainWithEmptyNameserverListTest() {
        final String domainName = "xdomain.ie";
        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(), "No notification should be present at start of test");

        DnsNotification notif1 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, "ns1.ie", "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif1);
        DnsNotification notif2 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, "ns2.ie", "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif2);
        DnsNotification notif3 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, "ns3.ie", "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif3);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 3, "Should list all three notifications");

        dnsNotificationDAO.deleteAllNotificationsExceptFor(domainName, Collections.<String>emptyList());

        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(), "Should be empty after all notifications were deleted");
    }

    @Test
    public void deleteAllForDomainWithNameserverListTest() {
        final String domainName = "xdomain.ie";
        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(), "No notification should be present at start of test");

        final String ns1Name = "ns1.ie";
        DnsNotification notif1 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, ns1Name, "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif1);
        final String ns2Name = "ns2.ie";
        DnsNotification notif2 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, ns2Name, "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif2);
        final String ns3Name = "ns3.ie";
        DnsNotification notif3 = new DnsNotification("APITEST-IEDR", "email@q.q", domainName, ns3Name, "11:12", "error message 1!1!1");
        dnsNotificationDAO.create(notif3);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 3, "Should list all three notifications");

        dnsNotificationDAO.deleteAllNotificationsExceptFor(domainName, Arrays.asList(ns1Name, ns2Name));

        final List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        Assert.assertEquals(notifications.size(), 2, "Should still contain notifications for ns1 and ns2");

        Assert.assertTrue(exists(notifications, NotificationPredicate.nsName(ns1Name)), "ns1 should still be present in result");
        Assert.assertTrue(exists(notifications, NotificationPredicate.nsName(ns2Name)), "ns2 should still be present in result");
        Assert.assertFalse(exists(notifications, NotificationPredicate.nsName(ns3Name)), "ns3 should no longer be present in result");
        Assert.assertTrue(forAll(notifications, NotificationPredicate.domainName(domainName)), "All should be about the same domain");
    }

    private static class NotificationPredicate {
        static CollectionUtils.Predicate<DnsNotification> nsName(final String ns) {
            return new CollectionUtils.Predicate<DnsNotification>() {
                @Override
                public boolean test(DnsNotification elem) {
                    return elem.getNsName().equals(ns);
                }
            };
        }
        static CollectionUtils.Predicate<DnsNotification> domainName(final String domainName) {
            return new CollectionUtils.Predicate<DnsNotification>() {
                @Override
                public boolean test(DnsNotification elem) {
                    return elem.getDomainName().equals(domainName);
                }
            };
        }
    }
}
