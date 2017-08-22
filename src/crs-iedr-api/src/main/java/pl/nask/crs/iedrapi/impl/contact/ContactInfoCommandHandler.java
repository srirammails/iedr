package pl.nask.crs.iedrapi.impl.contact;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.SIDType;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class ContactInfoCommandHandler extends AbstractContactCommandHandler<SIDType> {

    @Override
    public ResponseType handle(AuthData auth, SIDType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        String nhId = command.getId();
        NicHandle res = findValidNh(auth.getUser(), nhId);

        return ResponseTypeFactory.success(ContactConversionHelper.makeInfo(res));                        
    }

}
