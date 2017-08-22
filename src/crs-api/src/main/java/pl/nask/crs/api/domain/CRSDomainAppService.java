package pl.nask.crs.api.domain;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.ClassCategoryVO;
import pl.nask.crs.api.vo.CountryVO;
import pl.nask.crs.api.vo.DeletedDomainsSearchResultVO;
import pl.nask.crs.api.vo.DomainAvailabilityVO;
import pl.nask.crs.api.vo.DomainForRenewalCriteriaVO;
import pl.nask.crs.api.vo.DomainSearchResultVO;
import pl.nask.crs.api.vo.ExtendedDomainInfoVO;
import pl.nask.crs.api.vo.NsReportSearchResultVO;
import pl.nask.crs.api.vo.TransferedDomainSearchResultVO;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSDomainAppService {

    /**
     * Returns detailed domain info containing domain object, list of realted domains, list of pending domains
     * and information if domain has tickets or documents. This method works like #edit method but has different permissions.
     *
     * @param user authentication token, required
     * @param domainName viewed domain name, required
     * @return
     * @throws AccessDeniedException
     * @throws DomainNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract ExtendedDomainInfoVO view(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName)
            throws AccessDeniedException, DomainNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Searches for domains matching given criteria in the given order. Domain objects from the result don't carry information about contacts or nameservers.
     * Result is limited by offset and limit parameters. Full DomainVO is returned
     *
     * @param user authentication token (required)
     * @param criteria domain search criteria, required
     * @param offset, required
     * @param limit maximum number of domains to be returned, required
     * @param orderBy sorting criteria, optional
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract DomainSearchResultVO findDomains(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name="criteria")DomainSearchCriteria criteria,
            @WebParam(name="offset") long offset,
            @WebParam(name="limit") long limit,
            @WebParam(name="sortCriteria")List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Searches for domains matching given criteria in the given order. Domain objects from the result don't carry information about contacts or nameservers.
     * Result is limited by offset and limit parameters. DomainVO is trimmed.
     *
     * @param user authentication token (required)
     * @param criteria domain search criteria, required
     * @param offset, required
     * @param limit maximum number of domains to be returned, required
     * @param orderBy sorting criteria, optional
     * @return
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public abstract DomainSearchResultVO findDomainsTrimmed(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name="criteria")DomainSearchCriteria criteria,
            @WebParam(name="offset") long offset,
            @WebParam(name="limit") long limit,
            @WebParam(name="sortCriteria")List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    @WebMethod
    public abstract DeletedDomainsSearchResultVO findDeletedDomains(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name="criteria")DeletedDomainSearchCriteria criteria,
            @WebParam(name="offset") long offset,
            @WebParam(name="limit") long limit,
            @WebParam(name="sortCriteria")List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;


    /**
     * Searches for transfered domains matching given criteria.
     *
     * @param user authentication token (required)
     * @param criteria transfered domain search criteria
     * @param offset, required
     * @param limit maximum number of domains to be returned, required
     * @param sortBy, sorting criteria ,optional
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     * @return
     */
    @WebMethod
    public abstract DomainSearchResultVO findTransferedDomains(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria")TransferedDomainSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria") List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    /**
     * Moves domains into voluntary NRP 
     * 
     * @param user
     * @param domainNames
     */
    public void enterVoluntaryNRP(
    		@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") String... domainNames);
    
    
    /**
     * Removes domains from voluntary NRP 
     * 
     * @param user
     * @param domainNames
     */
    public void removeFromVoluntaryNRP(
    		@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") String... domainNames);

    @WebMethod
    public DomainSearchResultVO getDomainForRenewalPayment(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "renewalDateType") RenewalDateType renewalDateType,
            @WebParam(name = "criteria") DomainForRenewalCriteriaVO criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria")List<SortCriterion> sortBy)
    throws DomainNotFoundException, NotAdmissiblePeriodException;

    @WebMethod
    public DomainSearchResultVO getDomainsForRenewalFuturePayment(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "yearMonthDay") String yearMonthDay,
            @WebParam(name = "criteria") DomainForRenewalCriteriaVO criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name = "sortCriteria")List<SortCriterion> sortBy)
    throws DomainNotFoundException, NotAdmissiblePeriodException;

    @WebMethod
    public boolean isEventValid(
    		@WebParam(name = "user") AuthenticatedUserVO user,
    		@WebParam(name = "domainName") String domainName,
    		@WebParam(name = "eventName") DsmEventName eventName);
    
    @WebMethod
    public DomainAvailabilityVO checkAvailability(
    		@WebParam(name = "user") AuthenticatedUserVO user,
    		@WebParam(name = "domainName") String domainName);

    @WebMethod
    public boolean checkDomainExists(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName);

    /**
     * Allows to revert the billable domain state to 'billable'
     */
    @WebMethod
    public void revertToBillable(
    		@WebParam(name="user") AuthenticatedUserVO user,
    		@WebParam(name="domainNames") List<String> domainNames);
    
    @WebMethod
    public List<ClassCategoryVO> getClassCategory(
            @WebParam(name = "user") AuthenticatedUserVO user);

    @WebMethod
    public NsReportSearchResultVO getNsReports(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") NsReportSearchCriteria criteria,
            @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit,
            @WebParam(name="sortCriteria")List<SortCriterion> sortBy);

    @WebMethod
    List<String> checkPayAvailable(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames);

    @WebMethod
    void modifyRenewalMode(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames,
            @WebParam(name = "renewalMode") RenewalMode renewalMode);

    @WebMethod
    List<CountryVO> getCountries(
            @WebParam(name = "user") AuthenticatedUserVO user);
    
    @WebMethod
    boolean isCharity(
    		@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainName") String domainName) throws DomainNotFoundException;

}