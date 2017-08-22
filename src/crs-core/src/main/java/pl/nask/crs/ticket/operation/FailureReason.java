package pl.nask.crs.ticket.operation;

import pl.nask.crs.statuses.Status;

/**
 * @author Kasia Fulara
 */
public interface FailureReason extends Status, FailureCondition {

    public int getDataField();

}
