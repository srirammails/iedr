package pl.nask.crs.api.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.InternalLoginUserVO;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.InternalLoginUser;
import pl.nask.crs.user.Level;

@WebService(serviceName="CRSPermissionsAppService", endpointInterface="pl.nask.crs.api.users.CRSPermissionsAppService")
public class UserAppServiceEndpoint extends WsSessionAware implements CRSPermissionsAppService {

	private UserAppService service;
	
	public void setService(UserAppService service) {
		this.service = service;
	}

    @Override
    public int getUserLevel(AuthenticatedUserVO user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException {
        ValidationHelper.validate(user);
        AuthenticatedUser u = validateSessionAndRetrieveFullUserInfo(user, false);
        return service.getUserLevel(u);
    }

    @Override
    public List<InternalLoginUserVO> getInternalUsers() {
        return toInternalLoginUserVOList(service.getInternalUsers());
    }

    private List<InternalLoginUserVO> toInternalLoginUserVOList(List<InternalLoginUser> internalUsers) {
        if (Validator.isEmpty(internalUsers)) {
            return Collections.emptyList();
        } else {
            List<InternalLoginUserVO> ret = new ArrayList<InternalLoginUserVO>(internalUsers.size());
            for (InternalLoginUser user : internalUsers) {
                ret.add(new InternalLoginUserVO(user));
            }
            return ret;
        }
    }

}
