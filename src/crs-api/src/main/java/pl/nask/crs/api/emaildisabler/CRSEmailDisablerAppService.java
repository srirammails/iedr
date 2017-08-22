package pl.nask.crs.api.emaildisabler;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EmailDisablerSearchResultVO;
import pl.nask.crs.api.vo.EmailDisablerSuppressResultVO;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSEmailDisablerAppService {

    @WebMethod
    EmailDisablerSearchResultVO getAll(
            @WebParam(name = "user") AuthenticatedUserVO user
    );

    @WebMethod
    EmailDisablerSuppressResultVO modifySuppressionMode(
        @WebParam(name = "disabledIds") List<EmailDisablerSuppressInfo> emaildisablerInfo,
        @WebParam(name = "user") AuthenticatedUserVO user
    );

}
