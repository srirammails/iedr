package pl.nask.crs.app.triplepass;

import pl.nask.crs.security.authentication.AuthenticatedUser;


/**
 * Responsible for performing a triple-pass on a REG ticket
 *
 */
public interface TriplePassAppService {

    /**
     * Performs triple-pass on admin passed tickets and GIBO domains
     */
    public void triplePass();

    /**
	 * Performs a triple-pass on a domain registration ticket. 
	 *
     * @param user
	 * @param domainName the name of the domain which is to be checked
	 * @return true, if the domain was triple passed and created.
	 */
	public boolean triplePass(AuthenticatedUser user, String domainName);
	
	public boolean triplePass(AuthenticatedUser user, String domainName, String remark); 

	/**
	 * First step of triple pass for GIBO domain: admin failure (this would change the NRP status to DELETED)
	 * 
	 * @param user
	 * @param domainName
	 * @param remark
	 */
	public void giboAdminFailed(AuthenticatedUser user, String domainName, String remark);
}
