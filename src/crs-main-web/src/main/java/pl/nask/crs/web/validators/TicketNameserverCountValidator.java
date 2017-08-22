package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverCountValidator extends FieldValidatorSupport {

    private final ApplicationConfig appConfig;

    public TicketNameserverCountValidator(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
        setDefaultMessage("NS count out of allowed range: ");
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final int nameserversCount = ticketWrapper.getNewNameserverWrappers().size();
        if (nameserversCount < appConfig.getNameserverMinCount() || nameserversCount > appConfig.getNameserverMaxCount()) {
            addFieldError(getFieldName() + ".nameservers", object);
        }
    }

}
