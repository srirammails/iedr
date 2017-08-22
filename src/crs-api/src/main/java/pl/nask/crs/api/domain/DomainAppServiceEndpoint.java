package pl.nask.crs.api.domain;

import java.text.ParseException;
import java.util.*;

import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.ClassCategoryVO;
import pl.nask.crs.api.vo.CountryVO;
import pl.nask.crs.api.vo.DeletedDomainsSearchResultVO;
import pl.nask.crs.api.vo.DomainAvailabilityVO;
import pl.nask.crs.api.vo.DomainForRenewalCriteriaVO;
import pl.nask.crs.api.vo.DomainSearchResultVO;
import pl.nask.crs.api.vo.DomainVO;
import pl.nask.crs.api.vo.ExtendedDomainInfoVO;
import pl.nask.crs.api.vo.NsReportSearchResultVO;
import pl.nask.crs.api.vo.TransferedDomainSearchResultVO;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.TransferedDomain;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;
/**
 * Endpoint for the {@link DomainAppService}
 * 
 * @author Artur Gniadzik
 *
 */
@WebService(serviceName="CRSDomainAppService", endpointInterface="pl.nask.crs.api.domain.CRSDomainAppService")
public class DomainAppServiceEndpoint extends WsSessionAware implements CRSDomainAppService {

	private DomainAppService service;
    private DomainSearchService domainSearchService;
    private UserSearchService userSearchService;

	public void setService(DomainAppService service) {
		this.service = service;
	}

    public void setDomainSearchService(DomainSearchService domainSearchService) {
        this.domainSearchService = domainSearchService;
    }
    
    public void setUserSearchService(UserSearchService userSearchService) {
		this.userSearchService = userSearchService;
	}

    /* (non-Javadoc)
    * @see pl.nask.crs.api.domain.CRSDomainAppService#view(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String)
    */
    @Override
	public ExtendedDomainInfoVO view (AuthenticatedUserVO user, String domainName) 
	throws AccessDeniedException, DomainNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException
	{
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(domainName, "domain.name");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        ExtendedDomainInfoVO ret = new ExtendedDomainInfoVO(service.view(completeUser, domainName));
        setAdditionalFlags(user, ret.getDomain());
        return ret;
	}
	
	private void setAdditionalFlags(AuthenticatedUserVO user, DomainVO domain) {	
		if (domain.getDsmState().isVoluntaryNRP()) {
			boolean flag = service.isEventValid(user, domain.getName(), DsmEventName.RemoveFromVoluntaryNRP);
			domain.setRemoveFromVoluntaryNRPPossible(flag);						
		}
		
		boolean flag = service.isEventValid(user, domain.getName(), DsmEventName.EnterVoluntaryNRP);
		domain.setEnterVoluntaryNRPPossible(flag);
	}

