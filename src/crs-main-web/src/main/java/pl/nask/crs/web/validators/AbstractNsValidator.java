package pl.nask.crs.web.validators;

import pl.nask.crs.app.domains.wrappers.DnsWrapper;
import pl.nask.crs.app.domains.wrappers.NameserverStub;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import java.util.List;

public abstract class AbstractNsValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        final DnsWrapper dnsWrapper = (DnsWrapper) fieldValue;
        final List<NameserverStub> currentNameservers = dnsWrapper.getCurrentNameservers();
        for (int i = 0; i < currentNameservers.size(); i++) {
            NameserverStub ns = currentNameservers.get(i) == null ? null : currentNameservers.get(i);
            validateNameserver(object, ns, i);
        }
    }

    protected abstract void validateNameserver(Object object, NameserverStub ns, int index);
}
