package pl.nask.crs.ticket.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmStateStub;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.dao.ibatis.converters.TicketConverter;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketNameserver;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class ConvertingTicketDAO extends ConvertingGenericDAO<InternalTicket, Ticket, Long> implements TicketDAO {

    private final InternalTicketIBatisDAO dao;

    public ConvertingTicketDAO(InternalTicketIBatisDAO internalDao, Converter<InternalTicket, Ticket> internalConverter) {
        super(internalDao, internalConverter);
        dao = internalDao;
    }
    
    public List<String> findDomainNames(SearchCriteria<Ticket> domainSearchCriteria, int offset, int limit) {
        return dao.findDomainNames(domainSearchCriteria, offset, limit);
    }

    public long createTicket(Ticket ticket) {
        InternalTicket internalTicket = getInternalConverter().from(ticket);
        long id = dao.createTicket(internalTicket);
        updateNameservers(internalTicket.getNameservers(), id);
        return id;
    }

    @Override
    public void update(Ticket ticket) {
        super.update(ticket);
        InternalTicket internalTicket = getInternalConverter().from(ticket);
        updateNameservers(internalTicket.getNameservers(), internalTicket.getId());
    }

    private void updateNameservers(List<InternalTicketNameserver> nameservers, Long ticketId) {
        InternalTicket existingTicket = getInternalDao().get(ticketId);
        for (InternalTicketNameserver n : existingTicket.getNameservers()) {
            dao.deleteDNS(ticketId, n);
        }
        int order = 0;
        for (InternalTicketNameserver n : nameservers) {
            dao.createDNS(ticketId, n, order++);
        }
    }

}
