package pl.nask.crs.app.authorization.permissions;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.app.InvalidAccountNumberException;
import pl.nask.crs.app.authorization.queries.DomainQuery;
import pl.nask.crs.app.authorization.queries.NicHandleCreatePermissionQuery;
import pl.nask.crs.app.authorization.queries.NicHandleQuery;
import pl.nask.crs.app.authorization.queries.NicHandleSearchPermissionQuery;
import pl.nask.crs.app.authorization.queries.QueriedDomains;
import pl.nask.crs.app.authorization.queries.TicketPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PackagePermission;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

/**
 * Allows a Registrar (not a Direct registrar!) to perform operations on it's own objects like the domain, ticket, nichandles, nh defaults etc.
 *
 * @author Artur Gniadzik
 *
 */
public class ResellerPermission extends ContextualPermission {
	private NicHandleDAO nicHandleDAO;
	private DomainDAO domainDAO;
	private TicketDAO ticketDAO;
	private Permission methodPermission;
	
	public ResellerPermission(String id, String name, NicHandleDAO nicHandleDAO) {
		this(id, new PackagePermission(name, name), nicHandleDAO);		
	}
	
	public ResellerPermission(String id, Permission namedPermission, NicHandleDAO nicHandleDAO) {
		super(id, namedPermission.getName());
		this.nicHandleDAO = nicHandleDAO;
		this.methodPermission = namedPermission;
	}
	
	@Override
	protected boolean verifyContext(PermissionQuery permission) throws InvalidAccountNumberException {
		if (permission instanceof DomainQuery) {
			return verify ((DomainQuery) permission);			
		} else if (permission instanceof NicHandleQuery) {
			return verify((NicHandleQuery) permission);		
		} else if (permission instanceof NicHandleSearchPermissionQuery) {
			return verify((NicHandleSearchPermissionQuery) permission);
		} else if (permission instanceof TicketPermissionQuery) {
			return verify((TicketPermissionQuery) permission);
		} else if (permission instanceof NicHandleCreatePermissionQuery) {
			return verify((NicHandleCreatePermissionQuery) permission);
		} else if (permission instanceof NamedPermission) {
	    	 return true; // FIXME: return true for all implied NamedPermissions
	     } else
	    	 return false;
	}
	
	
	
	protected boolean verify(NicHandleCreatePermissionQuery permission) throws InvalidAccountNumberException {
		return validateAccountNumber(permission.getUser(), permission.getNicHandle().getAccountNumber());
	}

	private boolean verify(TicketPermissionQuery permission) {
		TicketPermissionQuery perm = (TicketPermissionQuery) permission;
		AuthenticatedUser user = perm.getUser();
		Ticket t = perm.getTicket();
		if (t == null) {
			long ticketId = perm.getTicketId();
			t = ticketDAO.get(ticketId);
		}			 
		
		NicHandle nh = nicHandleDAO.get(user.getUsername());
		//TODO billingNH match instead account match ?
		return verifyAccessToTheTicket(nh, t);
	}

	protected boolean verifyAccessToTheTicket(NicHandle nh, Ticket t) {
		if (t == null)
			return true;		
		NicHandle creatorNh = nicHandleDAO.get(t.getCreator().getNicHandle());
		long creatorAccountId = creatorNh.getAccount().getId();
		if (creatorAccountId == 1)
			return false; 
		long nhId = nh.getAccount().getId();
		return creatorAccountId == nhId;
	}

	private NicHandle getNicHandle(String id) {
		return nicHandleDAO.get(id);
	}
	
    private boolean verify(NicHandleQuery perm) throws InvalidAccountNumberException {
    	if (perm.getNicHandle() == null) {
    		return verifyAccessToTheNicHandle(perm.getUser(), getNicHandle(perm.getNicHandleId()));
    	} else {
    		return verifyAccessToTheNicHandle(perm.getUser(), perm.getNicHandle()) && isSelfNameChange(perm.getUser().getUsername(), perm.getNicHandle());
    	}
	}

