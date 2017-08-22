package pl.nask.crs.iedrapi;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.apache.log4j.Logger;

public class MyValidationEventHandler implements ValidationEventHandler {
	private static final Logger LOG = Logger.getLogger(ValidationEventHandler.class);
	
	private static final boolean continueOnError = false;

	private String schemaErrorMessage; 
	
	@Override
	public boolean handleEvent(ValidationEvent event) {		
		this.schemaErrorMessage = String.format("Severity: %s\nMessage: %s\nLocation: %s\nException: %s", 
				formatSeverity(event.getSeverity()), 
				event.getMessage(), 
				formatLocation(event.getLocator()), 
				formatException(event.getLinkedException()));	
		LOG.warn("Validation error");
		LOG.warn(schemaErrorMessage);		
        if (!continueOnError) {
			LOG.error("Valiadtion error discontinues operation");
		} else {
			LOG.info("Validation error doesn't stop operation");			
		}
		
		return continueOnError;
	}

	protected String formatException(Throwable linkedException) {
		if (linkedException == null) {
			return "none";
		} else {
			return linkedException.toString();
		}
	}

	protected String formatLocation(ValidationEventLocator locator) {
		return String.format("URL: %s\nNode: %s\nObject: %s\nLine: %s Column: %s Offset: %s",
		locator.getURL(),
		locator.getNode(),
		locator.getObject(),
		locator.getLineNumber(),
		locator.getColumnNumber(),
		locator.getOffset());
	}

	protected String formatSeverity(int severity) {
		switch (severity) {
			case ValidationEvent.ERROR: 
				return "ERROR";
			case ValidationEvent.FATAL_ERROR:
				return "FATAL ERROR";
			case ValidationEvent.WARNING:
				return "WARNING";
			default:
				return "UNKNOWN (" + severity + ")";
		}
	}
}
