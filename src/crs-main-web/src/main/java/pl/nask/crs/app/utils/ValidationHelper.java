package pl.nask.crs.app.utils;

import pl.nask.crs.commons.utils.EmailValidator;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ValidationHelper {
    private final ActionSupport as;

    /**
     * 
     * @param as
     *            ActionSuport object which should receive notification about
     *            the field error
     */
    public ValidationHelper(ActionSupport as) {
        this.as = as;
    }
    
    /**
     * checks, if the field contains non-empty value
     * 
     * @param fieldName
     *            ognl field name to check
     * @return true, if field value is null or empty String, false if String's
     *         length > 0
     */
    public boolean isFieldEmpty(String fieldName) {
        String s = fieldValue(fieldName);
        return isStringEmpty(s);
    }
    
    
    /**
     * checks, if the field contains non-empty value. If it doesn't, field error
     * is added and false is returned
     * 
     * @param fieldName
     * @param fieldLabel
     * @return
     */
    public boolean validateStringRequired(String fieldName, String fieldLabel) {
        String s = fieldValue(fieldName);
        return validateStringRequired(fieldName, s, fieldLabel);
    }

    /**
     * checks if the given field contains an e-mail address. If not, it adds
     * field error to the action support and returns false
     * 
     * @param fieldName
     * @param required
     * @param fieldLabel
     * @return
     */
    public boolean validateEmail(String fieldName, boolean required, String fieldLabel) {
        String s = fieldValue(fieldName);
        
        if (required && !validateStringRequired(fieldName, s, fieldLabel)) {
            return false;
        }                

        if (!isStringEmpty(s)) {
            try {                
                EmailValidator.validateEmail(s);
                return true;
            } catch (IllegalArgumentException ex) {
                as.addFieldError(fieldName, "E-mail is not valid ");
                return false;
            }
        }

        return true;
    }
    
    
    
    private boolean validateStringRequired(String fieldName, String fieldValue, String fieldLabel) {
        if (isStringEmpty(fieldValue)) {
            as.addFieldError(fieldName, "You must enter a value for " + fieldLabel);
            return false;
        }
        return true;
    }

    private boolean isStringEmpty(String s) {
        return (s == null || s.trim().length() == 0);        
    }

    private String fieldValue(String fieldName) {
        return ActionContext.getContext().getValueStack().findString(fieldName);
    }
}
