package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.CreateType;

import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainCreateCommandHandler extends AbstractDomainCommandHandler<CreateType> {

    private ApplicationConfig appConfig;

    public DomainCreateCommandHandler(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback) throws IedrApiException {
    	AuthenticatedUser user = auth.getUser();

        DomainValidationHelper.commandPlainCheck(appConfig, command, DOMAIN_NAMESPACE);
        ApiValidator.assertNoError(callback);
        
        RegistrationRequestVO request = prepareRegistrationRequest(command);    
        
        
        try {
        	long ticketId = getCommonAppService().registerDomain(user, request, null);
        	return ResponseTypeFactory.success(prepareResponse(command.getName().toLowerCase(), ticketId));
		} catch (PaymentException e) {
			throw mapPaymentException(e);
		} catch (Exception e) {
            throw mapException(e);
        }
    }
}