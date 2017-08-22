package pl.nask.crs.domains.dsm.actions;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

public class Email extends AbstractDsmAction {
	private static final Logger LOG = Logger.getLogger(Email.class);
	protected EmailTemplateSender templateSender;
	protected EmailTemplateDAO emailTemplateDao;
	protected NicHandleDAO nicHandleDAO;
	protected int emailTemplateId;
			
	public Email(NicHandleDAO nicHandleDAO, EmailTemplateSender templateSender, EmailTemplateDAO emailTEmplateDao) {
		this.nicHandleDAO = nicHandleDAO;
		this.templateSender = templateSender;
		this.emailTemplateDao = emailTEmplateDao;
	}
	
	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {		
		Ticket t = (Ticket) (event.hasParameter(DsmEvent.TICKET) ? event.getParameter(DsmEvent.TICKET) : null);
		Domain oldDomain = (Domain) (event.hasParameter(DsmEvent.OLD_DOMAIN) ? event.getParameter(DsmEvent.OLD_DOMAIN) : null);
		EmailParameters params = getEmailParameters(user, emailTemplateId, domain, oldDomain, t, event);		
		sendEmail(emailTemplateId, params);
	}

	private void sendEmail(int emailTemplateId, EmailParameters params)  {
		try {
			templateSender.sendEmail(emailTemplateId, params);
		} catch (IllegalArgumentException e) {
			logError(String.format("Error building email (template id=%s): ", emailTemplateId, e.getMessage()), e);
		} catch (TemplateNotFoundException e) {
			logError(String.format("Cannot find template (id=%s)", emailTemplateId), e);
		} catch (TemplateInstantiatingException e) {
			logError(String.format("Cannot build template (id=%s) with given parameters", emailTemplateId), e);
		} catch (EmailSendingException e) {
			logError(String.format("Error sending email, template (id=%s)", emailTemplateId), e);
		}
	}

	private void logError(String detailedMsg, Exception e) {
		LOG.error("Error sending email with DSM action: " + this +", " + detailedMsg, e);
	}

	private EmailParameters getEmailParameters(AuthenticatedUser user, int emailTemplateId, Domain domain, Domain oldDomain, Ticket t, DsmEvent event) {
		DomainEmailParameters domainEmailParameters;
		String username = (user == null) ? null : user.getUsername();
		DomainEmailParameters.AccountRelatedNHEnum accountRelatedNHEnum = DomainEmailParameters.AccountRelatedNHEnum.BILLC;
		switch (emailTemplateId) {
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 46:
			domainEmailParameters = new DomainEmailParameters(username, nicHandleDAO, domain);
			break;
		case 33:
			accountRelatedNHEnum = DomainEmailParameters.AccountRelatedNHEnum.GAINING_BILLC;
		case 17:
		case 18:
		case 19:
		case 20:
		case 39:
			// transfer! that goes into default cause - if it's a bulk transfer, than there is no transfer ticket..
		default:
			// by default we can predict, that DomainEmailParamerers should be created and try to use them.
			if (t == null) {
				domainEmailParameters =  new DomainEmailParameters(username, nicHandleDAO, domain);
			} else {
				if (oldDomain == null) {
					domainEmailParameters = new DomainEmailParameters(username, nicHandleDAO, domain, t);
				} else {
					domainEmailParameters = new DomainEmailParameters(username, nicHandleDAO, domain, oldDomain, t);
				}
			}
		}
		domainEmailParameters.setAccountRelatedNicHandle(accountRelatedNHEnum);
		if (event.hasParameter(DsmEvent.TRANSACTION_DETAIL)) 
			domainEmailParameters.setParameter(ParameterNameEnum.TRANSACTION_DETAIL, event.getParameter(DsmEvent.TRANSACTION_DETAIL));

		if (event.hasParameter(DsmEvent.TRANSACTION_VALUE))
			domainEmailParameters.setParameter(ParameterNameEnum.TRANSACTION_VALUE, MoneyUtils.getRoudedAndScaledValue((BigDecimal) event.getParameter(DsmEvent.TRANSACTION_VALUE), 2));

		if (event.hasParameter(DsmEvent.ORDER_ID))
			domainEmailParameters.setParameter(ParameterNameEnum.ORDER_ID, event.getParameter(DsmEvent.ORDER_ID));		

		return domainEmailParameters;
	}

	@Override
	public DsmAction getAction(String param) {
		Email a = new Email(nicHandleDAO, templateSender, emailTemplateDao);
		a.setEmailTemplateId(param);
		return a;
	}

	private void setEmailTemplateId(String param) {
		this.setActionParam(param);
		this.emailTemplateId = Integer.parseInt(param);		
	}
}
