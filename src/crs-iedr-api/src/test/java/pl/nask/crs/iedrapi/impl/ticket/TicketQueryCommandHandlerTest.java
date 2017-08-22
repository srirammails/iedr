package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.QueryType;
import ie.domainregistry.ieapi_ticket_1.QueryTypeType;
import ie.domainregistry.ieapi_ticket_1.ResDataType;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.helpers.DummyHttpSession;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

@ContextConfiguration("classpath:iedr-api-config.xml")
public class TicketQueryCommandHandlerTest extends AbstractTransactionalTestNGSpringContextTests {
	@Autowired
	TicketQueryCommandHandler handler;
	
	@Autowired
	CommonAppService commonAppService;
	
	@Autowired 
	TicketSearchService ticketSearchService;
	
	AuthData auth;
	
	@BeforeMethod
	public void prepareAuthData() {
		auth = AuthData.getInstance(new DummyHttpSession("APITEST-IEDR"), "127.0.0.1");
	}
	
	@Test
	public void shouldNotReturnCustomerCanceledTickets() throws Exception {
		// check, how many tickets are there
		QueryType command = TicketQueryCommandBuilder.buildQueryFor(QueryTypeType.ALL);
		ValidationCallback callback = new ValidationCallback();
		ResponseType res = handler.handle(auth, command, callback);
		List<String> oldResults = getTicketsFor(res);
		
		// cancel one ticket
		TicketSearchCriteria criteria = new TicketSearchCriteria();
		criteria.setDomainName("tickettodelete.ie");
		LimitedSearchResult<Ticket> ticketsToDelete = ticketSearchService.find(criteria , 0, 1, null);
		long ticketToDelete = ticketsToDelete.getResults().get(0).getId();
		commonAppService.cancel(auth.getUser(), ticketToDelete);
		
		// check, if the ticket list is shorter
		res = handler.handle(auth, command, callback);
		List<String> newResults = getTicketsFor(res);
		AssertJUnit.assertEquals(oldResults.size() - 1, newResults.size());
	}

	private List<String> getTicketsFor(ResponseType res) {
		return (((ResDataType)((JAXBElement)res.getResData().getAny().get(0)).getValue()).getDomain());
	}
}
