package pl.nask.crs.ticket.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

/**
 * @author Kasia Fulara
 */
public class HistoricTicketSearchCriteria implements SearchCriteria<HistoricalObject<Ticket>> {

    private Long ticketId;

    private String domainName;

    private String domainHolder;

    private Long accountId;
    
    private String category;
    private String clazz;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    
}
