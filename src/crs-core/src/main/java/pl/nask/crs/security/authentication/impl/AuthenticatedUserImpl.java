package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Class represetns authenticated user in the system.
 *
 * @author Marianna Mysiorska
 */

class AuthenticatedUserImpl implements AuthenticatedUser {

    private String username;
    private String token;
    
    private String superUserName;

    public AuthenticatedUserImpl(String username) {
        Validator.assertNotEmpty(username, "username");
        this.username = username;
    }

    public AuthenticatedUserImpl(String username, String token) {
        Validator.assertNotEmpty(username, "username");
        Validator.assertNotEmpty(token, "token");
        this.username = username;
        this.token = token;
    }
    
    public AuthenticatedUserImpl(String username, String superUserName, String token) {
    	this(username, token);
    	Validator.assertNotEmpty(superUserName, "superUserName");
    	this.superUserName = superUserName;
    }

    public String getUsername() {
        return username;
    }
    
    public String getAuthenticationToken() {
    	return token;
    }
    
    @Override
    public String getSuperUserName() {    
    	return superUserName;
    }

	@Override
	public String toString() {
		return "AuthenticatedUserImpl [getAuthenticationToken()="
				+ getAuthenticationToken() + ", getUsername()=" + getUsername()
				+ "]";
	}
    
    

}
