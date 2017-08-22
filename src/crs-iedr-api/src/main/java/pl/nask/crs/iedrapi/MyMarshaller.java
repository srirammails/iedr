package pl.nask.crs.iedrapi;

import ie.domainregistry.ieapi_1.IeapiType;
import ie.domainregistry.ieapi_1.ObjectFactory;
import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_1.ResultType;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;

import pl.nask.crs.xml.Constants;

public class MyMarshaller {
	
	private static final ObjectFactory ieapiTypeFactory = new ObjectFactory();
	
	private static final CustomPrefixMapper prefixMapper = new CustomPrefixMapper(); 
	
	private static final Map<String, String> uriToFile = new HashMap<String, String>();
	
	static {
		addUri(Constants.IEAPI_CONTACT_NAMESPACE, "ieapi-contact");
		addUri(Constants.IEAPI_NAMESPACE, "ieapi");
		addUri(Constants.IEAPI_ACCOUNT_NAMESPACE, "ieapi-account");
		addUri(Constants.IEAPI_DOMAIN_NAMESPACE, "ieapi-domain");
		addUri(Constants.IEAPI_TICKET_NAMESPACE, "ieapi-ticket");
		addUri(Constants.IEAPICOM_NAMESPACE, "ieapicom");
		addUri(Constants.SECDNS_NAMESPACE, "secDns", "1.1");
	}

	/*
	 * since JAXBContext is thread safe (but NOT the marshaller/unmarshaller), one for application is enough
	 */
	
	// global JAXBContext (for IedrApi)
	private static JAXBContext jaxbCtx; 
	// JAXBContext for err values
	private static JAXBContext jaxbCtxErr;
	
	static {
		try {
			jaxbCtx = JAXBContext.newInstance(IeapiType.class);
			jaxbCtxErr = JAXBContext.newInstance(String.class);
		} catch (JAXBException e) {
			// can't operate without JAXBContext
			throw new RuntimeException(e);
		}
	}

	// The result to be marshalled
	private final ResponseType result; 
	
	// marshallers
	private Marshaller resMarshaller;
	
	private Marshaller errMarshaller;

	private Marshaller globalMarshaller;
	
	public MyMarshaller(ResponseType result) throws JAXBException {
		this.result = result;		
		
		globalMarshaller = prepareGlobalMarshaller();
		resMarshaller = prepareResultMarshaller();
		errMarshaller = prepareErrMarshaller();
	}

	private static void addUri(String namespace, String resourceName) {
		addUri(namespace, resourceName, Constants.API_VERSION_NUMBER);
	}
	
	private static void addUri(String namespace, String resourceName, String versionNumber) {
		uriToFile.put(namespace, String.format("%s-%s.xsd", resourceName, versionNumber));
	}

	/**
	 * creates a marshaller for {@link ResultType}'s value (it there is something to be serialized)
	 * @return a marshaller to use or null, if there is nothing to serialize
	 * @throws JAXBException
	 */
	private Marshaller prepareErrMarshaller() throws JAXBException {
		if (result.getResult().getValue() != null && result.getResult().getValue().getContent().size() != 0) {		
				
			Marshaller m = jaxbCtxErr.createMarshaller();
			m.setProperty("jaxb.formatted.output", true);
			m.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper);
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ValidationEventHandler validationEventHandler = new MyValidationEventHandler();
			m.setEventHandler(validationEventHandler );
			return m;
		} else {
			return null;
		}
	}

	/**
	 * creates a marshaller for {@ ResponseType}'s resData (if there is something to be serialized)
	 * @return a marshaller to use or null, if there is nothing to serialize
	 * @throws JAXBException
	 */
	private Marshaller prepareResultMarshaller() throws JAXBException {
		if (result.getResData() != null && result.getResData().getAny().size() != 0) {		
			JAXBElement resultContent = (JAXBElement) result.getResData().getAny().get(0);			
			JAXBContext jaxbCtxRes = JAXBContext.newInstance(resultContent.getValue().getClass());
			Marshaller m = jaxbCtxRes.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			String uri = resultContent.getName().getNamespaceURI();
			
			String schemaLocation = uri + " " + getSchemaForUri(uri);
			
			// handle secDNS
			if (containsSecDNS(resultContent)) {
				schemaLocation += " " + Constants.SECDNS_NAMESPACE + " " + getSchemaForUri(Constants.SECDNS_NAMESPACE);
			}
			
			
			m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
			m.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper);
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ValidationEventHandler validationEventHandler = new MyValidationEventHandler();
			m.setEventHandler(validationEventHandler );
			return m;
		} else {
			return null;
		}
	}

	private boolean containsSecDNS(JAXBElement resultContent) {
		// TODO: scan resultContent to see, if it contains not null references to ietf.params.xml.ns.secdns_1 
		return true;
	}

	private Marshaller prepareGlobalMarshaller() throws JAXBException {
		Marshaller m = jaxbCtx.createMarshaller();
		m.setProperty("jaxb.formatted.output", true);
		m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, 
				Constants.IEAPI_NAMESPACE + " " + getSchemaForUri(Constants.IEAPI_NAMESPACE));
		m.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper);
		ValidationEventHandler validationEventHandler = new MyValidationEventHandler();
		m.setEventHandler(validationEventHandler );
		return m;
	}

	private String getSchemaForUri(String uri) {
		return uriToFile.get(uri);
	}

	public String marshall() throws JAXBException {
		return marshallWithStringWriter();
	}
	
	
	public String marshallWithStringWriter() throws JAXBException {
				
		// prepare resData and err value
		String serializedResult = null;
		if (resMarshaller != null) {
			 serializedResult = marshallPart(resMarshaller, result.getResData().getAny());
		}
		
		String serializedErr = null;
		if (errMarshaller != null) {
			serializedErr  = marshallPart(errMarshaller, result.getResult().getValue().getContent());
		}
				
		IeapiType t = new IeapiType();
		t.setResponse(result);
				
		JAXBElement<IeapiType> el = ieapiTypeFactory.createIeapi(t);
	    StringWriter writer = new StringWriter();	    
	    globalMarshaller.marshal(el, writer);
	    
	    // postprocessing: replace empty 'resData' or 'value' tags with values that were serialized earlier.
	    String res = writer.getBuffer().toString();
	    if (serializedResult != null)
	    	res = res.replaceAll("<resData/>", "<resData>\n" + Matcher.quoteReplacement(serializedResult) + "\n</resData>");
	    if (serializedErr != null)
	    	res = res.replaceAll("<value/>", "<value>\n" + Matcher.quoteReplacement(serializedErr) + "\n</value>");
	    return res;
	}

	private String marshallPart(Marshaller m, List<Object> list) throws JAXBException {
		StringWriter resultWriter = new StringWriter();

		m.marshal(list.get(0), resultWriter);
		list.clear();
		
		return resultWriter.toString();
	}

}
