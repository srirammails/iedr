package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.validator.IPAddressValidator;
import pl.nask.crs.commons.dns.validator.InvalidIPAddressException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverIpv4Validator extends AbstractTicketNameserverValidator {

    @Override
    protected void validateNameserver(Object object, TicketWrapper t, NameserverWrapper ns, int index) {
        String ipv4 = ns.getIpv4();
        if (!Validator.isEmpty(ipv4)) {
            try {
                IPAddressValidator.getInstance().validate(ipv4);
            } catch (InvalidIPAddressException e) {
                addFieldError(getFieldName() + ".nameserverWrappers[" + index + "]" + ".ipv4", object);
            }
        }
    }
}
