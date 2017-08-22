package pl.nask.crs.ticket.services;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;

import java.util.List;

/**
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public interface FailureReasonFactory extends Dictionary<Integer, FailureReason> {

    public List<FailureReason> getFailureReasonsByField(Field dataField);

}
