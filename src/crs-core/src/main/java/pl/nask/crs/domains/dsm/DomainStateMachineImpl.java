package pl.nask.crs.domains.dsm;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dsm.events.ParameterlessEvent;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.impl.DomainServiceHelper;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DomainStateMachineImpl implements DomainStateMachine {
	private static final Logger LOG = Logger.getLogger(DomainStateMachineImpl.class);
	
	private final DsmDAO dao;
	private final DomainDAO domainDao;	
	private final DomainServiceHelper serviceHelper;
	private final ApplicationConfig applicationConfig;
	
	private final DsmActionsRegistry actionsRegistry;
	
	public DomainStateMachineImpl(DsmDAO dao, DomainDAO domainDao, HistoricalDomainDAO historicalDomainDao, ApplicationConfig applicationConfig, DsmActionsRegistry registry) {
		this.dao = dao;
		this.domainDao = domainDao;
		this.applicationConfig = applicationConfig;
		this.serviceHelper = new DomainServiceHelper(domainDao, historicalDomainDao);
		this.actionsRegistry = registry;
	}
	
	@Override
	public List<String> getValidEventNames(String domainName) {
		return dao.getValidEventNames(domainName);
	}	
	
	@Override
	public void handleEvent(AuthenticatedUser user, Domain domain, DsmEvent event, OpInfo opInfo) {
		Validator.assertNotNull(domain, "domain");
		String domainName = domain.getName();
		DsmEventName eventName = event.getName();
		LOG.debug("Handling event " + eventName + " for domain " + domain.getName());

		DsmTransition trans = dao.getTransitionFor(domainName, eventName);
		if (trans == null) {
			List<String> names = dao.getValidEventNames(domainName);
			throw new DomainIllegalStateException(eventName + " is not a valid event for a domain " + domainName + " in the current state (" + domain.getDsmState().getId() + "). Valid events are: " + names, domain);
		}
		executeEventActions(user, domain, event, trans);
        String eventRemark = "DSM event: " + eventName;
		domain.setRemark(opInfo.getRemark() == null ? eventRemark : opInfo.getRemark() + " (" + eventRemark + ")");
		serviceHelper.updateDomainAndHistory(domain, trans.getTargetState(), opInfo);
        deleteIfEligible(domain, trans.getTargetState(), opInfo);
        LOG.debug("Finished handling event " + eventName + " for domain " + domainName);
    }

    private void deleteIfEligible(Domain domain, int dsmState, OpInfo opInfo) {
        if (dsmState == applicationConfig.getEligibleForDeletionDomainState()) {
            serviceHelper.delete(domain.getName(), domain.getChangeDate(), opInfo);
        }
    }

	@Override
	public void handleEvent(AuthenticatedUser user, NewDomain domain, DsmEvent event, OpInfo opInfo) {
		String domainName = domain.getName();
		LOG.debug("Creating a new domain object: " + domainName);		
		domainDao.createDomain(domain);
		handleEvent(user, domainName, event, opInfo);
	}

	void executeEventActions(AuthenticatedUser user, Domain domain, DsmEvent event, DsmTransition trans) {
		for (DsmAction action: trans.getActions(actionsRegistry)) {
			action.invoke(user, domain, event);
		}		
	}

	@Override
	public boolean validateEvent(String domainName, DsmEventName... eventNames) {
		boolean res = true;

		if (eventNames == null)
			return res;
		
		for (DsmEventName name: eventNames) { 
			res = res && validate(domainName, name);					
		}
		
		return res;
	}
	
	@Override
	public boolean validateEventAndLockDomain(String domainName, DsmEventName... eventName) {
		boolean res = validateEvent(domainName, eventName);
		if (res) {
			domainDao.lock(domainName);
		} 
		
		return res;
	}
	
	
	@Override
	public boolean validateEvent(String domainName, DsmEvent... events) {
		boolean res = false;

		if (events == null)
			return res;
		
		for (DsmEvent e: events) { 
			res = res || validate(domainName, e.getName());					
		}
		
		return res;
	}

	@Override
	public boolean validateEventAndLockDomain(String domainName, DsmEvent... event) {
		boolean res = validateEvent(domainName, event);
		if (res) {
			domainDao.lock(domainName);
		} 
		
		return res;
	}
	
	private boolean validate(String domainName, DsmEventName eventName) {
		boolean res = dao.checkEvent(domainName, eventName);
		LOG.debug("Validating event " + eventName + " for domain " + domainName + ": " + (res ? "valid" : "invalid"));
		return res;
	}

    @Override
    public void handleEvent(AuthenticatedUser user, String domainName, DsmEventName eventName, OpInfo opInfo) throws DomainIllegalStateException, DomainNotFoundException {
        handleEvent(user, domainName, new ParameterlessEvent(eventName), opInfo);
    }

    @Override
    public void handleEvent(AuthenticatedUser user, String domainName, DsmEvent event, OpInfo opInfo) throws DomainIllegalStateException, DomainNotFoundException {
        if (domainDao.lock(domainName)) {
            Domain domain = domainDao.get(domainName);
            handleEvent(user, domain, event, opInfo);
        } else {
            throw new DomainNotFoundException(domainName);
        }
    }
}
