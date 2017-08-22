package pl.nask.crs.iedrapi;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.xml.Constants;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class CustomPrefixMapper extends NamespacePrefixMapper {

	Map<String, String> prefixes = new HashMap<String, String>();
	{
		prefixes.put(Constants.IEAPI_CONTACT_NAMESPACE, "contact");
		prefixes.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		prefixes.put(Constants.IEAPI_ACCOUNT_NAMESPACE, "account");
		prefixes.put(Constants.IEAPI_DOMAIN_NAMESPACE, "domain");
		prefixes.put(Constants.IEAPI_TICKET_NAMESPACE, "ticket");
		prefixes.put(Constants.SECDNS_NAMESPACE, "secDNS");
	}
	
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion,
			boolean requirePrefix) {		
		String prefix = prefixes.get(namespaceUri);
		return (prefix != null ? prefix : "");
	}
	
	
}
