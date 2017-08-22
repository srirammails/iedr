package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketNameserverDuplicationValidator extends FieldValidatorSupport {
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        Set<String> names = new HashSet<String>();
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final List<NameserverWrapper> nameservers = ticketWrapper.getNewNameserverWrappers();

        for (int i = 0; i < nameservers.size(); i++) {
            NameserverWrapper ns = nameservers.get(i);
            final String name = ns.getName();
            if (names.contains(name)) {
                addFieldError(getFieldName() + ".nameserverWrappers[" + i + "].name", object);
            }
            names.add(name);
        }
    }
}
