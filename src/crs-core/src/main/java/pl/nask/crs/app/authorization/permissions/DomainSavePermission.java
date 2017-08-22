package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.app.authorization.queries.DomainQuery;
import pl.nask.crs.app.authorization.queries.QueriedDomains;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

/**
 * @author Kasia Fulara, Artur Gniadzik
 */
public class DomainSavePermission extends NamedPermissionQuery implements DomainQuery {

	private QueriedDomains dquery;
    private AuthenticatedUser user;

    public DomainSavePermission(String id, String name, Domain domain, AuthenticatedUser user) {
    	super(name);
		this.user = user;
        Validator.assertNotNull(domain, "domain");
        this.dquery= new QueriedDomains(domain);
    }

    @Override
    public QueriedDomains getDomains() {
    	return dquery;
    }
    
    public AuthenticatedUser getUser() {
		return user;
	}
}
