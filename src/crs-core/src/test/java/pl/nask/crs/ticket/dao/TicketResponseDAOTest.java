package pl.nask.crs.ticket.dao;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.response.TicketResponse;

/**
 * @author Pawel pixel Kleniewski
 */
public class TicketResponseDAOTest extends AbstractContextAwareTest {

    @Autowired
    TicketResponseDAO responses;

	@Test
	public void getTicketResponse() {
		TicketResponse expected = new TicketResponse(10, "Geographical Rule", "123\r\nqwe");
		TicketResponse actual = responses.get(expected.getId());
		// todo: Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
	}
}
