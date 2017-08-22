package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

import java.util.List;

public abstract class AbstractTicketNameserverValidator extends FieldValidatorSupport{

    @Override
    public void validate(Object o) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, o);
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final List<NameserverWrapper> newNameserverWrappers = ticketWrapper.getNewNameserverWrappers();
        for (int i = 0; i < newNameserverWrappers.size(); i++) {
            NameserverWrapper nsWrapper = newNameserverWrappers.get(i);
            validateNameserver(o, ticketWrapper, nsWrapper, i);
        }
    }

    protected abstract void validateNameserver(Object o, TicketWrapper t, NameserverWrapper ns, int i);
}
