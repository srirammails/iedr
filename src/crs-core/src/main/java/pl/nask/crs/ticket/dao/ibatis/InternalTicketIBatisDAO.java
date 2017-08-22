package pl.nask.crs.ticket.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketNameserver;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalTicketIBatisDAO extends GenericIBatisDAO<InternalTicket, Long> {
	private SequentialNumberGenerator idGenerator;
	

    public InternalTicketIBatisDAO() {
        setGetQueryId("ticket.geTicketById");
        setLockQueryId("ticket.getLockedTicketById");
        setUpdateQueryId("ticket.updateTicket");
        setFindQueryId("ticket.findTicket");
        setCountFindQueryId("ticket.countTotalSearchResult");
        setDeleteQueryId("ticket.deleteTicket");
    }

    public List<String> findDomainNames(SearchCriteria<Ticket> criteria, int offset, int limit) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("criteria", criteria);
        parameterMap.put("limit", limit);
        parameterMap.put("offset", offset);
        List<String> res = performQueryForList("ticket.findDomainNames", parameterMap);
        return res;
    }

    public long createTicket(InternalTicket internalTicket) {
        long id = idGenerator.getNextId();
        internalTicket.setId(id);
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        parameterMap.put("internalTicket", internalTicket);
        parameterMap.put("optCert", "N");
        performInsert("ticket.insertTicket", parameterMap);

        return id;
    }

	public void setIdGenerator(SequentialNumberGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

    public void deleteDNS(Long ticketId, InternalTicketNameserver nameserver) {
        int _ = -1;
        performDelete("ticket.deleteTicketNameserver", createDNSParams(ticketId, nameserver, _));
    }

    public void createDNS(Long ticketId, InternalTicketNameserver nameserver, int order) {
        performInsert("ticket.createTicketNameserver", createDNSParams(ticketId, nameserver, order));
    }

    private Map<String, Object> createDNSParams(Long ticketId, InternalTicketNameserver nameserver, int order) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ticketId", ticketId);
        params.put("nameserver", nameserver);
        params.put("order", order);
        return params;
    }
}
