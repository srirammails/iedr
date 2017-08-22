package pl.nask.crs.web;

import static pl.nask.crs.web.InterceptorHelper.dynamic;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.nask.crs.web.validators.SafeStringLengthFieldValidator;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ActionValidatorManager;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.ValidationInterceptor;
import com.opensymphony.xwork2.validator.Validator;
import com.opensymphony.xwork2.validator.ValidatorContext;
import com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator;

/**
 * Adds default 'max length' validation to all String fields from the parameter
 * map. The field is not validated, if it's not a String field, or has a
 * StringLengthFieldValidator defined, or exists in the 'excludeFields' list.
 * 
 * excludeFields parameter may be configured with
 * {@link DynamicInterceptorProperties}
 * 
 * exclude/include methods may also be configured with it.
 * 
 * (C) Copyright 2008 NASK Software Research & Development Department
 * 
 * @author Artur Gniadzik
 * 
 */
public class DefaultMaxLengthValidationInterceptor extends ValidationInterceptor {    
    
    private ActionValidatorManager actionValidatorManager;
    private int maxLength = 255;
    
    private Set excludeFields = new HashSet();

    private final static String HOST_MASTER_REMARK_FIELD = "hostmastersRemark";
    private final static int HOST_MASTER_REMARK_FIELD_MAX_LENGTH = 200;

    @Inject
    public void setActionValidatorManager(ActionValidatorManager mgr) {
        this.actionValidatorManager = mgr;
    }
    
    
    @Override
    // performs 'default' validation for all action fields
    protected void doBeforeInvocation(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        ActionProxy proxy = invocation.getProxy();        
        String context = proxy.getActionName();

        Map m = invocation.getInvocationContext().getParameters();
        Set fields = new HashSet(m.keySet());
        // fetch validators
        List<Validator> vl = actionValidatorManager.getValidators(action.getClass(), context);
        
        // leave only fields, which needs to be validated (default validator needs to be used)
        for (Validator v : vl) {
            if (v instanceof FieldValidator) {
                removeValidatedFields(fields, v);
            }
        }
        
        fields.removeAll(dynamic(excludeFields, invocation, this, "excludeFields"));
        
        validateFields(fields, action, invocation.getStack(), new DelegatingValidatorContext(action));                                        
    }


 


    private void validateFields(Set fields, Object action, ValueStack stack, ValidatorContext validatorContext)
            throws Exception {
        for (Object field : fields) {
            if (field instanceof String) {
                String fieldName = (String) field;
                StringLengthFieldValidator v = new SafeStringLengthFieldValidator();
                if (fieldName.equals(HOST_MASTER_REMARK_FIELD)) {
                    v.setMaxLength(HOST_MASTER_REMARK_FIELD_MAX_LENGTH);
                    v.setDefaultMessage("Maximum " + HOST_MASTER_REMARK_FIELD_MAX_LENGTH + " characters allowed");
                } else {
                    v.setMaxLength(getMaxLength());
                    v.setDefaultMessage("Maximum " + getMaxLength() + " characters allowed");
                }
                v.setValidatorContext(validatorContext);
                v.setValueStack(stack);
                v.setFieldName(fieldName);
                v.validate(action);
            }
        }
    }

    protected boolean applyInterceptor(ActionInvocation invocation) {
        String enabled = DynamicInterceptorProperties.getDynamicParameter(invocation, this, "enable");
        if (!Boolean.valueOf(enabled))
            return false;
        
        String method = invocation.getProxy().getMethod();
        // ValidationInterceptor
        boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(dynamic(getExcludeMethodsSet(), invocation, this, "excludeMethods"), dynamic(getIncludeMethodsSet(), invocation, this, "includeMethods"), method);
        if (log.isDebugEnabled()) {
            if (!applyMethod) {
                log.debug("Skipping Interceptor... Method [" + method + "] found in exclude list.");
            }
        }
        return applyMethod;
    }
    
    // remove all fields, which have maxlength validator defined
    private void removeValidatedFields(Set fields, Validator v) {
        if (v instanceof StringLengthFieldValidator) {
            String name = ((FieldValidator) v).getFieldName();
            fields.remove(name);
        }
    }  

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }


    public void setExcludeFields(String excludeFields) {
        this.excludeFields = InterceptorHelper.asCollection(excludeFields);
    }           
}
