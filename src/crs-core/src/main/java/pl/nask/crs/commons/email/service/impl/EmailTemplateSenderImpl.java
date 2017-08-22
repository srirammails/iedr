package pl.nask.crs.commons.email.service.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.ibatis.EmailDisablerIbatisDAO;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.utils.Validator;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author Kasia Fulara
 */
public class EmailTemplateSenderImpl implements EmailTemplateSender {

    private final static Logger LOGGER = Logger.getLogger(EmailTemplateSenderImpl.class);
    private EmailSender sender;
    private EmailInstantiator instantiator;
    private EmailTemplateDAO emailTemplateDAO;
    private EmailDisablerCheckService emailDisablerCheckService;

    public EmailTemplateSenderImpl(
            EmailSender emailSender, 
            EmailInstantiator emailInstantiator, 
            EmailTemplateDAO emailTemplateDAO,
            EmailDisablerCheckService emailDisablerCheckService
    ) {
        this.sender = emailSender;
        this.instantiator = emailInstantiator;
        this.emailTemplateDAO = emailTemplateDAO;
        this.emailDisablerCheckService = emailDisablerCheckService;
    }

//    /**
//     * @param templateName
//     * @param templateParameters
//     * @throws TemplateNotFoundException,     thrown if there was no template with given name in database
//     * @throws TemplateInstantiatingException
//     * @throws EmailSendingException
//     */
//    public void sendEmail(String templateName, EmailParameters templateParameters) throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
//        Validator.assertNotEmpty(templateName, "template name");
//        Email email = getEmail(templateName, templateParameters);
//        if (email.isActive()) {
//            LOGGER.info("Sending active email: " + templateName);
//            doSendEmail(email);
//        } else {
//            LOGGER.info("Skipping sending not active email: " + templateName);
//        }
//    }

	private void doSendEmail(Email email) throws EmailSendingException {
		if (email.isHtml()) {
			sender.sendHtmlEmail(email);
		} else {
			sender.sendEmail(email);
		}
	}

//    @Override
//    public void sendEmail(String templateName, EmailParameters templateParameters, List<File> attachments) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
//        Validator.assertNotEmpty(templateName, "template name");
//        Email email = getEmail(templateName, templateParameters);
//        if (email.isActive()) {
//            LOGGER.info("Sending active email: " + templateName);
//            email.setAttachments(attachments);
//            doSendEmail(email);
//        } else {
//            LOGGER.info("Skipping sending not active email: " + templateName);
//        }
//    }

//    private Email getEmail(String templateName, EmailParameters templateParameters, boolean shuldBeSentToExternalUser) 
//            throws TemplateNotFoundException, TemplateInstantiatingException {
//        EmailTemplate template = emailTemplateDAO.get(templateName);
//        if (template == null)
//            throw new TemplateNotFoundException("Cannot find email template: " + templateName);
//        return instantiator.instantiate(template, templateParameters, shouldBeSentToExternalUser);
//    }

    @Override
    public void sendEmail(int templateId, EmailParameters templateParameters) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        sendEmail(templateId, templateParameters, null);
    }

    private Email getEmail(int templateId, EmailParameters templateParameters, boolean shouldBeSentToExternalUser) throws TemplateNotFoundException, TemplateInstantiatingException {
        EmailTemplate template = emailTemplateDAO.get(templateId);
        if (template == null)
            throw new TemplateNotFoundException("Cannot find email template with id: " + templateId);
        return instantiator.instantiate(template, templateParameters, shouldBeSentToExternalUser); 
    }

    @Override
    public void sendEmail(int templateId, EmailParameters templateParameters, List<File> attachments) throws IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        String userName = templateParameters.getLoggedInNicHandle();
        boolean disabled = emailDisablerCheckService.shouldSendToExternal(
                templateId, 
                templateParameters.getDomainName(), 
                templateParameters.getAccountRelatedNicHandle(), 
                userName
        );
        Email email = getEmail(templateId, templateParameters, disabled);
        if (email.isToBeSent()) {
            LOGGER.info("Sending active email with id: " + templateId);
            if(attachments != null)
              email.setAttachments(attachments);
            doSendEmail(email);
        } else {
            LOGGER.info("Skipping sending not active email with id: " + templateId);
        }
    }
}
