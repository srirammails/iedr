package pl.nask.crs.iedrapi.exceptions;

public class DataManagementPolicyViolationException extends IedrApiException {
    public DataManagementPolicyViolationException(Exception e) {
        super(2308, e);
    }

    public DataManagementPolicyViolationException(ReasonCode reasonCode) {
        super(2308, reasonCode);
    }
    
    public DataManagementPolicyViolationException(ReasonCode reasonCode, Exception cause) {
        super(2308, reasonCode, cause);
    }

    public DataManagementPolicyViolationException(ReasonCode reasonCode, Value value) {
        super(2308, reasonCode, value);
    }
    
    public DataManagementPolicyViolationException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2308, reasonCode, cause, value);
    }

}
