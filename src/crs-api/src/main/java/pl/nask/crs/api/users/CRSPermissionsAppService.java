package pl.nask.crs.api.users;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.InternalLoginUserVO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.Level;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSPermissionsAppService {

    /**
     * 
     * @param user
     * @return 1 for Direct, 2 for Registrar, 3 for SuperRegistrar, 4 for tech or admin contact. 0 for undefined (not a customer)
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws InvalidTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    public int getUserLevel(
            @WebParam(name = "user") AuthenticatedUserVO user) throws AccessDeniedException, UserNotAuthenticatedException, InvalidTokenException, SessionExpiredException;

    @WebMethod
    public List<InternalLoginUserVO> getInternalUsers();

}