package pl.nask.crs.iedrapi.exceptions;

public class RequiredParameterMissingException extends IedrApiException {
    private String paramName;

    public String getParamName() {
        return paramName;
    }

    @Deprecated
    public RequiredParameterMissingException(String paramName) {
        super(2002);
        this.paramName = paramName;
    }        

    public RequiredParameterMissingException(ReasonCode reasonCode) {
        super(2002, reasonCode);
    }
    
    public RequiredParameterMissingException(ReasonCode reasonCode, Exception cause) {
        super(2002, reasonCode, cause);
    }

    
}
