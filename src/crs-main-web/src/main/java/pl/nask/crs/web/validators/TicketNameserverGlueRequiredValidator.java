package pl.nask.crs.web.validators;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverGlueRequiredValidator extends AbstractTicketNameserverValidator {
    @Override
    protected void validateNameserver(Object o, TicketWrapper ticketWrapper, NameserverWrapper ns, int i) {
        final String name = ns.getName().toLowerCase();
        final String ipv4 = ns.getIpv4();
        final String domain = ticketWrapper.getDomainName().getNewValue().toLowerCase();
        if ((name.equals(domain) || name.endsWith("." + domain)) && Validator.isEmpty(ipv4)) {
            addFieldError(getFieldName() + ".nameserverWrappers[" + i + "].ipv4", o);
        }
    }
}
