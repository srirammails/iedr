package pl.nask.crs.api.ticket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EnchantedEntityClassVO;
import pl.nask.crs.api.vo.EntityCategoryVO;
import pl.nask.crs.api.vo.EntityClassVO;
import pl.nask.crs.api.vo.FailureReasonsEditVO;
import pl.nask.crs.api.vo.TicketSearchResultVO;
import pl.nask.crs.api.vo.TicketVO;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
/**
 * 
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 *
 * Set of services to access and manipulate Ticket objects.
 * 
 * <p><b><i>user</i> parameter is required by all services</b></p>
 *
 * @author Artur Gniadzik
 *
 */
@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSTicketAppService {
	
    /**
     * Returns ticket object for specified ticket id. 
     *
     * @param user authentication token
     * @param ticketId ticket id to be view
     * 
     * @return full ticket object 
     * @throws AccessDeniedException
     * @throws TicketNotFoundException if no ticket with given id exists
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract TicketVO view(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId)
            throws AccessDeniedException, TicketNotFoundException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * If a domain has an open ticket (every domain may have one open ticket), the method would return this ticket. If not, null would be returned.
     * The result will be similar to calling {@link #find(AuthenticatedUserVO, TicketSearchCriteria, long, long, List)} with the ticketSearchCriteria.domainName set, and than calling {@link #view(AuthenticatedUserVO, long)} with the ticketId from the search result.
     * Note, that the behaviour differs from {@link #view(AuthenticatedUserVO, long)} method, since if no ticket is found, null is returned instead of throwing an exception. 
     * 
     *
     * @param user authentication token
     * @param domainName name of a domain for which tickets will be returned
     * @return ticket object with the same data, as obtained from {@link #view(AuthenticatedUserVO, long)}, or null, if the domain has no ticket open.
     * @throws AccessDeniedException 
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract TicketVO viewTicketForDomain (
    		@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Returns a ticket to edit
     *
     * @param user authentication token
     * @param ticketId ticket id to edit
     * @return
     * @throws AccessDeniedException
     * @throws TicketNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract TicketVO edit(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId)
            throws AccessDeniedException, TicketNotFoundException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Updates ticket
     *
     * @param user authentication token
     * @param ticketId ticket id to update
     * @param domainOperation domain data to be changed
     * @param remark requester's remark to be added. Hostmaster's remark will be updated automatically.
     * @param clikPaid boolean parameter 
     * @throws AccessDeniedException
     * @throws TicketNotFoundException
     * @throws EmptyRemarkException
     * @throws TicketCheckedOutToOtherException
     * @throws TicketNotCheckedOutException
     * @throws TicketNameserverException
     * @throws ContactNotActiveException
     * @throws ContactNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract void update(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "ticketId") long ticketId,
            @WebParam(name = "domainOperations") FailureReasonsEditVO domainOperation,
            @WebParam(name = "remark") String remark,
            @WebParam(name = "clikPaid") boolean clikPaid)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketAlreadyCheckedOutException, ContactNotActiveException, ContactNotFoundException,
            UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException, TicketEmailException, TicketNameserverException;


    /**
     * Returns ticket list. Result is limited by offset and limit parameters.
     *
     * @param user authentication token, required
     * @param searchCriteria ticket search criteria, optional
     * @param offset
     * @param limit maximum number of tickets to be returned
     * @param sortCriteria the list of sort criteria, optional. Possible values of 'orderBy' are:
     * id, type, domainName, domainNameFR, domainHolder, domainHolderFR, resellerAccountId, resellerAccountName,
     *  agreementSigned, ticketEdit, resellerAccountFR, domainHolderClass, domainHolderClassFR,
     *  domainHolderCat, domainHolderCatFR, 
     *  adminContact1NH, adminContact1Name, adminContact1Email, adminContact1CompanyName, adminContact1Country, adminContact1FR,
     *  adminContact2NH, adminContact2Name, adminContact2Email, adminContact2CompanyName, adminContact2Country, adminContact2FR,
     *   techContactNH, techContactName, techContactEmail, techContactCompanyName, techContactCountry, techContactFR, 
     *   billingContactNH, billingContactName, billingContactEmail, billingContactCompanyName, billingContactCountry, billingContactFR,
     *   creatorNH, creatorName, creatorEmail, creatorCompanyName, creatorCountry,
     *   ns1, ns1FR, ip1, ip1FR, 
     *   ns2, ns2FR, ip2, ip2FR,
     *   ns3, ns3FR, ip3, ip3FR,
     *   adminStatus, adminStatusChangeDate,
     *   techStatus, techStatusChangeDate,
     *  checkedOut, checkedOutToNH, checkedOutToName,
     *   renewalDate, changeDate,
     *   requestersRemark,
     *   hostmastersRemark,
     *   creationDate,        
     *   clikPaid
     * 
     * @return 
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public TicketSearchResultVO find(
    		@WebParam(name = "user") AuthenticatedUserVO user,
    		@WebParam(name = "searchCriteria") TicketSearchCriteria searchCriteria, 
    		@WebParam(name = "offset") long offset, 
    		@WebParam(name = "limit") long limit,
    		@WebParam(name = "sortCriteria" ) List<SortCriterion> sortCriteria
    		) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Returns all holder classes and corresponding categories.
     *
     * @param user authentication token, required
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    @Deprecated // not used by NRC, maybe should be left in case it's needed?
    public abstract List<EnchantedEntityClassVO> getClassesWithCategories(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Returns all holder classes.
     *
     * @param user authentication token, required
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract List<EntityClassVO> getClasses(
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Returns holder categories for corresponding class id.
     *
     * @param user authentication token, required
     * @param classId class id categories will be returned, required
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract List<EntityCategoryVO> getCategoriesForClass(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "classId") long classId)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;
}