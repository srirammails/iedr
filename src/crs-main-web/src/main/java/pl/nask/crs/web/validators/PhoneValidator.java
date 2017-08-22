package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import pl.nask.crs.app.nichandles.wrappers.PhoneWrapper;

public class PhoneValidator extends FieldValidatorSupport {
    public PhoneValidator() {
    	setDefaultMessage("Wrong number");
	}
    
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        if (fieldValue == null || ((PhoneWrapper)fieldValue).getCurrentList() == null || ((PhoneWrapper)fieldValue).getCurrentList().size() == 0) {
            addFieldError(getFieldName(), object);
        } else {
            for (int i = 0; i < ((PhoneWrapper)fieldValue).getCurrentList().size(); i++) {
                String value = ((PhoneWrapper)fieldValue).getCurrentList().get(i) == null ?  null : ((PhoneWrapper)fieldValue).getCurrentList().get(i);
                if (value == null || "".equals(value) || value.trim().length() == 0) {
                    addFieldError(getFieldName()+"["+i+"]", object);
                }
            }
        }
    }
}
