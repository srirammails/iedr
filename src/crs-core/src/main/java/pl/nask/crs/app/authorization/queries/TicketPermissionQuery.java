package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class TicketPermissionQuery extends NamedPermissionQuery {

	private long ticketId;
	private final AuthenticatedUser user;
	private Ticket ticket;

	public TicketPermissionQuery(String permissionName, long ticketId,
			AuthenticatedUser user) {
		super(permissionName);
		this.ticketId = ticketId;
		this.user = user;
	}
	
	public TicketPermissionQuery(String permissionName, Ticket ticket,
			AuthenticatedUser user) {
		super(permissionName);
		this.ticket= ticket;
		this.user = user;
	}
	
	public long getTicketId() {
		return ticketId;
	}
	
	public AuthenticatedUser getUser() {
		return user;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
}
