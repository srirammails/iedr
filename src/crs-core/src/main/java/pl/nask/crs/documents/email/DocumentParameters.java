package pl.nask.crs.documents.email;

import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.Document;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentParameters implements EmailParameters {

    private Document document;
    private String mailTo;
    private String username;
    private String accountNicHandle;

    public DocumentParameters(Document document, String mailTo, String username, String accountNicHandle) {
        Validator.assertNotNull(document, "Document");
        Validator.assertNotEmpty(accountNicHandle, "Account related nic handle");
        Validator.assertNotEmpty(mailTo, "Mail To");
        this.document = document;
        this.mailTo = mailTo;
        this.username = username;
        this.accountNicHandle = accountNicHandle;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return accountNicHandle;
    }

    public String getDomainName()
    {
        return null; // No admin verification for a list of domains
    }

    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum dp = ParameterNameEnum.forName(name);
        switch (dp) {
            case SOURCE:
                return document.getDocSource();
            case DOMAIN:
                return document.getDomainsAsString();
            case BILL_C_EMAIL:
                return mailTo;
            default:
                	return null;
        }
    }

}
