package pl.nask.crs.web.ticket;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Action for the Ticket VIEW.
 *
 * @author Artur Gniadzik
 * @author Patrycja Wegrzynowicz
 * @author Marianna Mysiorska
 */
public class TicketViewAction extends TicketAction {

    int historyIndex = -1;

    long changeId = -1;

    String previousAction = "tickets-input";

    protected TriplePassSupportService triplePassSupportService;

    public int getHistoryIndex() {
        return historyIndex;
    }

    public void setHistoryIndex(int historyIndex) {
        this.historyIndex = historyIndex;
    }

    public void setTriplePassSupportService(TriplePassSupportService triplePassSupportService) {
        this.triplePassSupportService = triplePassSupportService;
    }

    public boolean isHistory() {
        return historyIndex > -1;
    }

    public boolean hasCurrent() {
        return ticketModel.getTicket() != null;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public String current() throws Exception {
        historyIndex = -1;
        return execute();
    }

    /*
        Fix for bug #1315
     */
    public String getMethodForCurrentButton() {
        if (previousAction.equals("tickets-input"))
            return "ticketview";       
        else if (previousAction.equals("ticketshistory-input"))
            return "ticketview-current";
        else
            return previousAction;
    }

    public String change() throws Exception {
        return SUCCESS;
    }

    public String execute() throws Exception {
        if (id == null)
            throw new IllegalStateException("Ticket id missing");
        ticketModel = getTicketAppService().view(getUser(), id);
        return SUCCESS;
    }

    public String history() throws Exception {
        ticketModel = getTicketAppService().history(getUser(), id);
        if (changeId >= 0) {
            List<HistoricalObject<Ticket>> history = ticketModel.getHistory();
            for (int i = 0; i < history.size(); ++i) {
                HistoricalObject<Ticket> object = history.get(i);
                if (object.getChangeId() == changeId) {
                    historyIndex = i;
                    break;
                }
            }
        }
        return SUCCESS;
    }

    public Ticket getTicket() {
        List<HistoricalObject<Ticket>> history = getTicketModel().getHistory();
        if (0 <= historyIndex && historyIndex < history.size()) {
            return history.get(historyIndex).getObject();
        }
        return getTicketModel().getTicket();
    }

    protected TicketModel getTicketModel(long id) throws AccessDeniedException, TicketNotFoundException {
        return getTicketAppService().view(getUser(), id);
    }

    public String techCheck() {
        try {
            AuthenticatedUser user = null;
            triplePassSupportService.performTechnicalCheck(user, getId(), true);
            addActionMessage("Tech Check complete");
            return SUCCESS;
        } catch (TicketNotFoundException e) {
            addActionError("No ticket with id=" + getId());
            return ERROR;
        } catch (TicketIllegalStateException e) {
            addActionError("Ticket state prevents from performing a technical check. Is the ticket admin-passed?");
            LOG.error("Illegal state of the ticket", e);
            return ERROR;
        } catch (TechnicalCheckException e) {
            addActionError("Technical check ended with an error: " + StringEscapeUtils.escapeHtml(e.getMessage()));
            LOG.info("Technical check error", e);
            return ERROR;
        } catch (HostNotConfiguredException e) {
            addActionError("DNS Check failed: <pre>" + StringEscapeUtils.escapeHtml(e.getFullOutputMessage()) + "</pre>");
            return ERROR;
        }
    }
}
