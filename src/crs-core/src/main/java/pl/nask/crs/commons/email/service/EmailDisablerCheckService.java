package pl.nask.crs.commons.email.service;

/**
 * Responsible for performing disabler-based checks, e.g. should email be sent to
 * given user.
 */
public interface EmailDisablerCheckService {

    /**
     * Checks if given template should be sent to external adresses, i.e. it is not
     * disabled in current context.
     * @param templateId id of template to send
     * @param domain domain name of
     * @param owner owner of given domain if applicable
     * @param user logged in user
     * @return <code>true</code> if mail should be sent to external addresses, <code>false</code> otherwise
     */
    boolean shouldSendToExternal(int templateId, String domain, String owner, String user);
}
