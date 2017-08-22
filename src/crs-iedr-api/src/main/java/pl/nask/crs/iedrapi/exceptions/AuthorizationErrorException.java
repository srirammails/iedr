package pl.nask.crs.iedrapi.exceptions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public class AuthorizationErrorException extends IedrApiException {
    public AuthorizationErrorException() {
        super(2201);
    }

    public AuthorizationErrorException(ReasonCode reasonCode) {
        super(2201, reasonCode);
    }

	public AuthorizationErrorException(AccessDeniedException e) {
		super(2201, e);
	}

	 public AuthorizationErrorException(ReasonCode reasonCode, Exception cause) {
		super(2201, reasonCode, cause);
	 }

    public AuthorizationErrorException(ReasonCode reasonCode, Value value) {
        super(2201, reasonCode, value);
    }

    public AuthorizationErrorException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2201, reasonCode, cause, value);
    }
}
