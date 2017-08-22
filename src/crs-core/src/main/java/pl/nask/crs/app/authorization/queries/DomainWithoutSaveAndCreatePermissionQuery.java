package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class DomainWithoutSaveAndCreatePermissionQuery extends NamedPermissionQuery implements DomainQuery {
	
	private final AuthenticatedUser user;
	private final QueriedDomains dquery;
	
	public DomainWithoutSaveAndCreatePermissionQuery(String name, QueriedDomains domains, AuthenticatedUser user) {
		super(name);
		this.user = user;
		this.dquery = domains;
	}
	
	@Override
    public QueriedDomains getDomains() {
		return dquery;
	}

	public AuthenticatedUser getUser() {
		return user;
	}	
}
