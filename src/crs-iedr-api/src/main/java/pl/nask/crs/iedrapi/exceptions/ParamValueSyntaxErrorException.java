package pl.nask.crs.iedrapi.exceptions;

public class ParamValueSyntaxErrorException extends IedrApiException {
    public ParamValueSyntaxErrorException() {
        super(2004);
    }

    public ParamValueSyntaxErrorException(ReasonCode reasonCode) {
        super(2004, reasonCode);
    }

    public ParamValueSyntaxErrorException(ReasonCode reasonCode, Value value) {
        super(2004, reasonCode, value);
    }
    
    public ParamValueSyntaxErrorException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2004, reasonCode, cause, value);
    }
}
