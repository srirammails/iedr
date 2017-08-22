package pl.nask.crs.commons.email.service.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;

public class MockedEmailSender implements EmailSender {
	private static final Logger LOG = Logger.getLogger(MockedEmailSender.class);
	
	
	@Override
	public void sendEmail(Email email) throws IllegalArgumentException,
			EmailSendingException {
		if (LOG.isDebugEnabled())
			LOG.debug("Text Email sent: " + email);
	}

	@Override
	public void sendHtmlEmail(Email email) throws IllegalArgumentException,
			EmailSendingException {
		if (LOG.isDebugEnabled())
			LOG.debug("HTML Email sent: " + email);
	}
	
	@Override
	public String toString() {		
		return "MockedEmailSender";
	}	
}
