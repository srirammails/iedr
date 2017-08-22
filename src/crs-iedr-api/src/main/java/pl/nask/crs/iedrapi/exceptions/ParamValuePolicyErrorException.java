package pl.nask.crs.iedrapi.exceptions;

public class ParamValuePolicyErrorException extends IedrApiException {
    public ParamValuePolicyErrorException(ReasonCode reasonCode) {
        super(2102, reasonCode);
    }
    
    public ParamValuePolicyErrorException(ReasonCode reasonCode, Exception cause) {
        super(2102, reasonCode, cause);
    }
    
    public ParamValuePolicyErrorException(String msg) {
        super(2102, msg);
    }

    public ParamValuePolicyErrorException(ReasonCode reasonCode, String msg) {
        super(2102, reasonCode, msg);
    }

    public ParamValuePolicyErrorException(ReasonCode reasonCode, Value value) {
        super(2102, reasonCode, value);
    }
    
    public ParamValuePolicyErrorException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2102, reasonCode, cause, value);
    }

    public ParamValuePolicyErrorException(ReasonCode reasonCode, Value value, String msg) {
        super(2102, reasonCode, value, msg);
    }
}
