package pl.nask.crs.iedrapi.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class ObjectStatusProhibitsOperationException extends IedrApiException{

    public ObjectStatusProhibitsOperationException(ReasonCode code) {
        super(2304, code);
    }

    public ObjectStatusProhibitsOperationException(ReasonCode reasonCode, Value value) {
        super(2304, reasonCode, value);
    }
    
    public ObjectStatusProhibitsOperationException(ReasonCode reasonCode, Value value, Exception cause) {
        super(2304, reasonCode, cause, value);
    }
}
