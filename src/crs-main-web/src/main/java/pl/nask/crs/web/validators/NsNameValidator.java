package pl.nask.crs.web.validators;

import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.utils.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: mat
 * Date: 2009-01-21
 * Time: 11:57:43
 * To change this template use File | Settings | File Templates.
 */
public class NsNameValidator extends AbstractNsValidator {
	
	public NsNameValidator() {
		setDefaultMessage("Wrong ns name");
	}

	@Override
	protected void validateNameserver(Object object, NameserverStub ns, int index) {
		String value = ns.getName();
        if (Validator.isEmpty(value)) {
            addFieldError(getFieldName()+".currentNameservers[" + index + "]"+".name", object);
        } else {
            try {
                DomainNameValidator.validateName(value);
            } catch (InvalidDomainNameException e) {
                addFieldError(getFieldName()+".currentNameservers[" + index + "]"+".name", object);
            }
        }
		
	}
}
