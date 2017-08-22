package pl.nask.crs.domains.dsm;

import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * A Domain State Machine API - this allows to use Domain State Machine to manage domain's state.   
 * 
 * @author Artur Gniadzik
 *
 */
public interface DomainStateMachine {

	/**
	 * Returns a list of events which are valid in the current state of the domain.
	 * 
	 * @param domainName
	 * 
	 * @return
	 */
	List<String> getValidEventNames(String domainName);
	
	/**
	 * Checks, if any event is handled in the current domain state
	 *  
	 * @param domainName
	 * @param eventName
	 * 
	 * @return false if none of the events is handled in the current state (also if eventName param is empty or null) 
	 */
	boolean validateEvent(String domainName, DsmEventName... eventName);
	boolean validateEvent(String domainName, DsmEvent... event);
	
	/**
	 * Checks, if any event is handled in the current domain state. If it is, the domain is being locked for update
	 *  
	 * @param domainName
	 * @param eventName
	 * 
	 * @return false if none of the events is handled in the current state (also if eventName param is empty or null) 
	 */
	boolean validateEventAndLockDomain(String domainName, DsmEventName... eventName);
	boolean validateEventAndLockDomain(String domainName, DsmEvent... event);
	

	/**
	 * Makes a transition from the current domain state using an event name as the transition name. 
	 * If the domain's current state doesn't allow event name, an exception will be thrown. 
	 * If there are any actions defined with this transition, they will be invoked.
	 * @param user logged in user effecting the action or null if there is no such user (e.g. scheduler causes the action)
	 * @param domain domain object 
	 * @param eventName
	 */
	void handleEvent(AuthenticatedUser user, Domain domain, DsmEvent event, OpInfo opInfo) throws DomainIllegalStateException;

	/**
	 * Handles an event for a non-existing domain (initial dsm state == 0).
	 * @param user logged in user effecting the action or null if there is no such user (e.g. scheduler causes the action)
	 * @param domain
	 * @param event
	 */
	void handleEvent(AuthenticatedUser user, NewDomain domain, DsmEvent event, OpInfo opInfo);

    void handleEvent(AuthenticatedUser user, String domainName, DsmEventName eventName, OpInfo opInfo) throws DomainIllegalStateException;

    void handleEvent(AuthenticatedUser user, String domainName, DsmEvent event, OpInfo opInfo) throws DomainIllegalStateException;
}