    @Override
	public DomainSearchResultVO findDomains(AuthenticatedUserVO user, DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException
    {
        ValidationHelper.validate(user);
        validateSession(user);
        filterValues(criteria);
        applyUserRoleFilter(user, criteria);
        ValidationHelper.validate(criteria);
        return new DomainSearchResultVO(domainSearchService.find(criteria, offset, limit, orderBy), true);
    }

    @Override
    public DomainSearchResultVO findDomainsTrimmed(AuthenticatedUserVO user, DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        filterValues(criteria);
        applyUserRoleFilter(user, criteria);
        ValidationHelper.validate(criteria);
        return new DomainSearchResultVO(domainSearchService.find(criteria, offset, limit, orderBy), false);
    }

    private void applyUserRoleFilter(AuthenticatedUserVO user, DomainSearchCriteria criteria) {
    	criteria.setNicHandle(user.getUsername());
    	criteria.setContactType(Arrays.asList("T", "A", "B"));    	
	}

	private void filterValues(DomainSearchCriteria criteria) {
        if (criteria != null)
            criteria.filterValues();
    }

    @Override
    public DeletedDomainsSearchResultVO findDeletedDomains(AuthenticatedUserVO user, DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        if (criteria == null) {
            criteria = new DeletedDomainSearchCriteria();
        }
        criteria.setBillingNH(user.getUsername());
        return new DeletedDomainsSearchResultVO(service.findDeletedDomains(user, criteria, offset, limit, orderBy));
    }

    @Override
    public DomainSearchResultVO findTransferedDomains(AuthenticatedUserVO user, TransferedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return new DomainSearchResultVO(service.findTransferedDomains(user, criteria, offset, limit, sortBy));
    }

    @Override
    public DomainSearchResultVO getDomainForRenewalPayment(AuthenticatedUserVO user, RenewalDateType renewalDateType, DomainForRenewalCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortBy) throws DomainNotFoundException, NotAdmissiblePeriodException {
        ValidationHelper.validate(user);
        validateSession(user);
        Validator.assertNotNull(renewalDateType, "renewalDateType");

        Date startDate = null;
        Date endDate = null;
        Date currDate = new Date();
        switch (renewalDateType) {
            case OVERDUE:
                startDate = endDate = org.apache.commons.lang.time.DateUtils.addDays(currDate, -1);
                break;
            case RENEWAL_TODAY:
                startDate = endDate = currDate;
                break;
            case RENEWAL_THIS_MONTH:
                startDate = DateUtils.startOfMonth(currDate);
                endDate = DateUtils.endOfMonth(currDate);
                break;
            default:
                throw new IllegalArgumentException("Invalid renewal date type: " + renewalDateType);
        }
        return new DomainSearchResultVO(domainSearchService.findDomainsForCurrentRenewal(user.getUsername(), DateUtils.startOfDay(startDate), DateUtils.endOfDay(endDate), criteriaVO.getDomainName(), offset, limit, sortBy));
    }

    @Override
    public DomainSearchResultVO getDomainsForRenewalFuturePayment(AuthenticatedUserVO user, String yearMonthDay, DomainForRenewalCriteriaVO criteriaVO, long offset, long limit, List<SortCriterion> sortBy) throws DomainNotFoundException, NotAdmissiblePeriodException {
        ValidationHelper.validate(user);
        validateSession(user);
        int month;
		try {
			Date date = org.apache.commons.lang.time.DateUtils.parseDate(yearMonthDay, new String[]{"yyyy-MM-dd"});
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            month = c.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Cannot parse date: " + yearMonthDay, e);
		}
        return new DomainSearchResultVO(domainSearchService.findDomainsForFutureRenewal(user.getUsername(), month, criteriaVO.getDomainName(), offset, limit, sortBy));
    }

    @Override
    public void enterVoluntaryNRP(AuthenticatedUserVO user, String... domainNames) {
    	ValidationHelper.validate(user);
    	validateSession(user);
    	service.enterVoluntaryNRP(user, domainNames);
    }
    
    @Override
    public void removeFromVoluntaryNRP(AuthenticatedUserVO user, String... domainNames) {
    	ValidationHelper.validate(user);
    	validateSession(user);
    	service.removeFromVoluntaryNRP(user, domainNames);
    }
    
    @Override
    public boolean isEventValid(AuthenticatedUserVO user, String domainName,
    		DsmEventName eventName) {
    	ValidationHelper.validate(user);
    	validateSession(user);
    	return service.isEventValid(user, domainName, eventName);
    }

    
    @Override
    public DomainAvailabilityVO checkAvailability(AuthenticatedUserVO user, String domainName) {    
        return new DomainAvailabilityVO (service.checkAvailability(user, domainName));
    }

    @Override
    public boolean checkDomainExists(AuthenticatedUserVO user, String domainName) {
        return service.checkDomainExists(user, domainName);
    }

    
    @Override
    public void revertToBillable(AuthenticatedUserVO user,
    		List<String> domainNames) {
    	service.revertToBillable(user, domainNames);
    }
    
    @Override
    public List<ClassCategoryVO> getClassCategory(AuthenticatedUserVO user) {
        ValidationHelper.validate(user);
        validateSession(user);
        return toClassCategoryVOList(service.getClassCategory(user));
    }

    private List<ClassCategoryVO> toClassCategoryVOList(List<ClassCategoryEntity> classCategory) {
        if (Validator.isEmpty(classCategory)) {
            return Collections.emptyList();
        } else {
            List<ClassCategoryVO> ret = new ArrayList<ClassCategoryVO>(classCategory.size());
            for (ClassCategoryEntity entity : classCategory) {
                ret.add(new ClassCategoryVO(entity));
            }
            return ret;
        }
    }

    @Override
    public NsReportSearchResultVO getNsReports(AuthenticatedUserVO user, NsReportSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        ValidationHelper.validate(user);
        validateSession(user);
        return new NsReportSearchResultVO(domainSearchService.getNsReports(user.getUsername(), criteria, offset, limit, sortBy));
    }

    @Override
    public List<String> checkPayAvailable(AuthenticatedUserVO user, List<String> domainNames) {
        ValidationHelper.validate(user);
        validateSession(user);
        List<String> res = new LinkedList<String>();
        for (String domainName: domainNames) {
        	try {
        		List<String> partialRes = service.checkPayAvailable(user, Arrays.asList(domainName));
        		res.addAll(partialRes);
        	} catch (AccessDeniedException e) {
        		res.add(domainName + " (user has no permission for paying for this domain)");
        	}
        }
        return res;
    }

    @Override
    public void modifyRenewalMode(AuthenticatedUserVO user, List<String> domainNames, RenewalMode renewalMode) {
        ValidationHelper.validate(user);
        validateSession(user);
        service.modifyRenewalMode(user, domainNames, renewalMode);
    }

    @Override
    public List<CountryVO> getCountries(AuthenticatedUserVO user) {
    	// user MAY be null here - this method provides static data only
//        ValidationHelper.validate(user);
//        validateSession(user);
        return toCountriesVOList(service.getCountries(user));
    }

    private List<CountryVO> toCountriesVOList(List<Country> countries) {
        if (Validator.isEmpty(countries)) {
            return Collections.emptyList();
        } else {
            List<CountryVO> ret = new ArrayList<CountryVO>(countries.size());
            for (Country country : countries) {
                ret.add(new CountryVO(country));
            }
            return ret;
        }
    }
    
    @Override
	public boolean isCharity(AuthenticatedUserVO user, String domainName) throws DomainNotFoundException {
    	 ValidationHelper.validate(user);
         validateSession(user);
    	return service.isCharity(user, domainName);
    }
}
