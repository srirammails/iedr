package pl.nask.crs.api.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EnchantedEntityClassVO;
import pl.nask.crs.api.vo.EntityCategoryVO;
import pl.nask.crs.api.vo.EntityClassVO;
import pl.nask.crs.api.vo.FailureReasonsEditVO;
import pl.nask.crs.api.vo.StatusVO;
import pl.nask.crs.api.vo.TicketSearchResultVO;
import pl.nask.crs.api.vo.TicketVO;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.exceptions.ContactNotActiveException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.statuses.Status;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.impl.TicketSortCriteria;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;

@WebService(serviceName = "CRSTicketAppService", endpointInterface = "pl.nask.crs.api.ticket.CRSTicketAppService")
public class TicketAppServiceEndpoint extends WsSessionAware implements CRSTicketAppService {
    private static final String DEFAULT_HOSTMASTERS_REMARK = "changed with NRC (crs-ws)";
    private TicketAppService service;
    private TicketSearchService ticketSearchService;
    private UserSearchService userSearchService;

    private EntityClassFactory entityClassFactory;

    public void setService(TicketAppService service) {
        this.service = service;
    }

    public void setTicketSearchService(TicketSearchService ticketSearchService) {
        this.ticketSearchService = ticketSearchService;
    }

    public void setEntityClassFactory(EntityClassFactory entityClassFactory) {
        this.entityClassFactory = entityClassFactory;
    }
    
    public void setUserSearchService(UserSearchService userSearchService) {
		this.userSearchService = userSearchService;
	}

    /* (non-Javadoc)
    * @see pl.nask.crs.api.ticket.CRSTicketAppService#view(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
    */
    @Override
	public TicketVO view(AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new TicketVO(service.view(completeUser, ticketId));
    }

    @Override
	public TicketVO viewTicketForDomain(AuthenticatedUserVO user, String domainName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName(domainName);
        List<Ticket> ticketList = ticketSearchService.find(criteria, 0, 10, null).getResults();
        //TODO: is it possible to find more tickets and what to do with it ?
        return ticketList.size() == 0 ? null : new TicketVO(ticketList.get(0));
    }


   /* (non-Javadoc)
    * @see pl.nask.crs.api.ticket.CRSTicketAppService#edit(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
    */
    @Override
	public TicketVO edit(AuthenticatedUserVO user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return new TicketVO(service.edit(completeUser, ticketId));
    }

   
   
    /* (non-Javadoc)
    * @see pl.nask.crs.api.ticket.CRSTicketAppService#update(pl.nask.crs.api.vo.AuthenticatedUserVO, long, pl.nask.crs.api.vo.FailureReasonsVO, java.lang.String, boolean)
    */
    @Override
	public void update(AuthenticatedUserVO user, long ticketId, FailureReasonsEditVO domainOperation, String remark, boolean clikPaid)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException, TicketAlreadyCheckedOutException,
            ContactNotActiveException, ContactNotFoundException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException, TicketEmailException, TicketNameserverException {
        ValidationHelper.validate(user);
        ValidationHelper.validate(domainOperation);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);

        TicketModel ticket = service.edit(completeUser, ticketId);
        DomainOperation top = ticket.getTicket().getOperation();
        DomainOperation domainOp = domainOperation.makeDomainOperations(top.getType(), top.getRenewalDate());

        try {
            service.simpleUpdate(completeUser, ticketId, domainOp, remark, DEFAULT_HOSTMASTERS_REMARK, clikPaid, true);
            service.adminRenew(completeUser, ticketId);
        } catch (TicketEditFlagException e) {
            // Should never happen because forceUpdate=true indicate ticket edit flag validation will be skipped.
            throw new IllegalStateException(e);
        } catch (TicketCheckedOutToOtherException e) {
        	throw new IllegalStateException(e);
        } catch (TicketNotCheckedOutException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
	public TicketSearchResultVO find(AuthenticatedUserVO user, TicketSearchCriteria searchCriteria, long offset, long limit, List<SortCriterion> sortCriteria)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        TicketSortCriteria.validate(sortCriteria);
        searchCriteria.filterValues();

        User u = userSearchService.get(user.getUsername());
        if (u.hasGroup(Level.Direct) || u.hasGroup(Level.Registrar) || u.hasGroup(Level.SuperRegistrar)) {
        	searchCriteria.setBillNicHandle(user.getUsername());
        } else {
        	searchCriteria.setCreatorNh(user.getUsername());
        }

        LimitedSearchResult<Ticket> sres = ticketSearchService.find(searchCriteria, offset, limit, sortCriteria);
        List<TicketVO> list = new ArrayList<TicketVO>();
        for (Ticket t : sres.getResults()) {
            list.add(new TicketVO(t));
        }

        TicketSearchResultVO res = new TicketSearchResultVO(list, sres.getTotalResults());
        return res;
    }

    List<StatusVO> toStatusVOList(List<? extends Status> statuses) {
        if (statuses == null || statuses.size() == 0)
            return new ArrayList<StatusVO>(0);
        List<StatusVO> ret = new ArrayList<StatusVO>();
        for (Status status : statuses) {
            ret.add(new StatusVO(status));
        }
        return ret;
    }

    @Override
	public List<EnchantedEntityClassVO> getClassesWithCategories(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toEnchantedEntityClassVOList(entityClassFactory.getEntries());
    }

    private List<EnchantedEntityClassVO> toEnchantedEntityClassVOList(List<EnchancedEntityClass> enchancedEntityClassList) {
        if (Validator.isEmpty(enchancedEntityClassList))
            return new ArrayList<EnchantedEntityClassVO>(0);
        List<EnchantedEntityClassVO> ret = new ArrayList<EnchantedEntityClassVO>();
        for (EnchancedEntityClass entityClass : enchancedEntityClassList) {
            ret.add(new EnchantedEntityClassVO(entityClass));
        }
        return ret;
    }

    @Override
	public List<EntityClassVO> getClasses(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toEntityClassVOList(entityClassFactory.getEntries());
    }

    private List<EntityClassVO> toEntityClassVOList(List<EnchancedEntityClass> enchancedEntityClassList) {
        if (Validator.isEmpty(enchancedEntityClassList))
            return new ArrayList<EntityClassVO>(0);
        List<EntityClassVO> ret = new ArrayList<EntityClassVO>();
        for (EnchancedEntityClass enchancedEntityClass : enchancedEntityClassList) {
            ret.add(new EntityClassVO(enchancedEntityClass.getEntityClass()));
        }
        return ret;
    }

    @Override
	public List<EntityCategoryVO> getCategoriesForClass(AuthenticatedUserVO user, long classId)
            throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return toEntityCategoryVOList(entityClassFactory.getEntry(classId));
    }

    private List<EntityCategoryVO> toEntityCategoryVOList(EnchancedEntityClass enchancedEntityClass) {
        if (enchancedEntityClass == null)
            return new ArrayList<EntityCategoryVO>(0);
        List<EntityCategoryVO> ret = new ArrayList<EntityCategoryVO>();
        for (EntityCategory entityCategory : enchancedEntityClass.getCategories()) {
            ret.add(new EntityCategoryVO(entityCategory));
        }
        return ret;
    }
}
