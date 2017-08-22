package pl.nask.crs.commons.email.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.search.SearchResult;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class EmailDisablerIbatisDAO
        extends GenericIBatisDAO<EmailDisabler, EmailDisablerKey>
        implements EmailDisablerDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("emailId", "emailId");
        sortMap.put("nicHandle", "nicHandle");
        sortMap.put("disabled", "disabled");
        sortMap.put("changeDate", "changeDate");
    }

    public EmailDisablerIbatisDAO() {
        setGetQueryId("emaildisabler.get");
        setCreateQueryId("emaildisabler.create");
        setUpdateQueryId("emaildisabler.update");
        setDeleteQueryId("emaildisabler.delete");
        setFindQueryId("emaildisabler.find");
        setCountFindQueryId("emaildisabler.count");
        setSortMapping(sortMap);
    }

    @Override
    public boolean isNhAdminOrTechForDomain(final String nicHandle, final String domain) {
        int count = this.<Integer>performQueryForObject("emaildisabler.isNhAdminOrTechForDomain", new Object() {
            public String getNicHandle() {
                return nicHandle;
            }
            public String getDomainName() {
                return domain;
            }
        });

        return count > 0;
    }

    @Override
    public List<EmailDisabler> findWithEmailTempAndEmailGroup(final String nicHandle) {
        return performQueryForList("emaildisabler.findWithEmailTempAndEmailGroup",
                Collections.singletonMap("nicHandle", nicHandle));
    }

    @Override
    public void insertOrUpdate(final List<EmailDisablerSuppressInfo> emaildisablerInfo) {
        performInsert("emaildisabler.insertAndUpdateOnDuplicateKey",
                Collections.singletonMap("allValues", emaildisablerInfo));
    }

    @Override
    public SearchResult<EmailDisabler> findAndLockForUpdate(EmailDisablerSearchCriteria criteria) {
        FindParameters params = new FindParameters(criteria);
        List<EmailDisabler> list = performQueryForList("emaildisabler.findAndLockForUpdate", params);
        return new SearchResult<EmailDisabler>(criteria, null, list);
    }

    @Override
    public List<EmailDisabler> findAllDisablingsOfTemplatesBelongingToGroup(long groupId) {
        return performQueryForList("emaildisabler.findAllOfTemplatesBelongingToGroup", groupId);
    }

    @Override
    public List<EmailDisabler> findAllDisablingsOfTemplate(int templateId) {
        return performQueryForList("emaildisabler.findAllOfTemplate", templateId);
    }


    @Override
    public void enableTemplateForEveryone(int templateId) {
        performUpdate("emaildisabler.updateEnableForEveryone", templateId);
    }

    public String getNicHandleByDomainName(final String domain) {

        final Map<String, String> domainParam = Collections.singletonMap("domainName", domain);

        String result = performQueryForObject("emaildisabler.getNicHandleByDomainName", domainParam);
        if (result == null) {
            result = performQueryForObject("emaildisabler.getNicHandleFromTicketAboutDomainName", domainParam);
        }

        return result;
    }

    @Override
    public Map<String, String> getUserDetailsByNicHandle(String nicHandle) {
        return performQueryForObject("emaildisabler.getUserDetailsByNicHandle", Collections.singletonMap("nicHandle", nicHandle));
    }
}
