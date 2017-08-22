package pl.nask.crs.commons.email.dao;

import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.search.SearchResult;

public interface EmailDisablerDAO extends
        GenericDAO<EmailDisabler, EmailDisablerKey> {
    boolean isNhAdminOrTechForDomain(String nicHandle, String domain);

    List<EmailDisabler> findWithEmailTempAndEmailGroup(String nicHandle);

    /**
     * Searches for all rows matching the criteria, locks them for update and returns
     * @param criteria
     * @return
     */
    SearchResult<EmailDisabler> findAndLockForUpdate(EmailDisablerSearchCriteria criteria);

    List<EmailDisabler> findAllDisablingsOfTemplatesBelongingToGroup(long groupId);

    List<EmailDisabler> findAllDisablingsOfTemplate(int templateId);

    void insertOrUpdate(List<EmailDisablerSuppressInfo> emaildisablerInfo);

    void enableTemplateForEveryone(int templateId);

    String getNicHandleByDomainName(String domain);

    /**
     * Returns user details (name and email) for given nic handle.
     * @param nicHandle nic handle to be checked
     * @return map with two keys: "name" and "email", containing respectively user name and user email.
     */
    public Map<String, String> getUserDetailsByNicHandle(String nicHandle);
}
