package pl.nask.crs.api.vo;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;

public class RegistrationRequestVO extends AbstractTicketRequestVO {
	
	public static final int DEFAULT_PERIOD = 1;

    private String charityCode;
    
    private int period = DEFAULT_PERIOD;
    
    private PeriodType periodType = PeriodType.Y;

		/* (non-Javadoc)
	 * @see pl.nask.crs.api.vo.RegistrationRequest#getCharityCode()
	 */
	public String getCharityCode() {
		return charityCode;
	}

	public void setCharityCode(String charityCode) {
		this.charityCode = charityCode;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Period getRegPeriod () {
		switch (periodType) {
        case M:
        	return Period.fromMonths(period);
        case Y:
        	return Period.fromYears(period);
        default:
        	throw new IllegalStateException("Unhandled period type: " + periodType);
        }
	}

	/* (non-Javadoc)
	 * @see pl.nask.crs.api.vo.RegistrationRequest#getPeriodType()
	 */
	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	/* (non-Javadoc)
	 * @see pl.nask.crs.api.vo.RegistrationRequest#isCharity()
	 */
	public boolean isCharity() {
		return !Validator.isEmpty(charityCode);
	}

    @Override
    public String getAuthCode() {
        /* Should never be called.
         * Added to avoid creating too many additional classes.
         * TransferDomain class uses an object of the TicketRequest superclass and the method is called there.
         * If more methods are ever added which don't correspond to TicketRequest interface, it should be separated.
         * (changes by Wojciech Wąs)
         */
        throw new RuntimeException("Trying to get an authcode from registration ticket");
    }

	/* (non-Javadoc)
	 * @see pl.nask.crs.api.vo.RegistrationRequest#toTicket(java.lang.String, long, java.lang.String)
	 */
	public Ticket toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType) {
        Period p = getRegPeriod();

        return _toTicket(creatorNh, accountId, billingContactNh, ticketType, p, charityCode);
	}
}
