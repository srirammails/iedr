package pl.nask.crs.iedrapi;

import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.IeapiType;

import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import pl.nask.crs.iedrapi.exceptions.CommandSyntaxErrorException;

public class MyUnmarshaller {
	
	private final JAXBContext jaxbCtx;
	
	private List<String> schemaFilesList = new ArrayList<String>();

	private boolean validating; 
	
	public MyUnmarshaller() throws JAXBException {
		jaxbCtx = JAXBContext.newInstance(
                "ie.domainregistry.ieapi_1" +
                ":ie.domainregistry.ieapi_account_1" +
                ":ie.domainregistry.ieapi_contact_1" +
                ":ie.domainregistry.ieapi_domain_1" +
                ":ie.domainregistry.ieapi_ticket_1" +
                ":ie.domainregistry.ieapicom_1" +
                ":ietf.params.xml.ns.secdns_1");
	}
	
	public void setValidating(boolean validating) {
		this.validating = validating;
	}
	
	public CommandType unmarshal(String obj, ValidationCallback validationCallback) throws CommandSyntaxErrorException {
        if (obj == null)
            throw new CommandSyntaxErrorException();
        return unmarshal(new StringReader((String) obj), validationCallback);   
    }
	
	@SuppressWarnings("unchecked")
    private CommandType unmarshal(Reader reader, ValidationCallback validationCallback) throws CommandSyntaxErrorException {        
        try {
            Unmarshaller um = jaxbCtx.createUnmarshaller();
            if (validating)
            	enableSchemaValidation(um);
            um.setEventHandler(new UnmarshallerValidationEventHandler(validationCallback));
            JAXBElement<IeapiType> c = (JAXBElement<IeapiType>) um.unmarshal(reader);
            if (c.getValue().getCommand() == null)
                throw new CommandSyntaxErrorException();
            return c.getValue().getCommand();
        } catch (JAXBException e) {
            throw new CommandSyntaxErrorException(e);
        } catch (SAXException e) {
            throw new CommandSyntaxErrorException(e);
        } catch (URISyntaxException e) {
            throw new CommandSyntaxErrorException(e);
        }
    }
	
    private void enableSchemaValidation(Unmarshaller um) throws JAXBException, SAXException, URISyntaxException{
        um.setSchema(getSchema());
    }
    
    private Schema getSchema() throws SAXException, URISyntaxException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(toStreamSources(schemaFilesList));
    }
    
    public void setSchemaFilesList(List<String> schemaFileList) {
        this.schemaFilesList = schemaFileList;
    }
    
    private Source[] toStreamSources(Collection<String> filesList) throws URISyntaxException {
        List<Source> sourcesList = new ArrayList<Source>();
        for (String file : filesList) {
            URI uri = getClass().getResource(file).toURI();
            sourcesList.add(new StreamSource(uri.toString()));
        }
        Source[] ret = new Source[]{};
        ret = sourcesList.toArray(ret);
        return ret;
    }
}
