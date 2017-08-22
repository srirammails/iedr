package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import pl.nask.crs.app.nichandles.wrappers.PhoneWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: mat
 * Date: 2009-01-22
 * Time: 11:57:44
 * To change this template use File | Settings | File Templates.
 */
public class PhoneLengthValidator extends FieldValidatorSupport {
    public PhoneLengthValidator() {
		setDefaultMessage("Wrong number");
	}
	
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        if (fieldValue != null && ((PhoneWrapper)fieldValue).getCurrentList() != null && ((PhoneWrapper)fieldValue).getCurrentList().size() != 0) {
            for (int i = 0; i < ((PhoneWrapper)fieldValue).getCurrentList().size(); i++) {
                String value = ((PhoneWrapper)fieldValue).getCurrentList().get(i) == null ?  null : ((PhoneWrapper)fieldValue).getCurrentList().get(i);
                if (value != null && value.length() > 25) {
                    addFieldError(getFieldName()+"["+i+"]", object);
                }
            }
        }
    }
}
