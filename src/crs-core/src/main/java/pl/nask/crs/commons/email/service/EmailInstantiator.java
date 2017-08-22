package pl.nask.crs.commons.email.service;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface EmailInstantiator {

    public Email instantiate(EmailTemplate template, EmailParameters parameters, boolean shouldBeSentToExternalUser) throws TemplateInstantiatingException;

}
