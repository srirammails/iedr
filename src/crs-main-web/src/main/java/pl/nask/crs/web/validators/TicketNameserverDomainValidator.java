package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverDomainValidator extends AbstractTicketNameserverValidator {
    @Override
    protected void validateNameserver(Object object, TicketWrapper t, NameserverWrapper ns, int index) {
        String value = ns.getName();
        if (Validator.isEmpty(value)) {
            addFieldError(getFieldName() + ".nameserverWrappers[" + index + "]" + ".name", object);
        } else {
            try {
                DomainNameValidator.validateName(value);
            } catch (InvalidDomainNameException e) {
                addFieldError(getFieldName() + ".nameserverWrappers[" + index + "]" + ".name", object);
            }
        }
    }
}
