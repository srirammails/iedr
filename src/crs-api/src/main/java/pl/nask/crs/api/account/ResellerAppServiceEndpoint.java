package pl.nask.crs.api.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AccountVO;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.InternalRegistrarVO;
import pl.nask.crs.api.vo.NicHandleEditVO;
import pl.nask.crs.api.vo.StatusVO;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.statuses.Status;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(serviceName="CRSResellerService", endpointInterface="pl.nask.crs.api.account.CRSResellerAppService")
public class ResellerAppServiceEndpoint extends WsSessionAware implements CRSResellerAppService {
	private AccountAppService service;	
	private AccountSearchService searchService;
		

    /* (non-Javadoc)
    * @see pl.nask.crs.api.account.CRSResellerAppService#get(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
    */
	@Override
	public AccountVO get(AuthenticatedUserVO user, long id)
    	throws AccessDeniedException, AccountNotFoundException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException
    {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
		return new AccountVO(service.get(completeUser, id));
	}

    @Override
    public List<InternalRegistrarVO> getRegistrarsForLogin(AuthenticatedUserVO user) throws UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        return toInternalRegistrarVO(searchService.getRegistrarsForLogin());
    }

    @Override
    public NewAccountVO createDirectAccount(NicHandleEditVO nicHandleDetails, String password, boolean useTfa) throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException, AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException, EmptyPasswordException, PasswordTooEasyException {
    	return new NewAccountVO(service.createDirectAccount(nicHandleDetails.toNewNicHandle(), password, useTfa));    	
    }
    
	/* (non-Javadoc)
     * @see pl.nask.crs.api.account.CRSResellerAppService#setService(pl.nask.crs.app.accounts.AccountAppService)
     */
	public void setService(AccountAppService accountAppService) {
		this.service = accountAppService;
	}
	
	public void setSearchService(AccountSearchService searchService) {
		this.searchService = searchService;
	}

    List<StatusVO> toStatusVOList(List<? extends Status> statuses)  {
    	if (Validator.isEmpty(statuses))
    		return new ArrayList<StatusVO>(0);
    	List<StatusVO> ret = new ArrayList<StatusVO>();
    	for (Status status : statuses) {
    		ret.add(new StatusVO(status));
    	}
    	return ret;
    }

    private List<InternalRegistrarVO> toInternalRegistrarVO(List<InternalRegistrar> internalRegistrars) {
        if (Validator.isEmpty(internalRegistrars)) {
            return Collections.emptyList();
        } else  {
            List<InternalRegistrarVO> ret = new ArrayList<InternalRegistrarVO>(internalRegistrars.size());
            for (InternalRegistrar registrar : internalRegistrars) {
                ret.add(new InternalRegistrarVO(registrar));
            }
            return ret;
        }
    }
}
