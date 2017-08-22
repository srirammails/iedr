package pl.nask.crs.api.vo;

import java.util.List;

import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferRequestVO extends AbstractTicketRequestVO {

    //TODO holder, class, category, admin contacts are redundant ?

    private static final String CHARITY_MARKER = "charityMarker";

    public static final int DEFAULT_UNSET_PERIOD = 0;

    private int period = DEFAULT_UNSET_PERIOD;

    private PeriodType periodType = PeriodType.Y;

    private boolean status;

    private String authCode;

    @Override
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

    public void setPeriod(int period) {
   		this.period = period;
   	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean isCharity() {
        return period == DEFAULT_UNSET_PERIOD;
    }

    @Override
    public String getCharityCode() {
        return null;
    }

    @Override
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Ticket toTicket(String creatorNh, long accountId, String billingContactNh, DomainOperation.DomainOperationType ticketType) {
        Period p = getRegPeriod();
        String charityCode = null;
        if (p.getYears() == DEFAULT_UNSET_PERIOD) {
            p = null;
            charityCode = CHARITY_MARKER;
        }

        AdminStatus adminStatus = AdminStatusEnum.PASSED; //status ? AdminStatusEnum.HOLD_REGISTRAR_APPROVAL : AdminStatusEnum.HOLD_PAPERWORK;

        return _toTicket(creatorNh, accountId, billingContactNh, ticketType, p, charityCode, adminStatus);
    }
}
