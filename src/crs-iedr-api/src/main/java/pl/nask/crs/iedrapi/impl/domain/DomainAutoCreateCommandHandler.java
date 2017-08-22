package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.CreDataType;
import ie.domainregistry.ieapi_domain_1.CreateType;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.commons.exceptions.CreateDomainException;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainAutoCreateCommandHandler extends AbstractDomainCommandHandler<CreateType>{

    private ApplicationConfig appConfig;

    public DomainAutoCreateCommandHandler(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
    	AuthenticatedUserVO user = auth.getUser();
        DomainValidationHelper.commandPlainCheck(appConfig, command, DOMAIN_NAMESPACE);
        ApiValidator.assertNoError(callback);
        RegistrationRequestVO request = prepareRegistrationRequest(command);

        try {        	
        	getCommonAppService().registerGIBODomain(user, request);
        	Domain domain = getDomainAppService().view(user, request.getDomainName()).getDomain();

        	CreDataType res = new CreDataType();
        	res.setName(domain.getName());
        	res.setCreDate(domain.getRegistrationDate());
        	res.setExDate(domain.getRenewDate());
        	
        	return ResponseTypeFactory.success(res);
        } catch (DomainNotFoundException e) {
        	// shall never happen
        	throw new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST);
        } catch (Exception e) {
            throw mapException(e);
        }
    }
}
