package pl.nask.crs.web.ticket;

import com.opensymphony.xwork2.Preparable;
import org.apache.log4j.Logger;

import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.utils.ContactValidator;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * Action for the Ticket EDIT view. Action methods:
 * <ul>
 * <li>{{@link #save()}</li>
 * </ul>
 *
 * @author Artur Gniadzik
 */
public class TicketEditAction extends TicketAction implements Preparable {
    private final Logger log = Logger.getLogger(this.getClass());

    private String responseText;

    protected ContactValidator contactValidator;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * Saves changes in the ticket. Forwards to the REVISE view.
     *
     * @return REVISE if the save operation is successful, ERROR if an error occurs.
     */
    public String save() throws Exception {
        return update(false);
    }

    public String forceSave() throws Exception {
        return update(true);
    }

    private String update(boolean forceUpdate) throws Exception{
        try {
            log();
            log.error("Save");
            Ticket ticket = getTicket();
            getTicketAppService().update(getUser(), ticket.getId(), ticket.getOperation(),
                    responseText, ticket.isClikPaid(), forceUpdate);
            return REVISE;
        } catch (ContactNotActiveException ex){
            addActionError("Contact " + ex.getNicHandleId() + " is not Active.");
            return ERROR;
        }
    }

    protected TicketModel getTicketModel(long id) throws AccessDeniedException, TicketNotFoundException {
        return getTicketAppService().edit(getUser(), id);
    }


    @Override
    public void prepare() throws Exception {
        if (ticketModel != null) {
            getTicketWrapper().updateNameserversWithNewValues();
        }
    }
}
