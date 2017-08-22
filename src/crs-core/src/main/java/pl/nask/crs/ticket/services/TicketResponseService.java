package pl.nask.crs.ticket.services;

import pl.nask.crs.ticket.exceptions.TicketResponseAlreadyExistsException;
import pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException;
import pl.nask.crs.ticket.response.TicketResponse;

import java.util.List;

/**
 * It serves as a facade to the operations on ticket responses including creation, update and retrieval.
 *
 * @author Patrycja Wegrzynowicz
 */
public interface TicketResponseService {

    /**
     * Creates a new ticket response.
     *
     * @param title the title of a new ticket response; must be unique
     * @param text  the content of a new ticket response
     * @return TicketResponse new object
     * @throws TicketResponseAlreadyExistsException
     *          thrown when the response with a given name already exists
     */
    TicketResponse createResponse(String title, String text) throws TicketResponseAlreadyExistsException;

    /**
     * Updates the content of the ticket response with a given identifier.
     *
     * @param responseId the identifier of the ticket response to be updated
     * @param newText    the new content to be set on the ticket response with a given identifier
     * @throws pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException
     *          thrown when the response with a given identifier does not exist
     */
    void updateResponseText(long responseId, String newText) throws TicketResponseNotFoundException;

    /**
     * Deletes the ticket response identified by the given response identifier.
     *
     * @param responseId the identifier of the ticket response to be deleted
     * @throws pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException
     *          thrown when the response with a given identifier does not exist
     */
    void deleteResponse(long responseId) throws TicketResponseNotFoundException;

    /**
     * Returns the list of all ticket responses. When no responses are found, the empty list is returned.
     *
     * @return the list of all ticket responses; in case of no result, the empty list.
     */
    List<TicketResponse> findAllResponses();

}
