package pl.nask.crs.commons.exporter;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;


public abstract class XmlExporter <T> {
	private final static Logger log  = Logger.getLogger(XmlExporter.class);

	private final String SCHEMA_FILE_NAME;

	private final Schema schema;

	private final JAXBContext ctx;

	private boolean validateOutput;


	public XmlExporter(String schemaFileName, String packageName) throws JAXBException, SAXException {
		this.SCHEMA_FILE_NAME = schemaFileName;

		ctx = initJAXBContext(packageName); 

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schema = sf.newSchema(new StreamSource(getClass().getResourceAsStream("/" + SCHEMA_FILE_NAME)));
	}

	protected void serialize(JAXBElement<T> jaxb, OutputStream destination, OutputStream secondDestination) throws ExportException {
		Marshaller m;
		try {
			m = createMarshaller();
			m.marshal(jaxb, destination);
            if (secondDestination != null) {
                m.marshal(jaxb, secondDestination);
            }
		} catch (JAXBException e) {
			String msg = String.format("Error exporting account update %s : error generating xml", jaxb.getValue());
			log.error(msg);
			log.debug(msg, e);
			throw new ExportException(msg, e);
		} finally {
			if (destination != null) {
				try {
					destination.close();
				} catch (IOException e) {};
			}
		}
	}

	private Marshaller createMarshaller() throws JAXBException {
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_FILE_NAME);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		if (validateOutput) {
			m.setSchema(schema);
			ValidationEventHandler validationEventHandler = new DefaultValidationEventHandler();
			m.setEventHandler(validationEventHandler );
		}
		return m;
	}

	public void setValidateOutput(boolean validate) {
		this.validateOutput = validate;
	}

	private JAXBContext initJAXBContext(String packageName) throws JAXBException {
		return JAXBContext.newInstance(packageName);
	}
}
