package pl.nask.crs.ticket.services.impl;

import pl.nask.crs.ticket.exceptions.TicketResponseAlreadyExistsException;
import pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException;
import pl.nask.crs.ticket.response.TicketResponse;
import pl.nask.crs.ticket.services.TicketResponseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InMemoryTicketResponseService implements TicketResponseService {

    private Map<Long, TicketResponse> responses = new TreeMap<Long, TicketResponse>();

    public InMemoryTicketResponseService() {
        responses.put(1l, new TicketResponse(1l, "accept", "accept"));
        responses.put(2l, new TicketResponse(2l, "reject", "reject"));
    }

    public TicketResponse createResponse(String title, String text) throws TicketResponseAlreadyExistsException {
        TicketResponse response = new TicketResponse(nextId(), title, text);
        responses.put(response.getId(), response);
        return response;
    }

    public void updateResponseText(long responseId, String newText) throws TicketResponseNotFoundException {
        TicketResponse response = responses.get(responseId);
        if (response == null) throw new TicketResponseNotFoundException();
        response.setText(newText);
    }

    public void deleteResponse(long responseId) throws TicketResponseNotFoundException {
        TicketResponse response = responses.get(responseId);
        if (response == null) throw new TicketResponseNotFoundException();
        responses.remove(responseId);
    }

    public List<TicketResponse> findAllResponses() {
        return new ArrayList<TicketResponse>(responses.values());
    }

    private long nextId() {
        long max = 0;
        for (TicketResponse response : responses.values()) {
            if (response.getId() > max) max = response.getId();
        }
        return max + 1;
    }
}
