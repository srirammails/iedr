package pl.nask.crs.ticket.services;

import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.exceptions.TicketResponseAlreadyExistsException;
import pl.nask.crs.ticket.exceptions.TicketResponseNotFoundException;
import pl.nask.crs.ticket.response.TicketResponse;

/**
 *
 * @author Pawel pixel Kleniewski
 *         Date: 2008-10-24  Time: 14:15:33
 */
public class TicketResponseServiceTest extends AbstractContextAwareTest {

    @Resource
    TicketResponseService ticketResponseService;

    /**
     * method tests if TicketResponse can be created
     * should pass whitout exception
     *
     * @throws TicketResponseAlreadyExistsException
     *
     */
    @Test
    public void createTicketResponseNotFound() throws TicketResponseAlreadyExistsException {
        String
                title = "alamakota",
                text = "alamakota";

        TicketResponse response = ticketResponseService.createResponse(title, text);
        AssertJUnit.assertNotNull(response);
    }

    /**
     * method tests if TicketResponse can be created
     * should throw exception
     *
     * @throws TicketResponseAlreadyExistsException
     *
     */
    @Test(expectedExceptions = TicketResponseAlreadyExistsException.class)
    public void createTicketResponseFound() throws TicketResponseAlreadyExistsException {
        String title = "Geographical Rule";
        String text = "123\r\nqwe";

        ticketResponseService.createResponse(title, text);
    }

    /**
     * method tests if TicketResponse can be updated
     * should throw exception
     *
     * @throws TicketResponseNotFoundException
     *
     */
    @Test(expectedExceptions = TicketResponseNotFoundException.class)
    public void updateTicketResponseNotFound() throws TicketResponseNotFoundException {
        String
                text = "alamakota";

        ticketResponseService.updateResponseText(1, text);
    }

    /**
     * method tests if TicketResponse can be updated
     * should pass whitout exception
     *
     * @throws TicketResponseNotFoundException
     *
     */
    @Test
    public void updateTicketResponseFound() throws TicketResponseNotFoundException {
        String
                text = "alamakota";

        ticketResponseService.updateResponseText(10, text);
    }

    /**
     * method tests if TicketResponse can be deleted
     * should throw exception
     *
     * @throws TicketResponseNotFoundException
     *
     */
    @Test(expectedExceptions = TicketResponseNotFoundException.class)
    public void deleteTicketResponseNotFound() throws TicketResponseNotFoundException {

        ticketResponseService.deleteResponse(1);
    }

    /**
     * method tests if TicketResponse can be deleted
     * should pass whitout exception
     *
     * @throws TicketResponseNotFoundException
     *
     */
    @Test
    public void deleteTicketResponseFound() throws TicketResponseNotFoundException {

        ticketResponseService.deleteResponse(10);
    }

    @Test
    public void findAllTicketResponse() {

        List<TicketResponse> list = ticketResponseService.findAllResponses();

        assertTrue(!list.isEmpty());
        assertTrue(list.toArray().length == 58);
    }
}
