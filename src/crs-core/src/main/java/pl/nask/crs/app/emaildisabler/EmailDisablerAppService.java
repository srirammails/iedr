package pl.nask.crs.app.emaildisabler;

import java.util.List;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface EmailDisablerAppService {

    /**
     * Find all emails  for a given user. with additional information (like if email is disabled or not if it is suppressible etc).
     * @param user given user
     * @return list of emails with additional information e.g.: if email current status, email exteranl recipients
     */
    public List<EmailDisabler> getAllFor(AuthenticatedUser user)
            throws AccessDeniedException;

    /**
     * Gets all disablings of any template (for any user) that belongs to given group. This
     * method will be used mainly for statistics,
     * @param user logged in user, used only for checking permissions
     * @param groupId id of group which templates should be checked
     * @return list of found disablings (can be empty, cannot be null)
     */
    public List<EmailDisabler> getAllOfEmailGroup(AuthenticatedUser user, long groupId) throws AccessDeniedException;

    /**
     * Get all disablings of given template (for any user)
     * @param user logged in user, used only for checking permissions
     * @param templateId id of template which disablings are looked for
     * @return list of found disablings
     */
    public List<EmailDisabler> getAllOfTemplate(AuthenticatedUser user, int templateId) throws AccessDeniedException;


    /**
     * Disable/enable emails for one, logged-in user
     * @param user logged-in user, for whom emails will be blocked
     * @param emaildisablerInfo list of EmailDisablerSuppressInfo with desired changes (enable/disable).
     *                          Settings about disabling a non-suppressible template will be silently ignored.
     * @return list of successfully altered EmailDisablerSuppressInfo.
     */
    public List<EmailDisablerSuppressInfo> modifySuppressionMode(
        AuthenticatedUser user,
        List<EmailDisablerSuppressInfo> emaildisablerInfo
    );
}
