package pl.nask.crs.commons.email.dao;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

import javax.annotation.Resource;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HistoricalEmailGroupDAOTest extends AbstractTest {
    @Resource
    EmailGroupDAO emailGroupDAO;

    @Resource
    HistoricalEmailGroupDAO historicalEmailGroupDAO;

    private void compareHEG(HistoricalObject<EmailGroup> actual, HistoricalObject<EmailGroup> expected) {
        Assert.assertEquals(actual.getObject().getId(), expected.getObject().getId());
        Assert.assertEquals(actual.getObject().getName(), expected.getObject().getName());
        Assert.assertEquals(actual.getObject().getChangeDate(), expected.getObject().getChangeDate());
        Assert.assertEquals(actual.getChangedBy(), expected.getChangedBy());
        Assert.assertEquals(actual.getChangeDate(), DateUtils.truncate(expected.getChangeDate(), Calendar.SECOND));
    }

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailGroupDAO.find(null,0,1).getTotalResults();

        // A group...
        EmailGroup g1 = new EmailGroup("group 1");
        emailGroupDAO.create(g1);
        g1 = emailGroupDAO.get(g1.getId());
        // becomes history...
        // because of type conversion, we need a Date that ends on a full second
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        HistoricalObject<EmailGroup> histGrp1 = new HistoricalObject<EmailGroup>(g1, aDate, "somebody");
        historicalEmailGroupDAO.create(histGrp1);
        Assert.assertEquals(historicalEmailGroupDAO.find(null, 0, 1).getTotalResults(), origCount + 1);
        HistoricalObject<EmailGroup> histGrp2 =
                historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(),histGrp1.getChangeId()));

        // It's weird, but if the order of parameters in the line below is
        // reversed, the test does not pass. It's because change date in one
        // of the objects is a Date and is a Timestamp in the other.
        // Apparently Timestamp.equals can deal with Dates but not the other
        // way around.
        compareHEG(histGrp2, histGrp1);

        HistoricalEmailGroupSearchCriteria crit = new HistoricalEmailGroupSearchCriteria(g1.getId(),null,null,null,null);
        LimitedSearchResult<HistoricalObject<EmailGroup>> findRes = historicalEmailGroupDAO.find(crit, 0, 1);

        Assert.assertEquals(findRes.getTotalResults(), 1);
        compareHEG(findRes.getResults().get(0), histGrp2);

        crit = new HistoricalEmailGroupSearchCriteria(g1.getId(),g1.getName(),g1.getChangeDate(),histGrp2.getChangedBy(),histGrp2.getChangeDate());
        findRes = historicalEmailGroupDAO.find(crit, 0, 1);

        Assert.assertEquals(findRes.getTotalResults(), 1);
        compareHEG(findRes.getResults().get(0), histGrp2);
    }

    private void clearHistoricalEmailGroup() {
        List<HistoricalObject<EmailGroup>> forDeletion = historicalEmailGroupDAO.find(null).getResults();
        for(HistoricalObject<EmailGroup> eg : forDeletion) {
            historicalEmailGroupDAO.delete(eg);
        }
    }

    @Test
    public void sortingTest() {
        clearHistoricalEmailGroup();

        EmailGroup g1 = new EmailGroup("group 1");
        emailGroupDAO.create(g1);
        g1 = emailGroupDAO.get(g1.getId());
        EmailGroup g2 = new EmailGroup("group 2");
        emailGroupDAO.create(g2);
        g2 = emailGroupDAO.get(g2.getId());
        EmailGroup g3 = new EmailGroup("group 3");
        emailGroupDAO.create(g3);
        g3 = emailGroupDAO.get(g3.getId());

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Date aDate2 = DateUtils.addSeconds(aDate, 1);

        HistoricalObject<EmailGroup> histGrp1 = new HistoricalObject<EmailGroup>(g1, aDate, "somebody");
        historicalEmailGroupDAO.create(histGrp1);
        histGrp1 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(),histGrp1.getChangeId()));
        HistoricalObject<EmailGroup> histGrp2 = new HistoricalObject<EmailGroup>(g1, aDate2, "somebody");
        historicalEmailGroupDAO.create(histGrp2);
        histGrp2 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g1.getId(),histGrp2.getChangeId()));
        HistoricalObject<EmailGroup> histGrp3 = new HistoricalObject<EmailGroup>(g2, aDate, "someone");
        historicalEmailGroupDAO.create(histGrp3);
        histGrp3 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g2.getId(),histGrp3.getChangeId()));
        HistoricalObject<EmailGroup> histGrp4 = new HistoricalObject<EmailGroup>(g3, aDate2, "someone");
        historicalEmailGroupDAO.create(histGrp4);
        histGrp4 = historicalEmailGroupDAO.get(new HistoricalEmailGroupKey(g3.getId(),histGrp4.getChangeId()));

        List<SortCriterion> sortCrit = new LinkedList<SortCriterion>();
        sortCrit.add(new SortCriterion("histChangedBy", false));
        sortCrit.add(new SortCriterion("name", true));
        sortCrit.add(new SortCriterion("id", false));
        sortCrit.add(new SortCriterion("histChangeDate", true));
        sortCrit.add(new SortCriterion("changeDate", true));

        SearchResult<HistoricalObject<EmailGroup>> findRes = historicalEmailGroupDAO.find(null, sortCrit);
        Assert.assertEquals(findRes.getResults().size(), 4);
        compareHEG(findRes.getResults().get(0), histGrp3);
        compareHEG(findRes.getResults().get(1), histGrp4);
        compareHEG(findRes.getResults().get(2), histGrp1);
        compareHEG(findRes.getResults().get(3), histGrp2);
    }

    @Test
    public void substringTest() {
        clearHistoricalEmailGroup();

        EmailGroup g1 = new EmailGroup("group 1");
        emailGroupDAO.create(g1);
        g1 = emailGroupDAO.get(g1.getId());
        EmailGroup g2 = new EmailGroup("group 2");
        emailGroupDAO.create(g2);
        g2 = emailGroupDAO.get(g2.getId());

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);

        HistoricalObject<EmailGroup> histGrp1 = new HistoricalObject<EmailGroup>(g1, aDate, "somebody");
        historicalEmailGroupDAO.create(histGrp1);
        HistoricalObject<EmailGroup> histGrp2 = new HistoricalObject<EmailGroup>(g2, aDate, "someone");
        historicalEmailGroupDAO.create(histGrp2);

        SearchResult<HistoricalObject<EmailGroup>> findRes = historicalEmailGroupDAO.find(
                new HistoricalEmailGroupSearchCriteria(null, "rou", null, null, null));
        Assert.assertEquals(findRes.getResults().size(), 2);
        findRes = historicalEmailGroupDAO.find(
                new HistoricalEmailGroupSearchCriteria(null, "1", null, null, null));
        Assert.assertEquals(findRes.getResults().size(), 1);
        findRes = historicalEmailGroupDAO.find(
                new HistoricalEmailGroupSearchCriteria(null, null, null, "some", null));
        Assert.assertEquals(findRes.getResults().size(), 2);
        findRes = historicalEmailGroupDAO.find(
                new HistoricalEmailGroupSearchCriteria(null, null, null, "on", null));
        Assert.assertEquals(findRes.getResults().size(), 1);
    }
}
