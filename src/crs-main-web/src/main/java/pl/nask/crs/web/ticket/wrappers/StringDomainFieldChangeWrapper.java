package pl.nask.crs.web.ticket.wrappers;

import org.apache.commons.lang.StringEscapeUtils;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.FailureReasonFactory;

/**
 * @author Patrycja Wegrzynowicz
 */
public class StringDomainFieldChangeWrapper extends
        SimpleDomainFieldChangeWrapper<String> {

    public StringDomainFieldChangeWrapper(SimpleDomainFieldChange<String> orig,
                                          FailureReasonFactory frFactory, Field dataField, DomainOperationType opType) {
        super(orig, frFactory, dataField, opType);
    }

    @Override
    public String getNewValue() {
        return StringEscapeUtils.escapeHtml(getOrig().getNewValue());
    }

    @Override
    public void setNewValue(String newValue) {
        getOrig().setNewValue(newValue);
    }

    public String getValue() {
        return getNewValue();
    }

    public void setValue(String value) {
        setNewValue(value);
    }
}