    private boolean isSelfNameChange(String billNHId, NicHandle changedNH) {
        return billNHId.equals(changedNH.getNicHandleId()) || isNameNotChanged(changedNH);
    }

    private boolean isNameNotChanged(NicHandle changedNH) {
        NicHandle oldNH = nicHandleDAO.get(changedNH.getNicHandleId());
        return oldNH.getName().equals(changedNH.getName());
    }

    private boolean verify(NicHandleSearchPermissionQuery permission) throws InvalidAccountNumberException {
		NicHandleSearchPermissionQuery perm = (NicHandleSearchPermissionQuery) permission;
    	return validateAccountNumber(perm.getUser(), perm.getAccountNumber());
	}

	private boolean verify(DomainQuery permission) {
    	DomainQuery q = (DomainQuery) permission;
		AuthenticatedUser user = q.getUser();
		List<Domain> domains = getDomains(q.getDomains());
		return verifyAccessToTheDomains(user, domains);
	}

	private List<Domain> getDomains(QueriedDomains domains) {
    	if (!Validator.isEmpty(domains.getDomains())) {
    		return domains.getDomains();
    	} else {
    		return getDomains(domains.getDomainNames());
    	}
	}

	private List<Domain> getDomains(List<String> domainsNames) {
        List<Domain> ret = new ArrayList<Domain>();
        for (String name : domainsNames) {
            Domain d = domainDAO.get(name);
            ret.add(d);
        }
        return ret;
    }
	
	protected boolean verifyAccessToTheNicHandle(AuthenticatedUser user, NicHandle nicHandle) throws InvalidAccountNumberException {
    	if (nicHandle == null)
    		return true; 
    	if ((long) nicHandle.getAccount().getId() == 1)
    		return false;
    	
    	NicHandle userNh = nicHandleDAO.get(user.getUsername());

    	if ((long) nicHandle.getAccount().getId() == userNh.getAccount().getId()) {
    		return true;
    	} else {
    		throw new InvalidAccountNumberException();
    	}
	}
	
	protected boolean validateAccountNumber(AuthenticatedUser user, Long accountNumber) throws InvalidAccountNumberException {
		if (accountNumber == null)
    		return false;
		if (accountNumber == 1)
			return false;
		
    	NicHandle userNh = nicHandleDAO.get(user.getUsername());
    	if ((long) accountNumber == userNh.getAccount().getId()) {
    		return true;
    	} else {
    		throw new InvalidAccountNumberException();
    	}    	
	}
	

	protected boolean verifyAccessToTheDomains(AuthenticatedUser user, List<Domain> domains) {
        if (user == null || domains == null || domains.size() == 0)
            return false;
        NicHandle nh = nicHandleDAO.get(user.getUsername());
                       
		for (Domain d: domains) {
			if (!verifyAccessToTheDomain(nh, d)) {
				return false;
			}			
		}
		return true;
	}

	protected boolean verifyAccessToTheDomain(NicHandle nh, Domain d) {
		return d != null && d.getResellerAccount().getId() == nh.getAccount().getId();		
	}

	@Override
	public boolean isImplied(PermissionQuery query) {
		try {
			return methodPermission.implies(query);
		} catch (PermissionDeniedException e) {
			return false;
		}
	}
	
	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}
	
	public void setDomainDAO(DomainDAO domainDAO) {
		this.domainDAO = domainDAO;
	}
	
	public DomainDAO getDomainDAO() {
		return domainDAO;
	}
	
	@Override
	public String toString() {	
		return getClass().getSimpleName() + "[id: " + getId() + ", methodPermission: " + methodPermission + "]";
	}
	
	public Permission getMethodPermission() {
		return methodPermission;
	}
	
	@Override
	public String getDescription() {
		if (getClass() != ResellerPermission.class)
			return null;
		return "Contextual, allows to access domains, tickets and nichandles with the same account number (<>1) as user's. In combination with " + methodPermission.getId() + " (" + methodPermission.getDescription() + ")";
	}
}
