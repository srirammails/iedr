package pl.nask.crs.web.ticket;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * Action class for the ticket browser. Allows to perform action on the selected ticket (ticket with given id).
 *
 * @author Artur Gniadzik
 */
public class TicketBrowserAction extends AuthenticatedUserAwareAction {

    private static final Logger log = Logger.getLogger(ActionSupport.class);

    private TicketAppService ticketService;

    private Dictionary<Integer, AdminStatus> adminStatusDictionary;

    private long id;

    private int newAdminStatus;

    private String newHostmaster;

    public TicketBrowserAction(TicketAppService ticketService, Dictionary<Integer, AdminStatus> adminStatusDict) {
        Validator.assertNotNull(ticketService, "ticket service");
        Validator.assertNotNull(adminStatusDict, "admin status dict");
        this.ticketService = ticketService;
        this.adminStatusDictionary = adminStatusDict;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getPreviousTicketId() {
        return id;
    }

    public int getNewAdminStatus() {
        return newAdminStatus;
    }

    public void setNewAdminStatus(int newAdminStatus) {
        this.newAdminStatus = newAdminStatus;
    }

    public String getNewHostmaster() {
        return newHostmaster;
    }

    public void setNewHostmaster(String newHostmaster) {
        this.newHostmaster = newHostmaster;
    }

    public String checkIn() throws Exception {
        ticketService.checkIn(getUser(), id, adminStatusDictionary.getEntry(newAdminStatus));
        return SUCCESS;
    }

    public String checkOut() throws Exception {
        AuthenticatedUser user = getUser();
        ticketService.checkOut(user, id);
        return SUCCESS;
    }

    public String reassign() throws Exception {
        ticketService.reassign(getUser(), id, newHostmaster);
        return SUCCESS;
    }


    public String alterStatus() throws Exception {
        if (newAdminStatus == AdminStatusEnum.PASSED.getCode() && hasFailureReasons(id)) {
            addActionError("Cannot set admin status to PASSED: ticket contains failure reasons.");
            return ERROR;
        }
        ticketService.alterStatus(getUser(), id, adminStatusDictionary.getEntry(newAdminStatus));
        return SUCCESS;
    }

    private boolean hasFailureReasons(long id) throws Exception {
        TicketModel ticketModel = ticketService.view(getUser(), id);
        return ticketModel.getTicket().getOperation().hasFailureReasons();
    }

}
