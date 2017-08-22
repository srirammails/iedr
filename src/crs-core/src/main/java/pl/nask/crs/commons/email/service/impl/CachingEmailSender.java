package pl.nask.crs.commons.email.service.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;

public class CachingEmailSender implements EmailSender {
	private final static Logger LOG = Logger.getLogger(CachingEmailSender.class);
	
	private EmailQueue emailQueue;

	public CachingEmailSender(EmailQueue emailCache) {
		this.emailQueue = emailCache;
	}
	
	@Override
	public void sendEmail(Email email) throws IllegalArgumentException,
			EmailSendingException {
        LOG.info("Queued email: " + email);
        queue(email);
    }

	@Override
	public void sendHtmlEmail(Email email) throws IllegalArgumentException,
			EmailSendingException {
        LOG.info("Queued email: " + email);
        queue(email);
    }
	
	private void queue(Email email) {
		try {
			emailQueue.queue(email);
		} catch (InterruptedException e) {
			LOG.error("Interrupted while adding an email to the wait queue (this email will not be send): " + email);
		}
	}
	
	@Override
	public String toString() {
		return "CachingEmailSender";
	}
}
