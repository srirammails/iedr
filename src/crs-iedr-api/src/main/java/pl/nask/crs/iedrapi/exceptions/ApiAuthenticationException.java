package pl.nask.crs.iedrapi.exceptions;


public class ApiAuthenticationException extends IedrApiException {
    public ApiAuthenticationException(Exception e) {
       super(2100, e);
    }
}
