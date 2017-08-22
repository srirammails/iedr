package pl.nask.crs.commons.email.dao;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

import javax.annotation.Resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmailDisablerDAOTest extends AbstractTest {

    @Resource
    EmailDisablerDAO emailDisablerDAO;

    @Test
    public void basicDAOTests() {
        long origCount = emailDisablerDAO.find(null, 0, 1).getTotalResults();

        String nicHandle = "AA11-IEDR";
        int emailId = 42;
        Date aDate = new Date(((new Date().getTime())/1000)*1000);

        EmailDisablerSearchCriteria crit = new EmailDisablerSearchCriteria(null, nicHandle, null);
        long origCount2 = emailDisablerDAO.find(crit, 0, 1).getTotalResults();

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(emailId), nicHandle, true);
        emailDisablerDAO.create(ed1);

        ed1 = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));

        Assert.assertEquals(ed1.getEmailTemplate().getId(), emailId);
        Assert.assertEquals(ed1.getNicHandle(), nicHandle);
        Assert.assertEquals(ed1.isDisabled(), true);
        Assert.assertFalse(aDate.after(ed1.getChangeDate()));

        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(43), nicHandle, false);
        emailDisablerDAO.create(ed2);

        long emailId2 = 43;
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId2, nicHandle));
        Assert.assertEquals(ed2.getEmailTemplate().getId(), emailId2);
        Assert.assertEquals(ed2.getNicHandle(), nicHandle);
        Assert.assertEquals(ed2.isDisabled(), false);
        Assert.assertFalse(aDate.after(ed2.getChangeDate()));

        Assert.assertEquals(emailDisablerDAO.find(null, 0, 1).getTotalResults(), origCount + 2);
        List<EmailDisabler> ed3  = emailDisablerDAO.findWithEmailTempAndEmailGroup(nicHandle);

        List<Integer> ids = new LinkedList<Integer>();
        ids.add(43);
        ids.add(42);
        emailDisablerDAO.insertOrUpdate(this.createEmaildisablerInfo(ids, nicHandle));
        LimitedSearchResult<EmailDisabler> findRes = emailDisablerDAO.find(crit, 0, 1);
        Assert.assertEquals(findRes.getTotalResults(), origCount2 + 2);

        crit = new EmailDisablerSearchCriteria(ed1.getEmailTemplate().getId(), ed1.getNicHandle(), ed1.getChangeDate());
        findRes = emailDisablerDAO.find(crit, 0, 1);
        Assert.assertEquals(findRes.getTotalResults(), 1);
        Assert.assertEquals(findRes.getResults().get(0).getEmailTemplate().getId(), ed1.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed1.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(0).isDisabled(), ed1.isDisabled());
        Assert.assertEquals(findRes.getResults().get(0).getChangeDate(), ed1.getChangeDate());

        ed1.setDisabled(true);
        emailDisablerDAO.update(ed1);
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId,nicHandle));
        Assert.assertNotNull(ed2);
        Assert.assertEquals(ed1.getEmailTemplate().getId(), ed2.getEmailTemplate().getId());
        Assert.assertEquals(ed1.getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(ed1.isDisabled(), ed2.isDisabled());
        Assert.assertFalse(ed1.getChangeDate().before(ed2.getChangeDate()));

        emailDisablerDAO.delete(ed1);
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));
        Assert.assertNull(ed2);
    }

    private List<EmailDisablerSuppressInfo> createEmaildisablerInfo(List<Integer> eIDs, String nicHandle)
    {
        List<EmailDisablerSuppressInfo> info = new LinkedList<EmailDisablerSuppressInfo>();
        for (Integer id : eIDs) {
            info.add(new EmailDisablerSuppressInfo(id, nicHandle, true));
        }

        return info;
    }
    private EmailTemplate newEmailTemplateWithId(int emailId) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(emailId);
        return emailTemplate;
    }

    private void clearEmailDisabler() {
        List<EmailDisabler> forDeletion = emailDisablerDAO.find(null).getResults();
        for(EmailDisabler ed : forDeletion) {
            emailDisablerDAO.delete(ed);
        }
    }

    @Test
    public void sortingTest() throws Exception {
        clearEmailDisabler();

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(42), "AAA442-IEDR", true);
        emailDisablerDAO.create(ed1);
        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(43), "AAA906-IEDR", true);
        emailDisablerDAO.create(ed2);
        EmailDisabler ed3 = new EmailDisabler(newEmailTemplateWithId(42), "AAA967-IEDR", false);
        emailDisablerDAO.create(ed3);

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("nicHandle", true));
        SearchResult<EmailDisabler> findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getEmailTemplate().getId(), ed1.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed1.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getEmailTemplate().getId(), ed2.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getEmailTemplate().getId(), ed3.getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed3.getNicHandle());

        sortCrit.clear();
        sortCrit.add(new SortCriterion("emailId", false));
        sortCrit.add(new SortCriterion("nicHandle", true));
        findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed1.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed3.getNicHandle());

        sortCrit.clear();
        sortCrit.add(new SortCriterion("disabled", true));
        sortCrit.add(new SortCriterion("nicHandle", false));
        findRes = emailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().get(0).getNicHandle(), ed3.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(1).getNicHandle(), ed2.getNicHandle());
        Assert.assertEquals(findRes.getResults().get(2).getNicHandle(), ed1.getNicHandle());
    }

    @Test
    public void contactCountTest() throws Exception {
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("NTG1-IEDR", "theweb.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("ABC718-IEDR", "theweb.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT5-IEDR", "thedomena.msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT1-IEDR", "thedomena.msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "thedomena.msd3.ie"));
        Assert.assertTrue(emailDisablerDAO.isNhAdminOrTechForDomain("APIT4-IEDR", "thedomena.totransfer2.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "thedomena.totransfer2.ie"));
        Assert.assertFalse(emailDisablerDAO.isNhAdminOrTechForDomain("APIT2-IEDR", "obrienpr.ie"));
    }

    @Test
    public void testEnableTemplateForEveryone() {
        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(1), "NTG1-IEDR", true);
        emailDisablerDAO.create(ed1);
        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(1), "ABC718-IEDR", true);
        emailDisablerDAO.create(ed2);
        EmailDisabler ed3 = new EmailDisabler(newEmailTemplateWithId(1), "APIT5-IEDR", false);
        emailDisablerDAO.create(ed3);

        emailDisablerDAO.enableTemplateForEveryone(1);

        ed1 = emailDisablerDAO.get(new EmailDisablerKey(1, "NTG1-IEDR"));
        ed2 = emailDisablerDAO.get(new EmailDisablerKey(1, "ABC718-IEDR"));
        ed3 = emailDisablerDAO.get(new EmailDisablerKey(1, "APIT5-IEDR"));
        Assert.assertFalse(ed1.isDisabled(), "u1 should have email enabled again");
        Assert.assertFalse(ed2.isDisabled(), "u2 should have email enabled again");
        Assert.assertFalse(ed3.isDisabled(), "u3 should have email enabled still");
    }

    @Test
    public void returnsNullWhenEmailDisablerIsNotPresentInDatabase() {
        String nicHandle = "AA11-IEDR";
        int emailId = 1;

        EmailDisabler result = emailDisablerDAO.get(new EmailDisablerKey(emailId, nicHandle));

        Assert.assertTrue(result == null);
    }

    @Test
    public void testGetNicHandleForDomain() {
        Assert.assertEquals(emailDisablerDAO.getNicHandleByDomainName("adpDomain.ie"), "APITEST-IEDR", "Domain exists, it should take value from Contact table");
        Assert.assertEquals(emailDisablerDAO.getNicHandleByDomainName("onetouchping.ie"), "AAE359-IEDR", "Domain does not yet exists, NicHandle should be taken from registration ticket");
        Assert.assertNull(emailDisablerDAO.getNicHandleByDomainName("reallynonexistingdomain.ie"), "No domain and no ticket, should be NULL");
    }

}
