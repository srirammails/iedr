package pl.nask.crs.ticket;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * @author Kasia Fulara
 */
public class TicketTestUtil {

    private static final ApplicationContext xmlFactory;
    private static final long offset = 0;
    private static final long limit = 10;
    private static final long ticketId1 = 256625;
    private static final long ticketId2 = -10;
    private static final long statusId1 = 1;
    private static final long statusId2 = -19;
    private static final String statusName1 = "Passed";
    private static final String statusName2 = "bleeeeee";

    static {
        try {
            xmlFactory = new ClassPathXmlApplicationContext(new String[]{"/ticket-config.xml", "/ticket-config-test.xml", "/users-config.xml"});
        } catch (BeansException e) {
            Logger.getLogger(TicketTestUtil.class).error(e);
            throw e;
        }
    }

    /*all search criteria are set to null*/
    private static TicketSearchCriteria criteria1 = new TicketSearchCriteria();
    private static HistoricTicketSearchCriteria histCriteria1 = new HistoricTicketSearchCriteria();

    /* standard search criteria, result is present in database*/
    private static TicketSearchCriteria criteria2 = new TicketSearchCriteria();
    private static HistoricTicketSearchCriteria histCriteria2 = new HistoricTicketSearchCriteria();

    /*search criteria for object that are not present in database*/
    private static TicketSearchCriteria criteria3;


    static {
        histCriteria2.setTicketId(256544L);
        criteria2.setAdminStatus(1);
        criteria2.setDomainName("bi");

    }

    public static ApplicationContext getXmlFactory() {
        return xmlFactory;
    }

    public static TicketSearchCriteria getCriteria1() {
        return criteria1;
    }

    public static TicketSearchCriteria getCriteria2() {
        return criteria2;
    }

    public static TicketSearchCriteria getCriteria3() {
        return criteria3;
    }

    public static long getOffset() {
        return offset;
    }

    public static long getLimit() {
        return limit;
    }

    public static long getTicketId1() {
        return ticketId1;
    }

    public static long getTicketId2() {
        return ticketId2;
    }

    public static long getStatusId1() {
        return statusId1;
    }

    public static long getStatusId2() {
        return statusId2;
    }

    public static String getStatusName1() {
        return statusName1;
    }

    public static String getStatusName2() {
        return statusName2;
    }

    public static HistoricTicketSearchCriteria getHistCriteria1() {
        return histCriteria1;
    }

    public static HistoricTicketSearchCriteria getHistCriteria2() {
        return histCriteria2;
    }
}
