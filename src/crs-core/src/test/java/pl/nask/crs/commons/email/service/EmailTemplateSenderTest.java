package pl.nask.crs.commons.email.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

/**
 * @author Kasia Fulara
 */
public class EmailTemplateSenderTest extends AbstractContextAwareTest {

    private EmailParameters param;
    @Autowired
    EmailTemplateSender emailTemplateSender;
    @Autowired
    TicketDAO ticketDao;
    @Autowired
    NicHandleDAO nicHandleDAO;

    @BeforeMethod
	public void init() {
        Ticket actual = ticketDao.get(7L);
        param = new TicketEmailParameters(null, actual);
        //param = new NicHandleEmailParameters(nicHandleDAO.get("APITEST-IEDR"));
    }

    @Test
    public void testSendTemplate() {
        try {
            emailTemplateSender.sendEmail(104, param);
        } catch (TemplateNotFoundException e) {
            Logger.getLogger(EmailTemplateSenderTest.class).error(e);
        } catch (TemplateInstantiatingException e) {
            Logger.getLogger(EmailTemplateSenderTest.class).error(e);
        } catch (EmailSendingException e) {
            Logger.getLogger(EmailTemplateSenderTest.class).error(e);
        }
    }

}
