package pl.nask.crs.iedrapi.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class BillingFailureException extends IedrApiException {

	private static final long serialVersionUID = 8918856383119364992L;

	public BillingFailureException() {
        super(2104);
    }

    public BillingFailureException(ReasonCode code) {
        super(2104, code);
    }

	public BillingFailureException(ReasonCode code, Exception cause) {
		super(2104, code, cause);
	}

    public BillingFailureException(ReasonCode code, String msg) {
        super(2104, code, msg);
    }

}
