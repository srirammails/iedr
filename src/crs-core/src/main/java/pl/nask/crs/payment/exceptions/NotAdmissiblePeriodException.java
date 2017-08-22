package pl.nask.crs.payment.exceptions;


/**
 * @author: Marcin Tkaczyk, Artur Gniadzik
 */
public class NotAdmissiblePeriodException extends Exception {

	private static final long serialVersionUID = 7658926540993430898L;
	private Integer period;
	private String periodType;

	public NotAdmissiblePeriodException(int period) {
		this.period = period;
	}

	public NotAdmissiblePeriodException(String message) {
        super(message);
    }

    public NotAdmissiblePeriodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAdmissiblePeriodException(Throwable cause) {
        super(cause);
    }

	public NotAdmissiblePeriodException(int period, String periodType) {
		this(period);
		this.periodType = periodType;
	}

	public int getPeriod() {
		return period;
	}
	
	public String getPeriodType() {
		return periodType;
	}
	
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (period != null)
			msg += ", period=" + period;
		
		if (periodType != null)
			msg += ", periodType=" + periodType;
		
		return msg;
	}
}
