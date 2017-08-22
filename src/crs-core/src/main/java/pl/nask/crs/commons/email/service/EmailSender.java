package pl.nask.crs.commons.email.service;

import pl.nask.crs.commons.email.Email;

/**
 * The interface is responsible for sending out e-mails via SMTP server.
 *
 * @author Patrycja Wegrzynowicz
 */
public interface EmailSender {

    /**
     * Sends out the given e-mail.
     *
     *
     *
     * @param email the email to be sent out
     *
     * @throws IllegalArgumentException thrown when email is null
     *
     * @throws EmailSendingException thrown when a failure occured while sending out this email
     */
    public void sendEmail(Email email) throws IllegalArgumentException, EmailSendingException;

    public void sendHtmlEmail(Email email) throws IllegalArgumentException, EmailSendingException;
    
}
