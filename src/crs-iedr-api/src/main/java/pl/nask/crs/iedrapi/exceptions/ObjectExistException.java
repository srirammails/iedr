package pl.nask.crs.iedrapi.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class ObjectExistException extends IedrApiException {
    public ObjectExistException() {
        super(2302);
    }

    public ObjectExistException(ReasonCode reasonCode) {
        super(2302, reasonCode);
    }

    public ObjectExistException(ReasonCode reasonCode, Value value) {
        super(2302, reasonCode, value);
    }
    
    public ObjectExistException(ReasonCode reasonCode, Value value, Exception e) {
        super(2302, reasonCode, e, value);
    }
}
