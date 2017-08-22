package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import pl.nask.crs.app.domains.wrappers.DnsWrapper;
import pl.nask.crs.commons.config.ApplicationConfig;

public class NsValidator extends FieldValidatorSupport {
    private final ApplicationConfig appConfig;

    public NsValidator(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
        setDefaultMessage("NS count out of allowed range: ");
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        final DnsWrapper dnsWrapper = (DnsWrapper) fieldValue;
        final int nsCount = dnsWrapper.getCurrentNameservers().size();
        if (nsCount < appConfig.getNameserverMinCount() || nsCount > appConfig.getNameserverMaxCount()) {
            addFieldError(getFieldName(), object);
        }
    }
}
