package pl.nask.crs.ticket.services.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.dao.TicketResponseDAO;
import pl.nask.crs.ticket.exceptions.TicketResponseAlreadyExistsException;
import pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException;
import pl.nask.crs.ticket.response.TicketResponse;
import pl.nask.crs.ticket.services.TicketResponseService;

import java.util.List;

/**
 * @author Patrycja Wegrzynowicz
 * @author Pawel pixel Kleniewski
 */

public class TicketResponseServiceImpl implements TicketResponseService {

    private TicketResponseDAO responses;

    public TicketResponseServiceImpl(TicketResponseDAO responses) {
        Validator.assertNotNull(responses, "TicketResponseDAO");
        this.responses = responses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketResponse createResponse(String title, String text) throws TicketResponseAlreadyExistsException {
        TicketResponse response = responses.get(title);
        if (response != null) throw new TicketResponseAlreadyExistsException();
        response = new TicketResponse(title, text);
        responses.create(response);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateResponseText(long responseId, String newText) throws TicketResponseNotFoundException {
        TicketResponse response = responses.get(responseId);
        if (response == null) throw new TicketResponseNotFoundException();
        response.setText(newText);
        responses.update(response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResponse(long responseId) throws TicketResponseNotFoundException {
        TicketResponse response = responses.get(responseId);
        if (response == null) throw new TicketResponseNotFoundException();
        responses.deleteById(responseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> findAllResponses() {
        return responses.find(null).getResults();
    }
}
