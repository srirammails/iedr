package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.validator.IPAddressValidator;
import pl.nask.crs.commons.dns.validator.InvalidIPAddressException;
import pl.nask.crs.commons.utils.Validator;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Validates IP Address (C) Copyright 2008 NASK Software Research & Development
 * Department
 * 
 * @author Artur Gniadzik
 * 
 */
public class IPAddress extends FieldValidatorSupport {
    
	public IPAddress() {
		setDefaultMessage("Wrong IP address");
	}
	
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String value = fieldValue == null ? null : fieldValue.toString();
        
        if (Validator.isEmpty(value))
            return;
        try {
            IPAddressValidator.getInstance().validate(value);
        } catch (InvalidIPAddressException e) {
            addFieldError(getFieldName(), object);
        }
    }

}
