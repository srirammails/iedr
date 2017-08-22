package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_ticket_1.QueryType;
import ie.domainregistry.ieapi_ticket_1.QueryTypeType;

public class TicketQueryCommandBuilder {

	public static QueryType buildQueryFor(QueryTypeType ticketType, Integer page) {
		QueryType query = new QueryType();
		query.setPage(page);		
		query.setType(ticketType );
		return query;
	}
	
	public static QueryType buildQueryFor(QueryTypeType ticketType) {
		QueryType query = new QueryType();
		query.setPage(null);		
		query.setType(ticketType );
		return query;
	}

}
