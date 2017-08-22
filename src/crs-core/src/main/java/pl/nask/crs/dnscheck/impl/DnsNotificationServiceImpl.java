package pl.nask.crs.dnscheck.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import pl.nask.crs.dnscheck.email.DnsNotificationEmailParameters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationServiceImpl implements DnsNotificationService {

    private final static Logger LOG = Logger.getLogger(DnsNotificationServiceImpl.class);

    private DnsNotificationDAO dnsNotificationDAO;

    private EmailTemplateSender emailTemplateSender;

    private ResellerDefaultsService defaultsService;

    private EmailDisablerDAO disablerDAO;

    public DnsNotificationServiceImpl(DnsNotificationDAO dnsNotificationDAO, EmailTemplateSender emailTemplateSender, ResellerDefaultsService defaultsService, EmailDisablerDAO disablerDAO) {
        Validator.assertNotNull(dnsNotificationDAO, "dnsNotificationDAO");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(defaultsService, "defaultsService");
        this.dnsNotificationDAO = dnsNotificationDAO;
        this.emailTemplateSender = emailTemplateSender;
        this.defaultsService = defaultsService;
        this.disablerDAO = disablerDAO;
    }

    @Override
    public void createNotification(DnsNotification dnsNotification) {
        dnsNotificationDAO.create(dnsNotification);
    }

    @Override
    public void sendNotifications() {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        if (notifications.size() == 0) {
            LOG.info("No notifications found.");
            return;
        }
        // domain => notifications
        Map<String, Map<String, SortedSet<DnsNotification>>> nhToNotifications = prepareNhToNotificationsMap(notifications);
        for (Map.Entry<String, Map<String, SortedSet<DnsNotification>>> entry : nhToNotifications.entrySet()) {
            sendAndRemove(entry);
        }
    }

    @Override
    public void removeAllNotificationsExceptFor(String domainName, Set<String> failedNameservers) {
        dnsNotificationDAO.deleteAllNotificationsExceptFor(domainName, new ArrayList<String>(failedNameservers));
    }

    private String prepareMessage(Map<String, SortedSet<DnsNotification>> notifications) {
        StringBuilder message = new StringBuilder();
        for (String nsName : notifications.keySet()) {
            SortedSet<DnsNotification> notifs = notifications.get(nsName);
            List<String> failTimes = new ArrayList<String>();
            for (DnsNotification n : notifs) {
                final String timeOfCheck = n.getTimeOfCheck();
                if (timeOfCheck != null)
                    failTimes.add(timeOfCheck);
            }
            message.append("\nFor nameserver ")
                    .append(nsName);
            if (!failTimes.isEmpty()) {
                message.append(" at ")
                        .append(StringUtils.join(failTimes, ", "));
            }
            message.append(". The most recent error messages are\n")
                    .append(notifs.last().getCheckOutput())
                    .append("\n");
        }
        return message.toString();
    }

    /**
     * Creates a map of form
     * <pre>
     *     domainName_1 => {
     *         nameserver_1 => list of notifications sorted by time
     *         nameserver_2 => list of notifications sorted by time
     *         ...
     *     }
     *     domainName_2 ...
     *     ...
     * </pre>
     * @param notifications - flat list of notifications
     * @return
     */
    private Map<String, Map<String, SortedSet<DnsNotification>>> prepareNhToNotificationsMap(List<DnsNotification> notifications) {
        Map<String, Map<String, SortedSet<DnsNotification>>> ret = new HashMap<String, Map<String, SortedSet<DnsNotification>>>();

        Comparator<DnsNotification> dnsNotificationComparator = new Comparator<DnsNotification>() {
            @Override
            public int compare(DnsNotification o1, DnsNotification o2) {
                String t1 = o1.getTimeOfCheck();
                String t2 = o2.getTimeOfCheck();
                if (t1 == t2)
                    return 0;
                else if (t1 == null)
                    return 1;
                else if (t2 == null)
                    return -1;
                else try {
                    DateFormat f = new SimpleDateFormat("HH:mm");
                    return f.parse(t1).compareTo(f.parse(t2));
                } catch (ParseException e) {
                    return 0;
                }
            }
        };
        for (DnsNotification notification : notifications) {
            final String domainName = notification.getDomainName();
            if (!ret.containsKey(domainName)) {
                ret.put(domainName, new HashMap<String, SortedSet<DnsNotification>>());
            }
            final String nsName = notification.getNsName();
            if (!ret.get(domainName).containsKey(nsName)) {
                ret.get(domainName).put(nsName, new TreeSet<DnsNotification>(dnsNotificationComparator));
            }
            ret.get(domainName).get(nsName).add(notification);
        }

        return ret;
    }

    private boolean isEligibleToSend(String nicHandleId) {
        try {
            ResellerDefaults resellerDefaults = defaultsService.get(nicHandleId);
            if (resellerDefaults.getDnsNotificationPeriod() == null) {
                return true;
            } else {
                DnsNotificationDate notificationDate = dnsNotificationDAO.getNotificationDate(nicHandleId);
                if (notificationDate == null) {
                    createNextNotificationDate(nicHandleId, resellerDefaults.getDnsNotificationPeriod());
                    return true;
                } else {
                    if (notificationDate.getNextNotificationDate().before(new Date())) {
                        updateNextNotificationDate(notificationDate, resellerDefaults.getDnsNotificationPeriod());
                        return true;
                    }
                }
            }
            return false;
        } catch (DefaultsNotFoundException e) {
            return true;
        }
    }

    private void createNextNotificationDate(String nicHandleId, int period) {
        DnsNotificationDate notificationDate = new DnsNotificationDate(nicHandleId, new Date());
        prepareNextNotificationDate(notificationDate, period);
        dnsNotificationDAO.createNotificatioDate(notificationDate);
    }

    private void prepareNextNotificationDate(DnsNotificationDate notificationDate, int period) {
        Date nextNotificationDate = DateUtils.getCurrDate(period);
        nextNotificationDate = DateUtils.startOfDay(nextNotificationDate);
        notificationDate.setNextNotificationDate(nextNotificationDate);
    }

    private void updateNextNotificationDate(DnsNotificationDate notificationDate, int period) {
        prepareNextNotificationDate(notificationDate, period);
        dnsNotificationDAO.update(notificationDate);
    }

    private void sendAndRemove(Map.Entry<String, Map<String, SortedSet<DnsNotification>>> entry) {
        try {
            String domain = entry.getKey();
            String any_nameserver = entry.getValue().keySet().iterator().next();
            DnsNotification any_notification = entry.getValue().get(any_nameserver).first();

            String nicHandleId = any_notification.getNicHandleId();
            if (isEligibleToSend(nicHandleId)) {
                String registrarNicHandleId = disablerDAO.getNicHandleByDomainName(domain);
                if (registrarNicHandleId != null) {
                    String message = prepareMessage(entry.getValue());
                    String email = any_notification.getEmail();
                    DnsNotificationEmailParameters parameters = new DnsNotificationEmailParameters(nicHandleId, registrarNicHandleId, email, domain, message);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(), parameters);
                }
                Collection<DnsNotification> allNotifs = new HashSet<DnsNotification>();
                for (Set<DnsNotification> notifs : entry.getValue().values()) {
                    allNotifs.addAll(notifs);
                }
                removeNotifications(allNotifs);
            }
        } catch (Exception e) {
            LOG.error("Problem with sending notification", e);
        }

    }

    private void removeNotifications(Collection<DnsNotification> notifications) {
        for (DnsNotification notification : notifications) {
            dnsNotificationDAO.deleteById(notification.getId());
        }
    }
}
