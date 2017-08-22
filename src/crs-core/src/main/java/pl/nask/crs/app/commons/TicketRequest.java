package pl.nask.crs.app.commons;

import java.util.List;

import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;

public interface TicketRequest {
    public enum PeriodType {
        Y,M
    }

    public String getDomainName();

    public String getDomainHolder();

    public String getDomainHolderClass();

    public String getDomainHolderCategory();

    public String getAdminContact1NicHandle();

    public String getAdminContact2NicHandle();

    public String getTechContactNicHandle();

    public List<Nameserver> getNameservers();

    public Nameserver getNameserver(int order);

    public String getRequestersRemark();

    public String getCharityCode();

    public Period getRegPeriod();

    public String getAuthCode();

    public boolean isCharity();

    public Ticket toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType);

    public List<String> getAdminContacts();

}