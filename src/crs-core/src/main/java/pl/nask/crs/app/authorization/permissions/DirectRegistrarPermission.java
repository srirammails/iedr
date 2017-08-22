package pl.nask.crs.app.authorization.permissions;

import java.util.List;

import pl.nask.crs.app.InvalidAccountNumberException;
import pl.nask.crs.app.authorization.queries.NicHandleCreatePermissionQuery;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.user.permissions.Permission;

public class DirectRegistrarPermission extends ResellerPermission {

	public DirectRegistrarPermission(String id, String name,
			NicHandleDAO nicHandleDAO) {
		super(id, name, nicHandleDAO);
	}
	
	public DirectRegistrarPermission(String id,
			Permission namedPermission, NicHandleDAO nicHandleDAO) {
		super(id, namedPermission, nicHandleDAO);
	}

	@Override
	protected boolean verifyAccessToTheDomain(NicHandle nh, Domain d) {
		if (nh.getAccount().getId() != d.getResellerAccount().getId()) {
			return false;
		}		
		
		if (d.getBillingContact() == null 
			|| d.getBillingContact().getNicHandle() == null 
			|| !d.getBillingContact().getNicHandle().equals(nh.getNicHandleId())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	protected boolean verifyAccessToTheTicket(NicHandle nh, Ticket t) {
		return nh.getNicHandleId().equals(t.getCreator().getNicHandle());
	}
	
	@Override
	protected boolean verifyAccessToTheNicHandle(AuthenticatedUser user,
			NicHandle nicHandle) throws InvalidAccountNumberException {	
		String directNh = user.getUsername();
		return directNh.equalsIgnoreCase(nicHandle.getCreator()) 
				||
				directNh.equalsIgnoreCase(nicHandle.getNicHandleId())
				|| 
				nhIsAContactInDirectsDomain(directNh, nicHandle.getNicHandleId());
	}
	
	
	
	private boolean nhIsAContactInDirectsDomain(String directNh, String nh) {
		DomainSearchCriteria crit = new DomainSearchCriteria();
		crit.setBillingNH(directNh);
		crit.setNicHandle(nh);
		List<String> res = getDomainDAO().findDomainNames(crit, 0, 1);
		return res.size() > 0;
	}

	@Override
	protected boolean verify(NicHandleCreatePermissionQuery permission)
			throws InvalidAccountNumberException {
		return ((long) permission.getNicHandle().getAccountNumber() == 1);				
	}
	
	@Override
	public String getDescription() {
		if (getClass() != DirectRegistrarPermission.class)
			return null;
		return "Contextual, allows to access domains, tickets and nichandles if the account number is 1, user is a domain billing contact, creator of a ticket or nichandle, or a nichandle is a contact in one of the domains the user is a billing contact in. In combination with " + getMethodPermission().getId() + " (" + getMethodPermission().getDescription() + ")"; 
	}
}
