package pl.nask.crs.commons.email.service;

import static org.easymock.EasyMock.createMock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailInstantiator;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.impl.EmailInstantiatorImpl;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

/**
 * @author Kasia Fulara
 */

public class EmailInstantiatorTest extends AbstractContextAwareTest {

    private EmailInstantiator emailInst;
    private EmailTemplateDAO daoMock;
    private EmailParameters param;
    @Autowired
    TicketDAO ticketDao;
    @Resource
    EmailTemplateDAO emailTemplateDAO;

    @BeforeMethod
	public void init() {
        daoMock = createMock(EmailTemplateDAO.class);
        emailInst = new EmailInstantiatorImpl();
        Ticket actual = ticketDao.get(7L);
        param = new TicketEmailParameters(null, actual);
    }

    @Test
    public void testInstantiate() {
        try {
            EmailTemplate template = emailTemplateDAO.get(43);
            Email email = emailInst.instantiate(template, param, true);
        } catch (TemplateInstantiatingException e) {
            Logger.getLogger(EmailInstantiatorTest.class).error(e);
        }
    }
}
