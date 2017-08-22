package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.service.EmailGroupService;

public class EmailGroupValidator extends FieldValidatorSupport {
    private EmailGroupService service;

    public EmailGroupValidator(EmailGroupService service) {
        this.service = service;
        setDefaultMessage("Unknown group");
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Long id = (Long) this.getFieldValue(fieldName, object);
        if (id == null || id == EmailGroup.EMPTY_ID) {
            return;
        }

        EmailGroup g = service.get(id);
        if (g == null) {
            addFieldError(fieldName, object);
        }
    }

}