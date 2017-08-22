package pl.nask.crs.domains.dsm.actions;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * 
 * Sets the renewal date of the domain to current data + 1 year
 * 
 */
public class SetRenewalDate extends AbstractDsmAction {
	
	@Override
	public DsmAction getAction(String param) {
		SetRenewalDate action = new SetRenewalDate();
		action.setActionParamAsInt(param);
		return action;
	}
	
	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
		domain.setRenewDate(DateUtils.addYears(domain.getRegistrationDate(), getActionParamAsInt()));
	}
}
