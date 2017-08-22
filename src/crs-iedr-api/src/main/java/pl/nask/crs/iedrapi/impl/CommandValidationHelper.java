package pl.nask.crs.iedrapi.impl;

import pl.nask.crs.commons.dns.validator.DomainNameValidator;

import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

/**
 * @author: Marcin Tkaczyk
 */
public class CommandValidationHelper {

	
    public static boolean isDomainNameSyntaxValid(String domainName) {
        try {
            DomainNameValidator.validateIedrName(domainName);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean isNsValid(String nsName) {
        try {
            DomainNameValidator.validateName(nsName);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isEmpty(List<String> list) {
        return list.size() == 0 || list.get(0) == null || list.get(0).trim().length() == 0;
    }

}
