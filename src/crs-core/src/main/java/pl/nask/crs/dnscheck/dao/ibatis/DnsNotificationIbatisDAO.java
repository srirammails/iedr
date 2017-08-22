package pl.nask.crs.dnscheck.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationIbatisDAO extends GenericIBatisDAO<DnsNotification, Integer> implements DnsNotificationDAO {

    public DnsNotificationIbatisDAO() {
        setCreateQueryId("notification.insertNotification");
        setGetQueryId("notification.getNotificationById");
        setDeleteQueryId("notification.deleteById");
    }

    @Override
    public List<DnsNotification> getByDomainName(String domainName) {
        return performQueryForList("notification.getNotificationByDomainName", domainName);
    }

    @Override
    public List<DnsNotification> getByNH(String nicHandleId) {
        return performQueryForList("notification.getNotificationByNH", nicHandleId);
    }

    @Override
    public List<DnsNotification> getAll() {
        return performQueryForList("notification.getAll");
    }

    @Override
    public DnsNotificationDate getNotificationDate(String nicHandleId) {
        return performQueryForObject("notification.getNotificationDate", nicHandleId);
    }

    @Override
    public void createNotificatioDate(DnsNotificationDate notificationDate) {
        performInsert("notification.createNotificationDate", notificationDate);
    }

    @Override
    public void update(DnsNotificationDate notificationDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nicHandleId", notificationDate.getNicHandle());
        params.put("date", notificationDate.getNextNotificationDate());
        performUpdate("notification.updateNotificationDate", params);
    }

    @Override
    public void deleteAllNotificationsExceptFor(String domainName, List<String> nsNames) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("domainName", domainName);
        params.put("nsNames", new ArrayList<String>(nsNames));
        performDelete("notification.deleteAllAboutDomainExceptGivenNameservers", params);
    }
}
