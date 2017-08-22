package pl.nask.crs.web.validators;

import pl.nask.crs.app.domains.wrappers.NameserverStub;
import pl.nask.crs.commons.dns.validator.IPAddressValidator;
import pl.nask.crs.commons.dns.validator.InvalidIPAddressException;
import pl.nask.crs.commons.utils.Validator;

public class NsIPValidator extends AbstractNsValidator {
    
    public NsIPValidator() {
    	setDefaultMessage("Wrong ns IP");
	}
            
    @Override
    protected void validateNameserver(Object object, NameserverStub ns, int index) {
    	String value = ns.getIp();
    	if (!Validator.isEmpty(value)) {
            try {
                IPAddressValidator.getInstance().validate(value);
            } catch (InvalidIPAddressException e) {
                addFieldError(getFieldName()+".currentNameservers[" + index + "]"+".ip", object);
            }
        }
    	
    }
}
