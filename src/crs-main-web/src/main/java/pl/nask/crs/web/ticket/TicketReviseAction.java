package pl.nask.crs.web.ticket;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.Preparable;
import org.apache.log4j.Logger;

import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.services.TicketService;

import com.opensymphony.xwork2.ActionContext;

/**
 * Action for the Ticket REVISE view. Action methods:
 * <p/>
 * <ul>
 * <li>{@link #accept()}</li>
 * <li>{@link #reject()}</li>
 * <li>{@link #save()}</li>
 * </ul>
 *
 * @author Artur Gniadzik
 * @author Patrycja Wegrzynowicz
 */
public class TicketReviseAction extends TicketAction implements Preparable {
    private Logger log = Logger.getLogger(this.getClass());

    private String responseText;

    private Integer newAdminStatus;

    private boolean forceEdit;
    
    private TriplePassAppService triplePassAppService;

    public boolean isForceEdit() {
        return forceEdit;
    }

    public void setForceEdit(boolean forceEdit) {
        this.forceEdit = forceEdit;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public void setNewAdminStatus(Integer newAdminStatus) {
        this.newAdminStatus = newAdminStatus;
    }

    public List<AdminStatus> getRejectionStatuses() {
        List<AdminStatus> ret = new ArrayList<AdminStatus>();
        for (AdminStatus status : getAdminStatusFactory().getEntries()) {
            if (status.getId() != TicketService.NEW
                    && status.getId() != TicketService.PASSED) {
                ret.add(status);
            }
        }
        return ret;
    }

    /**
     * Accepts the ticket and then forwards to the SEARCH view.
     *
     * @return always SEARCH.
     */
    public String accept() throws Exception {
        Ticket ticket = getTicket();
        try {
            if (!validateCurrentTicket(ticket)) {
                return ERROR;
            }
            if (!validateAccept())
                return ERROR;

            getTicketAppService().accept(getUser(), ticket.getId(), responseText);
            
            // invoke triple pass
            if (ticket.getOperation().getType() == DomainOperationType.REG
                    || ticket.getOperation().getType() == DomainOperationType.XFER
                    || ticket.getOperation().getType() == DomainOperationType.MOD) {
                String domainName = ticket.getOperation().getDomainNameField().getNewValue();
                log.info("Invoking triple pass for a domain: " + domainName);
            	getTriplePassAppService().triplePass(getUser(), domainName);
            }
            return SEARCH;
        } catch (TicketEmailException e) {
            Logger.getLogger(getClass()).error("accepting email", e);
            addActionError("Error sending email during the accept of the ticket for "
                    + ticket.getOperation().getDomainNameField().getNewValue());
            return EMAIL_ERROR;
        }
    }

    /**
     * Rejects the ticket and then forwards to the SEARCH view.
     *
     * @return always SEARCH;
     */
    public String reject() throws Exception {
        Ticket ticket = getTicket();
        try {
            if (!validateCurrentTicket(ticket)) {
                return ERROR;
            }
            if (!validateReject())
                return ERROR;
            getTicketAppService().reject(getUser(), ticket.getId(),
                    getAdminStatusFactory().getEntry(newAdminStatus),
                    ticket.getOperation(), responseText);
            if (ticket.getOperation().getType() == DomainOperationType.XFER) {
                String domainName = ticket.getOperation().getDomainNameField().getNewValue();
                log.info("Invoking triple pass for a domain: " + domainName);
                getTriplePassAppService().triplePass(getUser(), domainName);
            }
            return SEARCH;
        } catch (TicketEmailException e) {
            Logger.getLogger(getClass()).error("rejecting email", e);
            addActionError("Error sending email during the reject of the ticket for "
                    + ticket.getOperation().getDomainNameField().getNewValue());
            return EMAIL_ERROR;
        }
    }

    /**
     * Saves the ticket and then forwards to the SEARCH view.
     *
     * @return always SUCCESS;
     */
    public String save() throws Exception {
        log.error("save");
        log.error(responseText);
        Ticket ticket = getTicket();
        if (!validateCurrentTicket(ticket)) {
            return ERROR;
        }
        if (!validateSave()) {
            return ERROR;
        }
        log.error(responseText);
        getTicketAppService().save(getUser(), ticket.getId(), ticket.getOperation(),
                responseText);
        // refresh
        refresh();
        // clear hostmasters remark!
        setResponseText("");
        return SUCCESS;
    }

    private boolean validateSave() {
        return validationHelper.validateStringRequired("responseText", "Hostmaster's Remark");
        // return true;
    }

    private boolean validateAccept() {
        if (getTicket().getOperation().hasFailureReasons()) {
            validateFr("ticketWrapper.domainName.failureReasonId",
                    "domain name");
            validateFr("ticketWrapper.resellerAccount.failureReasonId",
                    "account name");
            validateFr("ticketWrapper.domainHolder.failureReasonId",
                    "domain holder");
            validateFr("ticketWrapper.adminContact1.failureReasonId",
                    "admin contact");
            validateFr("ticketWrapper.adminContact2.failureReasonId",
                    "admin contact");
            validateFr("ticketWrapper.techContact.failureReasonId",
                    "tech contact");
            validateFr("ticketWrapper.billingContact.failureReasonId",
                    "billing contact");
            validateFr("ticketWrapper.domainHolderClass.failureReasonId",
                    "holder class");
            validateFr("ticketWrapper.domainHolderCategory.failureReasonId",
                    "holder category");

            validateFr("nameserverWrappers[0].nameFr", "nameserver name");
            validateFr("nameserverWrappers[0].ipFr", "nameserver ip");
            validateFr("nameserverWrappers[1].nameFr", "nameserver name");
            validateFr("nameserverWrappers[1].ipFr", "nameserver ip");
            validateFr("nameserverWrappers[2].nameFr", "nameserver name");
            validateFr("nameserverWrappers[2].ipFr", "nameserver ip");
            return false;
        } else
            return true;
    }

    private boolean validateFr(String fieldName, String fieldLabel) {
        Integer i = (Integer) ActionContext.getContext().getValueStack()
                .findValue(fieldName, Integer.class);
        if (i != 0) {
            addFieldError(fieldName, "There should be no failure reason for "
                    + fieldLabel);
            return false;
        }
        return true;
    }

    private boolean validateReject() {
        boolean res = true;
        if (newAdminStatus == null) {
            addActionError("You must set an admin status");
            res = false;
        }
        return res
                & validationHelper.validateStringRequired("responseText", "Hostmaster's Remark");
    }

    protected TicketModel getTicketModel(long id)
            throws TicketNotFoundException, AccessDeniedException {
        return getTicketAppService().revise(getUser(), id);
    }

    public String cancelEdit() {
        setForceEdit(false);
        return SUCCESS;
    }
    
    public TriplePassAppService getTriplePassAppService() {
    	return triplePassAppService;
    }
    
    public void setTriplePassAppService(TriplePassAppService service) {
		this.triplePassAppService = service;
	}

    @Override
    public void prepare() throws Exception {
        if (ticketModel != null) {
            getTicketWrapper().updateNameserversWithNewFailureReasons();
        }
    }

    private boolean validateCurrentTicket(Ticket ticket) throws Exception {
        Ticket dbTicket = getTicketAppService().view(getUser(), ticket.getId()).getTicket();
        if (!ticket.getChangeDate().equals(dbTicket.getChangeDate())) {
            refresh();
            addActionMessage("The ticket was changed in the meantime, please recheck.");
            return false;
        }
        return true;
    }
}
