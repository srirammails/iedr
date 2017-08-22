package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

import java.util.List;

public abstract class SimpleDomainFieldChangeWrapper<T> {

    private FailureReasonFactory frFactory;
    private Field dataField;
    private SimpleDomainFieldChange<T> orig;
    private final DomainOperationType opType;

    public SimpleDomainFieldChangeWrapper(SimpleDomainFieldChange<T> orig,
                                          FailureReasonFactory frFactory, Field dataField, DomainOperationType opType) {

        this.orig = orig;
        this.frFactory = frFactory;
        this.dataField = dataField;
        this.opType = opType;
    }

    protected SimpleDomainFieldChange<T> getOrig() {
        return orig;
    }

    public void setFailureReasonId(int id) {
        if (id == 0)
            orig.setFailureReason(null);
        else
            orig.setFailureReason(frFactory.getEntry(id));
    }

    public int getFailureReasonId() {
        if (orig.getFailureReason() == null)
            return 0;
        else
            return orig.getFailureReason().getId();
    }

    public List<FailureReason> getFailureReasonList() {
        return frFactory.getFailureReasonsByField(dataField);
    }

    public FailureReason getFailureReason() {
        return orig.getFailureReason();
    }

    public abstract T getNewValue();

    public abstract void setNewValue(T value);

    public boolean isModification() {
        return ((opType == DomainOperationType.MOD) || (opType == DomainOperationType.XFER)) && orig.isModification();
    }
}
