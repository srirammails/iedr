package pl.nask.crs.iedrapi.exceptions;

public class ObjectDoesNotExistException extends IedrApiException {

    public ObjectDoesNotExistException(ReasonCode reasonCode) {
        super(2303, reasonCode);
    }

    public ObjectDoesNotExistException(ReasonCode reasonCode, Exception cause) {
        super(2303, reasonCode, cause);
    }

    public ObjectDoesNotExistException(ReasonCode reasonCode, Value value) {
        super(2303, reasonCode, value);
    }
    
    public ObjectDoesNotExistException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2303, reasonCode, cause, value);
    }
}
