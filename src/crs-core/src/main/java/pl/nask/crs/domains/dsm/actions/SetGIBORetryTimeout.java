package pl.nask.crs.domains.dsm.actions;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Sets the GIBORetryTimeout for 24 hours from now.
 * @author Artur Gniadzik
 *
 */
public class SetGIBORetryTimeout extends AbstractDsmAction {

	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
		domain.setGiboRetryTimeout(DateUtils.addHours(new Date(), getGlobalConfig().getGiboRetryTimeout()));
	}
}
