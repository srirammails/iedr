package pl.nask.crs.commons.email.dao;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.ibatis.HistoricalEmailDisablerIbatisDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HistoricalEmailDisablerDAOTest extends AbstractTest {
    @Resource
    EmailDisablerDAO emailDisablerDAO;

    @Resource
    HistoricalEmailDisablerIbatisDAO historicalEmailDisablerDAO;

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailDisablerDAO.find(null, 0, 1).getTotalResults();

        String nicHandle = "AA11-IEDR";
        long emailId = 42;
        EmailTemplate emailTemplate = newEmailTemplateWithId(42);
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);
        EmailDisabler ed1 = new EmailDisabler(emailTemplate, nicHandle, true);
        emailDisablerDAO.create(ed1);

        EmailDisabler ed2 = new EmailDisabler(emailTemplate, nicHandle, true);

        final HistoricalObject<EmailDisabler> histEd1 = new HistoricalObject<EmailDisabler>(ed1, aDate2, "somebody");
        historicalEmailDisablerDAO.create(histEd1);
        Assert.assertEquals(historicalEmailDisablerDAO.find(null, 0, 1).getTotalResults(), origCount + 1);

        HistoricalObject<EmailDisabler> histDis1 =
                historicalEmailDisablerDAO.get(
                        new HistoricalEmailDisablerKey(emailId, nicHandle, histEd1.getChangeId()));
        Assert.assertNotNull(histDis1);
        Assert.assertEquals(histDis1.getChangeDate(), DateUtils.truncate(aDate2, Calendar.SECOND));

        LimitedSearchResult<HistoricalObject<EmailDisabler>> findRes =
                historicalEmailDisablerDAO.find(
                        new HistoricalEmailDisablerSearchCriteria(
                                emailId,
                                nicHandle,
                                true,
                                aDate,
                                "somebody",
                                aDate2),
                        0, 1);

        Assert.assertEquals(findRes.getTotalResults(), 1);
        Assert.assertEquals(findRes.getResults().get(0).getObject().getEmailTemplate().getId(),
                histDis1.getObject().getEmailTemplate().getId());
        Assert.assertEquals(findRes.getResults().get(0).getObject().getNicHandle(),
                histDis1.getObject().getNicHandle());
        Assert.assertEquals(findRes.getResults().get(0).getObject().isDisabled(),
                histDis1.getObject().isDisabled());
        Assert.assertEquals(findRes.getResults().get(0).getObject().getChangeDate(),
                histDis1.getObject().getChangeDate());
        Assert.assertEquals(findRes.getResults().get(0).getChangeDate(),
                histDis1.getChangeDate());
        Assert.assertEquals(findRes.getResults().get(0).getChangedBy(),
                histDis1.getChangedBy());
    }

    private void clearEmailDisabler() {
        for(EmailDisabler ed : emailDisablerDAO.find(null).getResults()) {
            emailDisablerDAO.delete(ed);
        }
    }

    private void clearHistoricalEmailDisabler() {
        List<HistoricalObject<EmailDisabler>> forDeletion = historicalEmailDisablerDAO.find(null).getResults();
        for(HistoricalObject<EmailDisabler> ed : forDeletion) {
            historicalEmailDisablerDAO.delete(ed);
        }
    }

    private void compareHED(HistoricalObject<EmailDisabler> actual, HistoricalObject<EmailDisabler> expected) {
        Assert.assertEquals(actual.getObject().getEmailTemplate().getId(), expected.getObject().getEmailTemplate().getId());
        Assert.assertEquals(actual.getObject().getNicHandle(), expected.getObject().getNicHandle());
        Assert.assertEquals(actual.getChangeDate(), DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND));
    }

    @Test
    public void sortingTest() throws Exception {
        clearHistoricalEmailDisabler();
        clearEmailDisabler();

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(42), "AAA442-IEDR", true);
        emailDisablerDAO.create(ed1);
        EmailDisabler ed2 = new EmailDisabler(newEmailTemplateWithId(43), "AAA906-IEDR", true);
        emailDisablerDAO.create(ed2);
        EmailDisabler ed3 = new EmailDisabler(newEmailTemplateWithId(42), "AAA967-IEDR", false);
        emailDisablerDAO.create(ed3);

        HistoricalObject<EmailDisabler> hed1 = new HistoricalObject<EmailDisabler>(ed1, aDate, "author 1");
        historicalEmailDisablerDAO.create(hed1);
        HistoricalObject<EmailDisabler> hed2 = new HistoricalObject<EmailDisabler>(ed1, aDate2, "author 2");
        historicalEmailDisablerDAO.create(hed2);
        HistoricalObject<EmailDisabler> hed3 = new HistoricalObject<EmailDisabler>(ed2, aDate, "author 1");
        historicalEmailDisablerDAO.create(hed3);
        HistoricalObject<EmailDisabler> hed4 = new HistoricalObject<EmailDisabler>(ed3, aDate, "author 2");
        historicalEmailDisablerDAO.create(hed4);

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("emailId",true));
        sortCrit.add(new SortCriterion("histChangedBy", false));
        sortCrit.add(new SortCriterion("nicHandle", false));
        sortCrit.add(new SortCriterion("changeDate", false));
        sortCrit.add(new SortCriterion("disabled", false));
        sortCrit.add(new SortCriterion("histChangeDate", false));

        SearchResult<HistoricalObject<EmailDisabler>> findRes = historicalEmailDisablerDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().size(), 4);
        compareHED(findRes.getResults().get(0), hed4);
        compareHED(findRes.getResults().get(1), hed2);
        compareHED(findRes.getResults().get(2), hed1);
        compareHED(findRes.getResults().get(3), hed3);
    }

    @Test
    public void substringSearchTest() {
        clearHistoricalEmailDisabler();
        clearEmailDisabler();

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);

        EmailDisabler ed1 = new EmailDisabler(newEmailTemplateWithId(42), "AAA442-IEDR", true);
        emailDisablerDAO.create(ed1);

        HistoricalObject<EmailDisabler> hed1 = new HistoricalObject<EmailDisabler>(ed1, aDate, "author 1st");
        historicalEmailDisablerDAO.create(hed1);
        HistoricalObject<EmailDisabler> hed2 = new HistoricalObject<EmailDisabler>(ed1, aDate2, "author 2nd");
        historicalEmailDisablerDAO.create(hed2);

        SearchResult<HistoricalObject<EmailDisabler>> findRes = historicalEmailDisablerDAO.find(
                new HistoricalEmailDisablerSearchCriteria(null, null, null, null, "thor", null));
        Assert.assertEquals(findRes.getResults().size(), 2);
        findRes = historicalEmailDisablerDAO.find(
                new HistoricalEmailDisablerSearchCriteria(null, null, null, null, "2", null));
        Assert.assertEquals(findRes.getResults().size(), 1);
    }

    private EmailTemplate newEmailTemplateWithId(int emailId) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(emailId);
        return emailTemplate;
    }
}
