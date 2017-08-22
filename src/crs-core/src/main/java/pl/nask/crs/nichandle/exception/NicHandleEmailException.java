package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleEmailException extends NicHandleException {
  
    private final String nicHandleId;

    public NicHandleEmailException(String nicHandleId, Throwable cause) {
        super("Cannot send confirmation email, nh=" + nicHandleId + " ,reason=" + cause.getMessage(), cause);
        this.nicHandleId = nicHandleId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }        
}
