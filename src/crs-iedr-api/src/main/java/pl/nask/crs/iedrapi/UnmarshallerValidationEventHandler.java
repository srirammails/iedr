package pl.nask.crs.iedrapi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.log4j.Logger;

/**
 * @author: Marcin Tkaczyk
 */
public class UnmarshallerValidationEventHandler extends MyValidationEventHandler {

    private static final Logger log = Logger.getLogger(ValidationEventHandler.class);

    private static final boolean continueOnError = true;

    private static List<String> interruptingErrorMessagesList = new ArrayList<String>();

	private final ValidationCallback validationCallback;

    static {
        interruptingErrorMessagesList.add("cvc-complex-type.2.4.c");
    }

    public UnmarshallerValidationEventHandler(ValidationCallback validationCallback) {
    	this.validationCallback = validationCallback;
	}

	/**
     * This handler only logs schema validatin errors
     * @param event
     * @return
     */
    public boolean handleEvent(ValidationEvent event) {
        log.warn("Validation error");
        log.warn(String.format("Severity: %s\nMessage: %s\nLocation: %s\nException: %s",
                formatSeverity(event.getSeverity()),
                event.getMessage(),
                formatLocation(event.getLocator()),
                formatException(event.getLinkedException())));
        if (validationCallback != null)
        	validationCallback.addErrorMessage(event.getMessage());
        return continueOnError;
    }
}
