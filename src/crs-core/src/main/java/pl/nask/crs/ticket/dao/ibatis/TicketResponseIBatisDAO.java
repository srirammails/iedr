package pl.nask.crs.ticket.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.ticket.dao.TicketResponseDAO;
import pl.nask.crs.ticket.response.TicketResponse;

public class TicketResponseIBatisDAO extends GenericIBatisDAO<TicketResponse, Long> implements TicketResponseDAO {

    public TicketResponseIBatisDAO() {
        setCreateQueryId("ticket-response.createTicketResponse");
        setGetQueryId("ticket-response.getTicketResponse");
        setFindQueryId("ticket-response.findTicketResponse");
        setUpdateQueryId("ticket-response.updateTicketResponseText");
        setLockQueryId("ticket-response.getLockedTicketResponse");
        setDeleteQueryId("ticket-response.deleteTicketResponse");
    }

    public TicketResponse get(String title) {
        return performQueryForObject("ticket-response.getTicketResponseByTitle", title);
    }
}
