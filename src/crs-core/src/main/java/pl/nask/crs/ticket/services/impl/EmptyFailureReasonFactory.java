package pl.nask.crs.ticket.services.impl;

import pl.nask.crs.ticket.services.FailureReasonFactory;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;

import java.util.List;

/**
 * @author Piotr Tkaczyk
 */
public class EmptyFailureReasonFactory implements FailureReasonFactory {

    public List<FailureReason> getFailureReasonsByField(Field dataField) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FailureReason getEntry(Integer integer) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<FailureReason> getEntries() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
