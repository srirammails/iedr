package pl.nask.crs.ticket.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.ticket.response.TicketResponse;

public interface TicketResponseDAO extends GenericDAO<TicketResponse, Long> {

	/**
	 * searches for object with id given as a parameter
	 *
	 * @param title name of the searched ticket response
	 * @return
	 */
	public TicketResponse get(String title);
}
