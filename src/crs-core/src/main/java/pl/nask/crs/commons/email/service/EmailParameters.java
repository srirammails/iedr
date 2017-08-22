package pl.nask.crs.commons.email.service;

import java.util.List;

import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface EmailParameters {

    public List<? extends ParameterName> getParameterNames();

    public String getParameterValue(String name, boolean html);
    
    /*
     * returns NicHandle of the User that is loggedIn to the console or null if user is logged in through the crsweb 
     * or there is no user logged in (scheduler forced this job)
     */
    
    public String getLoggedInNicHandle();

    public String getAccountRelatedNicHandle();
    
    public String getDomainName();

}
