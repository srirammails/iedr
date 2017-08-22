package pl.nask.crs.domains.dsm;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.actions.AbstractDsmAction;
import pl.nask.crs.domains.dsm.actions.ClearDeletionDate;
import pl.nask.crs.domains.dsm.actions.ClearSuspensionDate;
import pl.nask.crs.domains.dsm.actions.Email;
import pl.nask.crs.domains.dsm.actions.EmailCond;
import pl.nask.crs.domains.dsm.actions.RollRenewalDate;
import pl.nask.crs.domains.dsm.actions.SetDeletionDate;
import pl.nask.crs.domains.dsm.actions.SetGIBORetryTimeout;
import pl.nask.crs.domains.dsm.actions.SetRenewalDate;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateCurrent;
import pl.nask.crs.domains.dsm.actions.SetSuspensionDateRenewal;
import pl.nask.crs.domains.dsm.actions.SetTransferDate;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

public class DsmActionsRegistry {
	private Map<String, DsmActionFactory> map = new HashMap<String, DsmActionFactory>();
	private ApplicationConfig appConfig;
	private EmailTemplateSender emailTemplateSender;
	private EmailTemplateDAO emailTemplateDao; 
	private DomainDAO domainDao;
	private NicHandleDAO nicHandleDAO;
	
	public DsmActionsRegistry(ApplicationConfig applicationConfig) {
		appConfig = applicationConfig;
	}			
		
	public void initActionFactories() {		
		addFactory("SetRenewalDate", new SetRenewalDate());
		addFactory("SetGIBORetryTimeout", new SetGIBORetryTimeout());
		addFactory("SetSuspensionDateCurrent", new SetSuspensionDateCurrent());
		addFactory("SetDeletionDate", new SetDeletionDate());
		addFactory("ClearDeletionDate", new ClearDeletionDate());
		addFactory("ClearSuspensionDate", new ClearSuspensionDate());
		addFactory("SetSuspensionDateRenewal", new SetSuspensionDateRenewal());
		addFactory("RollRenewalDate", new RollRenewalDate());
		addFactory("RollRenewalDateOne", new RollRenewalDate(1));
		addFactory("Email", new Email(nicHandleDAO, emailTemplateSender, emailTemplateDao));
        addFactory("SetTransferDate", new SetTransferDate(domainDao));
        addFactory("EmailCond", new EmailCond(nicHandleDAO, emailTemplateSender, emailTemplateDao));
	}

	private void addFactory(String key, AbstractDsmAction actionFactory) {		
		actionFactory.setGlobalConfig(appConfig);
		map.put(key, actionFactory);
	}

	public DsmAction actionFor(String factoryName, String param) {
		DsmActionFactory actionFactory = map.get(factoryName);
		if (actionFactory == null)
			return null;
		
		DsmAction action = actionFactory.getAction(param);
		return action; 
	}

	public boolean hasActionFactoryFor(String name) {
		return map.containsKey(name);
	}
	
	public void setEmailTemplateDao(EmailTemplateDAO emailTemplateDao) {
		this.emailTemplateDao = emailTemplateDao;
	}
	
	public void setEmailTemplateSender(EmailTemplateSender emailTemplateSender) {
		this.emailTemplateSender = emailTemplateSender;
	}
	
	public void setDomainDao(DomainDAO domainDao) {
		this.domainDao = domainDao;
	}
	
	public void setNicHandleDAO(NicHandleDAO nicHandleDAO) {
		this.nicHandleDAO = nicHandleDAO;
	}
}
