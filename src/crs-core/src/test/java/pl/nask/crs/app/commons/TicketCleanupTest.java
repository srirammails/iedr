package pl.nask.crs.app.commons;

import javax.annotation.Resource;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;
import pl.nask.crs.ticket.services.impl.TransferTicketEmailParameters;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class TicketCleanupTest extends AbstractTransactionalTestNGSpringContextTests {

	@Resource
	private CommonSupportService commonSupportService;
	
	@Resource 
	private TicketService ticketService;
	
	@Mocked
	private EmailTemplateSenderImpl sender;
	
	private long xferTicketId = 259924;
	
	@Test
	public void notificationEmail103ShouldBeSentWhenRemovingExpiredTransferTicket() throws Exception {
		// having a not canceled ticket
		ticketService.updateCustomerStatus(xferTicketId, CustomerStatusEnum.NEW, "test");
		ticketService.updateAdminStatus(xferTicketId, AdminStatusEnum.NEW, "test");
		
		// expect
		new NonStrictExpectations() {{
			sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(), withInstanceOf(TicketEmailParameters.class)); minTimes=1;
			sender.sendEmail(EmailTemplateNamesEnum.TRANSFER_REQUEST_EXPIRED.getId(), withInstanceOf(TransferTicketEmailParameters.class)); minTimes=1;
		}};
		
		// when the ticket is cleaned
		commonSupportService.cleanupTicket(null, new OpInfo("test"), xferTicketId);
	}
	
	@Test
	public void notificationEmail103ShouldBeSentWhenRemovingCustomerCanceledTicet() throws Exception {
		// having a cancelled ticket
		ticketService.updateCustomerStatus(xferTicketId, CustomerStatusEnum.CANCELLED, "test");
		
		// expect
		new NonStrictExpectations() {{
			sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(), withInstanceOf(TicketEmailParameters.class)); minTimes=1;
		}};
		
		// when the ticket is cleaned
		commonSupportService.cleanupTicket(null, new OpInfo("test"), xferTicketId);
	}
	
	@Test
	public void notificationEmail103ShouldBeSentWhenRemovingAdminCanceledTicet() throws Exception {
		// having a cancelled ticket
		ticketService.updateAdminStatus(xferTicketId, AdminStatusEnum.CANCELLED, "test");
		
		// expect
		new NonStrictExpectations() {{
			sender.sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP.getId(), withInstanceOf(TicketEmailParameters.class)); minTimes=1;
		}};
		
		// when the ticket is cleaned
		commonSupportService.cleanupTicket(null, new OpInfo("test"), xferTicketId);
	}
}
