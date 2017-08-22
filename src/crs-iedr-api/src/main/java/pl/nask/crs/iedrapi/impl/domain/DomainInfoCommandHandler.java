package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.SNameType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainInfoCommandHandler extends AbstractDomainCommandHandler<SNameType> {

    @Override
    public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        String name = command.getName();
        boolean inclAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCode());
        boolean forceGen = TypeConverter.commandFieldToBoolean(command.isAuthCodeForceGeneration());

        if (forceGen) {
            try {
                getCommonAppService().generateOrProlongAuthCode(auth.getUser(), name);
            } catch (Exception e) {
                throw mapException(e);
            }
        }
        Domain res = findDomain(auth.getUser(), name);
        validateAccountId(getAccountId(auth.getUser()), res);
        return ResponseTypeFactory.success(DomainConversionHelper.makeInfo(res, inclAuthCode));
    }
}
