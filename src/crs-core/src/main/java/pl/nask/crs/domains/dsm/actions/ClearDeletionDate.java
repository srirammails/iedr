package pl.nask.crs.domains.dsm.actions;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class ClearDeletionDate extends AbstractDsmAction {

	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
		domain.setDeletionDate(null);
	}
}
