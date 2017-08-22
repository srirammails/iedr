package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator;

/**
 * Like it's superclass, validates the length of the text field, but checks if
 * the field holds a String value. If not, validation is skipped (no
 * ClassCastException !). <br/>
 * (C) Copyright 2008 NASK Software Research & Development Department
 * 
 * @author Artur Gniadzik
 * 
 */
public class SafeStringLengthFieldValidator extends StringLengthFieldValidator {
   
    /*
     * almost copied from superclass (non-Javadoc)
     * 
     * @see
     * com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator
     * #validate(java.lang.Object)
     */
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        
        // ---begin--- check, if the field contains a String
        Object o = getFieldValue(fieldName, object);
        if (!(o instanceof String))
            return;
        
        String val = (String) o; 
        // ---end---
        
        if (getTrim()) {
        	val = val.trim();
        }
        
        if (val.length() == 0) {
            // use a required validator for these
            return;
        }
        
        if ((getMinLength() > -1) && (val.length() < getMinLength())) {
            addFieldError(fieldName, object);
        } else if ((getMaxLength() > -1) && (val.length() > getMaxLength())) {
            addFieldError(fieldName, object);
        }
    }
}
