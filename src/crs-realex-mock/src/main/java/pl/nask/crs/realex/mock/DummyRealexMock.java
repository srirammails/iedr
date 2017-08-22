package pl.nask.crs.realex.mock;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.ClasspathResourceReader;

public class DummyRealexMock {
	private static final Logger LOG = Logger.getLogger(DummyRealexMock.class); 
	
	private final String responseFileName = "default00Response.xml";

	private final String response;

	public DummyRealexMock() {
		this.response = ClasspathResourceReader.readString(responseFileName);
		if (response == null)
			throw new IllegalStateException ("Couldn't read the response file: " + responseFileName);
	}
	
	/**
	 * Dummy implementation: will just log the message and return the default message. 
	 * @param xmlMessage
	 * @return 
	 */
	public String processMessage(String xmlMessage) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Got Message " + xmlMessage);
			LOG.info("Returning default message (file: " + responseFileName + ")");
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Returning message: " + response);
		}
		
		return response;
	}
}
