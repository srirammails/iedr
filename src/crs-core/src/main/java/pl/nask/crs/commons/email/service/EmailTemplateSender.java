package pl.nask.crs.commons.email.service;

import java.io.File;
import java.util.List;

/**
 * It sends out e-mails based on a template and received e-mail template parameters.
 *
 * @author Patrycja Wegrzynowicz
 */
public interface EmailTemplateSender {

    /**
     * Sends out the e-mail. The email to be sent out is instantiated based
     * on the template identified by the given template name with a use of the email parameters.
     *
     * @param templateName       the name of the template to be used while creating the email to sent out
     * @param templateParameters the parameters to be used while instantiating the template
     * @throws IllegalArgumentException       thrown when templateName is null
     * @throws TemplateNotFoundException      thrown when no template is found corresponding to the given templateName
     * @throws TemplateInstantiatingException thrown when an error occured while instantiating the template with the given templateParameters
     * @throws EmailSendingException          thrown when an error occured while sending out the email instantiated based on the template and parameters
     */
//    public void sendEmail(String templateName, EmailParameters templateParameters) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;
//
//    public void sendEmail(String templateName, EmailParameters templateParameters, List<File> attachments) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    public void sendEmail(int templateId, EmailParameters templateParameters) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

    public void sendEmail(int templateId, EmailParameters templateParameters, List<File> attachments) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException;

}
