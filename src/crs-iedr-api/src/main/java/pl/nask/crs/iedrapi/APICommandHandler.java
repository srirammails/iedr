package pl.nask.crs.iedrapi;

import ie.domainregistry.ieapi_1.ResponseType;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public interface APICommandHandler<T> {

    ResponseType handle(AuthData auth, T command, ValidationCallback callback) throws AccessDeniedException, IedrApiException;

}
