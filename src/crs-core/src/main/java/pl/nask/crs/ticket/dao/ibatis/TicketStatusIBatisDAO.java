package pl.nask.crs.ticket.dao.ibatis;

import pl.nask.crs.statuses.Status;
import pl.nask.crs.statuses.dao.ibatis.StatusIBatisDAO;

public class TicketStatusIBatisDAO extends StatusIBatisDAO<Status, Integer> {

    public TicketStatusIBatisDAO(String getQueryId, String findQueryId) {
        super(getQueryId, findQueryId);
    }
}
