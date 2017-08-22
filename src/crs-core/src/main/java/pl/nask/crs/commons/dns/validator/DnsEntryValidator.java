package pl.nask.crs.commons.dns.validator;


import static pl.nask.crs.commons.utils.Validator.isEmpty;

import java.util.List;

public class DnsEntryValidator {
	
	public static void checkNameserver(String domainName, String nsName, List<String> ipAddress) throws NsGlueNotAllowedException, NsGlueRequiredException {
		if (!isEmpty(ipAddress)) {
			checkNameserver(domainName, nsName, ipAddress.get(0));
		} else {
			checkNameserver(domainName, nsName, "");
		}
	}	
	
	public static void checkNameserver(String domainName, String nsName, String ipAddress) throws NsGlueNotAllowedException, NsGlueRequiredException {
        domainName = domainName.toLowerCase();
        nsName = nsName.toLowerCase();

		DomainNameValidator.validateName(nsName);        
		// ipv4 validation

        if (!isEmpty(ipAddress) && !ipAddress.matches("(\\d{1,3}\\.){3}\\d{1,3}"))
        	throw new InvalidIPv4AddressException(ipAddress);            

        // for each domain:ns: if domain:nsAddr && domain:nsName not ends with domain:name => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 164)
        if (!isEmpty(ipAddress) && !isGlueRequired(domainName, nsName))
        	throw new NsGlueNotAllowedException(domainName, nsName, ipAddress);

        // for each domain:ns: if !domain:nsAddr && domain:nsName ends with domain:name => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 175)
        if (isEmpty(ipAddress) && isGlueRequired(domainName, nsName))
            throw new NsGlueRequiredException(domainName, nsName, "");		
	}

    private static boolean isGlueRequired(String domainName, String dnsName) {
        return dnsName.equalsIgnoreCase(domainName) || dnsName.endsWith("." + domainName);
    }
}
