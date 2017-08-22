package pl.nask.crs.commons.email.service;

import org.junit.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;

import javax.annotation.Resource;

import java.util.Collections;

public class EmailTemplateServiceTest extends AbstractTest {
    @Resource
    EmailTemplateService service;

    @Resource
    EmailGroupDAO grp_dao;

    @Resource
    HistoricalEmailTemplateDAO hist_email_dao;

    @Resource
    EmailTemplateDAO email_dao;

    @Resource
    EmailDisablerDAO disabler_dao;

    @Test
    public void testSave() throws Exception {
        HistoricalEmailTemplateSearchCriteria criteria = new HistoricalEmailTemplateSearchCriteria();
        criteria.setId(1);
        SearchResult<HistoricalObject<EmailTemplate>> hist_old = hist_email_dao.find(criteria);
        Assert.assertTrue("History for email template is empty at start", hist_old.getResults().isEmpty());

        EmailGroup grp = new EmailGroup();
        grp.setName("grp 1");
        grp_dao.create(grp);

        EmailTemplate e1 = email_dao.get(1);
        e1.setGroup(grp);
        service.save(e1, new OpInfo("U1"));

        SearchResult<HistoricalObject<EmailTemplate>> hist_1 = hist_email_dao.find(criteria);
        Assert.assertEquals("History for email template is filled after save", hist_1.getResults().size(), 1);
        Assert.assertEquals("In history we should have current object", hist_1.getResults().get(0).getObject().getGroup().getId(), grp.getId());

        e1.setGroup(null);
        service.save(e1, new OpInfo("U2"));

        SearchResult<HistoricalObject<EmailTemplate>> hist_2 = hist_email_dao.find(criteria);
        Assert.assertEquals("History for email template is filled after save", hist_2.getResults().size(), 2);
        Assert.assertNull("In history we should have current object", hist_2.getResults().get(1).getObject().getGroup());
    }

    @Test
    public void testTemplateIsEnabledAfterAssignedToInvisibleGroup() {
        EmailGroup grp = new EmailGroup("Test invisible group");
        grp.setVisible(false);
        grp_dao.create(grp);

        EmailTemplate tpl = email_dao.get(1);
        EmailDisablerSuppressInfo suppressInfo = new EmailDisablerSuppressInfo(tpl.getId(), "IDL2-IEDR", true);
        disabler_dao.insertOrUpdate(Collections.singletonList(suppressInfo));
        EmailDisabler disabler = disabler_dao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));

        Assert.assertNotNull("Just created disabling should exist", disabler);
        Assert.assertTrue("For now template is blocked", disabler.isDisabled());

        tpl.setGroup(grp);
        service.save(tpl, new OpInfo("U"));

        disabler = disabler_dao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));

        Assert.assertNotNull("Disabling still exists", disabler);
        Assert.assertFalse("Template assigned to invisible group should be enabled", disabler.isDisabled());
    }

    @Test
    public void testOnGroupDeleted() {
        EmailGroup grp = new EmailGroup();
        grp.setName("grp1");
        grp_dao.create(grp);

        email_dao.lock(1);
        EmailTemplate e1 = email_dao.get(1);
        e1.setGroup(grp);
        email_dao.update(e1);
        email_dao.lock(2);
        EmailTemplate e2 = email_dao.get(2);
        e2.setGroup(grp);
        email_dao.update(e2);

        e1 = email_dao.get(1);
        e2 = email_dao.get(2);
        Assert.assertNotNull("Email template 1 should have set group", e1.getGroup());
        Assert.assertEquals("Email template 1 group should be the one just created", e1.getGroup().getId(), grp.getId());
        Assert.assertNotNull("Email template 2 should have set group", e2.getGroup());
        Assert.assertEquals("Email template 2 group should be the one just created", e2.getGroup().getId(), grp.getId());

        HistoricalEmailTemplateSearchCriteria find_1 = new HistoricalEmailTemplateSearchCriteria();
        find_1.setId(1);
        HistoricalEmailTemplateSearchCriteria find_2 = new HistoricalEmailTemplateSearchCriteria();
        find_2.setId(2);

        SearchResult<HistoricalObject<EmailTemplate>> h_1_o = hist_email_dao.find(find_1);
        Assert.assertEquals("History for email template 1 is empty at start", 0, h_1_o.getResults().size());
        SearchResult<HistoricalObject<EmailTemplate>> h_2_o = hist_email_dao.find(find_2);
        Assert.assertEquals("History for email template is empty at start", 0, h_2_o.getResults().size());

        service.onGroupDeleted(grp, new OpInfo("a1"));

        e1 = email_dao.get(1);
        e2 = email_dao.get(2);
        Assert.assertTrue("Email template should be removed", e1.getGroup() == null);
        Assert.assertTrue("Email template should be removed", e2.getGroup() == null);

        h_1_o = hist_email_dao.find(find_1);
        Assert.assertEquals("History of 1 should be filled after removing group", 1, h_1_o.getResults().size());
        h_2_o = hist_email_dao.find(find_2);
        Assert.assertEquals("History of 1 should be filled after removing group", 1, h_2_o.getResults().size());

    }

    @Test
    public void testEmptyGroupShouldBeNull() {
        EmailTemplate e = service.getEmailTemplate(170);
        Assert.assertNull("Email template without assigned group should have it set to null", e.getGroup());
    }

    @Test
    public void testAfterSettingNullGroupItShouldBeNull() {
        final int id = 170;
        EmailTemplate e = service.getEmailTemplate(id);
        EmailGroup grp = new EmailGroup("Test group");
        grp_dao.create(grp);
        e.setGroup(grp);

        final OpInfo op = new OpInfo("test user");
        service.save(e, op);
        e = email_dao.get(id);
        Assert.assertEquals("Should have this new group set", e.getGroup().getId(), grp.getId());

        e.setGroup(null);
        service.save(e, op);
        e = email_dao.get(id);
        Assert.assertNull("Should have group set to null", e.getGroup());
    }

}
