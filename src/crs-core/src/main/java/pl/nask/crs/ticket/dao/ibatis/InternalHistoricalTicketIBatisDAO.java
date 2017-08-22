package pl.nask.crs.ticket.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalHistoricalTicketIBatisDAO extends GenericIBatisDAO<InternalHistoricalTicket, Long> {

    public InternalHistoricalTicketIBatisDAO() {
        setCreateQueryId("historic-ticket.createTicketHistory");
        setFindQueryId("historic-ticket.findTicketHistory");
        setLimitedFindQueryId("historic-ticket.findTicketHistorySimple");
        setCountFindQueryId("historic-ticket.countTicketHistory");
    }

    @Override
    public void create(InternalHistoricalTicket internalHistoricalTicket) {
        super.create(internalHistoricalTicket);
        performInsert("historic-ticket.createTicketNameserverHist", internalHistoricalTicket);
    }
}
