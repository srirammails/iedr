package pl.nask.crs.app.authorization.permissions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.app.authorization.queries.DomainQuery;
import pl.nask.crs.app.authorization.queries.DomainSearchQuery;
import pl.nask.crs.app.authorization.queries.NicHandleCreatePermissionQuery;
import pl.nask.crs.app.authorization.queries.NicHandleQuery;
import pl.nask.crs.app.authorization.queries.TicketPermissionQuery;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.user.permissions.ContextualPermission;
import pl.nask.crs.user.permissions.NamedPermission;
import pl.nask.crs.user.permissions.PackagePermission;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public class DefaultAccessPermission extends ContextualPermission {
	private NicHandleDAO nicHandleDAO;
	private DomainDAO domainDAO;
	private TicketDAO ticketDAO;
	private Permission methodPermission;
	private Set<String> contactType = new HashSet<String>();
	{
		contactType.add("T");
		contactType.add("A");
		contactType.add("B");
	}

	public DefaultAccessPermission(String id, String name, NicHandleDAO nicHandleDAO) {
		this(id, new PackagePermission(name, name), nicHandleDAO);		
	}

	public DefaultAccessPermission(String id, Permission namedPermission, NicHandleDAO nicHandleDAO) {
		super(id, namedPermission.getName());
		this.nicHandleDAO = nicHandleDAO;
		this.methodPermission = namedPermission;
	}


	@Override
	protected boolean verifyContext(PermissionQuery permission)
			throws PermissionDeniedException {
		if (permission instanceof DomainSearchQuery) {					
			return verify((DomainSearchQuery) permission);
		} else if (permission instanceof DomainQuery) {
			return verify ((DomainQuery) permission);			
		} else if (permission instanceof NicHandleQuery) {
			return verify((NicHandleQuery) permission);		
		} else if (permission instanceof TicketPermissionQuery) {
			return verify((TicketPermissionQuery) permission);
		} else if (permission instanceof NicHandleCreatePermissionQuery) {
			return verify((NicHandleCreatePermissionQuery) permission);
		} else if (permission instanceof NamedPermission) {
			return true; // FIXME: return true for all implied NamedPermissions
		} else
			return false;
	}

	private boolean verify(DomainSearchQuery permission) {
		return permission.getUser().getUsername().equals(permission.getBillingNH());
	}

	private boolean verify(NicHandleCreatePermissionQuery permission) {
		NicHandle user = nicHandleDAO.get(permission.getUser().getUsername());
		return permission.getNicHandle() != null && user.getAccount().getId() == (long) permission.getNicHandle().getAccountNumber();
	}

	private boolean verify(TicketPermissionQuery permission) {
		TicketPermissionQuery perm = (TicketPermissionQuery) permission;
		Ticket t = perm.getTicket();
		if (t == null) {
			long ticketId = perm.getTicketId();
			t = ticketDAO.get(ticketId);
		}			 
		
		return Validator.isEqual(permission.getUser().getUsername(), t.getCreator().getNicHandle());
	}
	
	private boolean verify(NicHandleQuery permission) {
		if (Validator.isEqual(permission.getUser().getUsername(), permission.getNicHandleId())) {
			return true;
		} else {
            if (permission.getName().endsWith(".save")) {
                return false;
            }
			NicHandle nh = permission.getNicHandle(); 
			if (nh == null) {
				nh = nicHandleDAO.get(permission.getNicHandleId());
			}
			if (permission.getUser().getUsername().equalsIgnoreCase(nh.getCreator())) {
				return true;
			} else {
				return nhIsAContactTheSameDomain(permission.getUser().getUsername(), permission.getNicHandleId());
			}
		}
	}

	private boolean verify(DomainQuery permission) {
		String nh = permission.getUser().getUsername();
		if (permission.getDomains().getDomains() != null) {
			for (Domain d: permission.getDomains().getDomains()) {
				if (!verifyAccessToTheDomain(nh, d))
					return false;
			}
		} else {
			for (String domainName: permission.getDomains().getDomainNames()) {
				if (!verifyAccessToTheDomain(nh, domainDAO.get(domainName)))
					return false;
			}
		}
		return true;
	}

	private boolean verifyAccessToTheDomain(String nh, Domain d) {
		if (d == null)
			return true;
		return (contactType.contains("B") && isOnContactList(nh, d.getBillingContacts()))
				||
				(contactType.contains("A") && isOnContactList(nh, d.getAdminContacts()))
				||
				(contactType.contains("T") && isOnContactList(nh, d.getTechContacts()));
	}	

	private boolean isOnContactList(String nh, List<Contact> contacts) {
		for (Contact c: contacts) {
			if (Validator.isEqual(nh,  c.getNicHandle()))
				return true;
		}
		return false;
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
	
	public void setContactType(String contactType) {
		this.contactType.clear();
		this.contactType.add(contactType);
	}

	private boolean nhIsAContactTheSameDomain(String firstNh, String secondNh) {
		DomainSearchCriteria crit = new DomainSearchCriteria();
		crit.setNicHandle(firstNh);
		crit.setContactType(Arrays.asList("A"));
		crit.setSecondContact(secondNh);
		List<String> res = domainDAO.findDomainNames(crit, 0, 1);
		return res.size() > 0;
	}

	@Override
	public String toString() {	
		return getClass().getSimpleName() + "[id: " + getId() + ", methodPermission: " + methodPermission + "]";
	}

	@Override
	public String getDescription() {
		if (getClass() != DefaultAccessPermission.class)
			return null;
		return "Contextual, allows to access " + getProtectedObjects() + " in combination with " + methodPermission.getId() + " (" + methodPermission.getDescription() + ")";
	}

	private String getProtectedObjects() {
		StringBuilder sb = new StringBuilder();
		append(sb, nicHandleDAO != null, "NicHandles which are created by the user or are contacts in the same domains");
		append(sb, ticketDAO != null, "Tickets created by the user");
		append(sb, domainDAO != null, "Domains the user is a contact (" + CollectionUtils.toString(contactType, ", ")+ ") in");
		return sb.toString();
	}

	private void append(StringBuilder sb, boolean append, String text) {
		if (append) {
			if (sb.length() > 0)
				sb.append(", ");
				sb.append(text);
		}
	}

}
