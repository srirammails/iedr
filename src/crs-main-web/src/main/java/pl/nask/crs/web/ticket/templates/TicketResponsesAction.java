package pl.nask.crs.web.ticket.templates;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.exceptions.TicketResponseAlreadyExistsException;
import pl.nask.crs.ticket.response.TicketResponse;
import pl.nask.crs.ticket.services.TicketResponseService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TicketResponsesAction extends ActionSupport {

    private static final int EMPTY_TICKET_RESPONSE_ID = -1;

    private static final String EMPTY_TICKET_TITLE = "";

    private static final String EMPTY_TICKET_TEXT = "";

    private static final TicketResponse EMPTY_TICKET_RESPONSE = new TicketResponse(
            EMPTY_TICKET_RESPONSE_ID,
            EMPTY_TICKET_TITLE,
            EMPTY_TICKET_TEXT
    );

    private long selectedResponseId = EMPTY_TICKET_RESPONSE.getId();

    private String newResponseTitle = EMPTY_TICKET_RESPONSE.getTitle();

    private String responseText = EMPTY_TICKET_RESPONSE.getText();

    private TicketResponseService ticketResponseService;

    public TicketResponsesAction(TicketResponseService ticketResponseService) {
        Validator.assertNotNull(ticketResponseService, "ticket response service");
        this.ticketResponseService = ticketResponseService;
    }

    public long getSelectedResponseId() {
        return selectedResponseId;
    }

    public void setSelectedResponseId(long selectedResponseId) {
        this.selectedResponseId = selectedResponseId;
    }

    public String getNewResponseTitle() {
        return newResponseTitle;
    }

    public void setNewResponseTitle(String newResponseTitle) {
        this.newResponseTitle = newResponseTitle;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public List<TicketResponse> getResponses() {
        List<TicketResponse> ret = new ArrayList<TicketResponse>();
        ret.add(EMPTY_TICKET_RESPONSE);
        ret.addAll(ticketResponseService.findAllResponses());
        return ret;
    }

    public void prepare() {
        initToEmptyResponse();
    }

    public String save() throws Exception {
        ticketResponseService.updateResponseText(selectedResponseId, responseText);
        addActionMessage("The ticket response text has been saved.");
        return SUCCESS;
    }

    public String add() throws Exception {
        try {
            TicketResponse created = ticketResponseService.createResponse(newResponseTitle, responseText);
            selectedResponseId = created.getId();
            addActionMessage("The ticket response has been created.");
            return SUCCESS;
        } catch (TicketResponseAlreadyExistsException e) {
            addActionMessage("Cannot save response: response with this title already exists");
            return ERROR;
        }
    }

    public String delete() throws Exception {
        if (selectedResponseId != EMPTY_TICKET_RESPONSE.getId()) {
            ticketResponseService.deleteResponse(selectedResponseId);
            initToEmptyResponse();
            addActionMessage("The ticket response has been deleted.");
        }
        return SUCCESS;
    }

    private void initToEmptyResponse() {
        selectedResponseId = EMPTY_TICKET_RESPONSE.getId();
        newResponseTitle = EMPTY_TICKET_RESPONSE.getTitle();
        responseText = EMPTY_TICKET_RESPONSE.getText();
    }

}
