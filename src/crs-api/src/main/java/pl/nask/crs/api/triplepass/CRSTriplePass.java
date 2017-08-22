package pl.nask.crs.api.triplepass;

import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;

// FIXME: those are not used by ANYTHING except some selenium tests. Remove it (as the logic is already tested)
@Deprecated
@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSTriplePass {
	/**
	 * Performs a triple-pass on a domain registration ticket:
	 * <ul>
	 * <li>check, if the ticket is admin-passed</li>
	 * <li>perform a technical check</li>
	 * <li>perform a financial check</li>
	 * <li>promote ticket to a domain</li>
	 * </li>
	 * 
     * @param user
	 * @param domainName the name of the domain which is to be checked
	 * @return true, if triplePass was successful and the domain was created  
	 */
	boolean triplePass(
			@WebParam(name="user") AuthenticatedUserVO user, 
			@WebParam(name="domainName") String domainName);
	
	/**
	 * A part of the triple pass, performs a financial check on a registration ticket.
	 * 
	 * @param user
	 * @param ticketId the id of the ticket to perform the financial check on.
	 * 
	 * @return true, if the financial check was successful.
	 */
	boolean financialCheck(
			@WebParam(name="user") AuthenticatedUserVO user,
			@WebParam(name="ticketId") long ticketId);

	/**
	 * promotes the registration ticket to a domain object
	 * @param ticketId Id of the ticket
	 * @return true, if the domain was created, false if not.
	 * @throws IllegalArgumentException if the ticket does not exist or it's state is not valid
	 */
	boolean promoteTicketToDomain(
			@WebParam(name="user") AuthenticatedUserVO user,
			@WebParam(name="ticketId") long ticketId);

    /**
     * First step of triple pass for GIBO domain: admin failure (this would change the NRP status to DELETED)
     *
     * @param user
     * @param domainName
     * @param remark
     */
    public void giboAdminFailed(AuthenticatedUserVO user, String domainName, String remark);

}
