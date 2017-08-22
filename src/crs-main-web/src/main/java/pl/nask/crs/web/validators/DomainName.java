package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class DomainName extends FieldValidatorSupport {
    protected String defaultMessage = "Wrong nameserver name";
    
    public DomainName() {
		setDefaultMessage("Wrong nameserver name");
	}
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String value = fieldValue == null ? null : fieldValue.toString();       
        
        try {
            DomainNameValidator.validateName(value);
        } catch (InvalidDomainNameException e) {
            addFieldError(getFieldName(), object);
        }
    }

}
