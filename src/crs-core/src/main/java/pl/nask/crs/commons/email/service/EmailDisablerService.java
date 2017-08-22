package pl.nask.crs.commons.email.service;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;

import java.util.List;

public interface EmailDisablerService {
    boolean isTemplateDisabledForNH(int templateId, String nicHandle);

    /**
     * Find all emails  for a given user. with additional information (like if email is disabled or not if it is suppressible etc).
     * @param nicHandle given user
     */
    List<EmailDisabler> findAllEmailDisablersWithAddInfoForUser(String nicHandle);

    List<EmailDisabler> findAllOfTemplatesBelongingToGroup(long groupId);

    List<EmailDisabler> findAllOfTemlpate(int templateId);

    /**
     * Mark as disabled/enabled emails for a given user
     * @param emaildisablerInfo email list
     * @param opInfo given user
     */
    public List<EmailDisablerSuppressInfo> updateEmailStatus(
        List<EmailDisablerSuppressInfo> emaildisablerInfo,
        OpInfo opInfo
    );

    /**
     * Removes all disabling of given template, e.g. due to template no longer being suppressible
     * @param templateId id of template that is no longer suppressible
     * @param op operator that caused the change and should be archived in historical table
     */
    void removeDisablingsOfTemplate(int templateId, OpInfo op);
}
