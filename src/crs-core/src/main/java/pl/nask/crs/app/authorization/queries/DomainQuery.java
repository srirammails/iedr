package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;


public interface DomainQuery {
	QueriedDomains getDomains();
	
	AuthenticatedUser getUser();
}
