package pl.nask.crs.iedrapi.exceptions;

import ie.domainregistry.ieapi_1.ResponseType;
import pl.nask.crs.iedrapi.ResponseTypeFactory;

public abstract class IedrApiException extends Exception {
    protected int resultCode; 
    protected ReasonCode reasonCode;
    protected String reasonMessage;
    protected Value value;
    
    public IedrApiException(int resultCode) {
        this.resultCode = resultCode;
    }
    
    public IedrApiException(int resultCode, ReasonCode reasonCode) {
        this.resultCode = resultCode;
        this.reasonCode = reasonCode;
    }

    protected IedrApiException(int resultCode, ReasonCode reasonCode, Value value) {
        this.resultCode = resultCode;
        this.reasonCode = reasonCode;
        this.value = value;
    }

    public IedrApiException(int resultCode, ReasonCode reasonCode, Exception cause) {
    	super(cause);
        this.resultCode = resultCode;
        this.reasonCode = reasonCode;
    }

    public IedrApiException(int resultCode, ReasonCode reasonCode, String reasonMessage) {
        this.resultCode = resultCode;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
    }

    public IedrApiException(int resultCode, ReasonCode reasonCode, Value value, String reasonMessage) {
        this.resultCode = resultCode;
        this.reasonCode = reasonCode;
        this.value = value;
        this.reasonMessage = reasonMessage;
    }

    public IedrApiException(int resultCode, Exception cause) {
        super(cause);
        this.resultCode = resultCode;
    }
    
    public IedrApiException(int resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
    
    public IedrApiException(int resultCode, Exception cause, String message) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public IedrApiException(int resultCode, ReasonCode reasonCode, Exception cause, Value value) {
    	this(resultCode, reasonCode, cause);
    	this.value = value;
	}

	public ResponseType getResponseType() {
        return ResponseTypeFactory.createResponse(resultCode, reasonCode, reasonMessage, value);
    }

}
