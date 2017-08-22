package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class CategoryValidator extends FieldValidatorSupport {
    public CategoryValidator() {
		setDefaultMessage("Not a valid Category");
	}
    
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        if (fieldValue == null || (Long)fieldValue == -1) {
            addFieldError(getFieldName(), object);
        }
    }
}
