package pl.nask.crs.iedrapi.exceptions;

public class ParameterValueRangeErrorException extends IedrApiException {

    public ParameterValueRangeErrorException() {
        super(2003);
    }

    public ParameterValueRangeErrorException(ReasonCode code) {
        super(2003, code);
    }

    public ParameterValueRangeErrorException(ReasonCode reasonCode, Value value) {
        super(2003, reasonCode, value);
    }
    
    public ParameterValueRangeErrorException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2003, reasonCode, cause, value);
    }
}
