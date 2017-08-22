package pl.nask.crs.domains.dsm.actions;

import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.actions.conditions.ActionCondition;
import pl.nask.crs.domains.dsm.actions.conditions.ActionConditionFactory;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class EmailCond extends Email {
	private ActionCondition actionCondition;

	public EmailCond(NicHandleDAO nicHandleDAO, EmailTemplateSender emailTemplateSender, EmailTemplateDAO emailTemplateDao) {
		super(nicHandleDAO, emailTemplateSender, emailTemplateDao);
	}

	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException  {
		if (conditionTrue(domain, event)) {
			super.invokeAction(user, domain, event);
		}
	}
	
	private boolean conditionTrue(Domain domain, DsmEvent event) {
		return actionCondition.accept(domain, event);
	}

	@Override
	public DsmAction getAction(String param) {
		EmailCond action = new EmailCond(nicHandleDAO, templateSender, emailTemplateDao);
		action.setActionParam(param);
		return action;
	}
	
	@Override
	protected void setActionParam(String actionParam) {	
		super.setActionParam(actionParam);
		// parse actionParam string
		String[] params = actionParam.split(",");
		if (params.length != 3)
			throw new IllegalArgumentException("EmailCond action needs 3 arguments to be passed");
		this.actionCondition = ActionConditionFactory.valueOf(params[0].trim()).conditionFor(params[1].trim());
		this.emailTemplateId = Integer.parseInt(params[2].trim());
	}

}
