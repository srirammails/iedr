package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DsmAction {

	void invoke(AuthenticatedUser user, Domain domain, DsmEvent event);

	String getActionParam();

	
}
