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
import pl.nask.crs.commons.email.dao.HistoricalEmailGroupDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.EmailGroupSearchCriteria;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupSearchCriteria;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;

import javax.annotation.Resource;

import java.util.Collections;

public class EmailGroupServiceTest extends AbstractTest {
    @Resource
    EmailGroupService service;

    @Resource
    EmailGroupDAO grp_dao;
    @Resource
    HistoricalEmailGroupDAO hist_grp_dao;
    @Resource
    EmailTemplateDAO email_dao;
    @Resource
    HistoricalEmailTemplateDAO hist_email_dao;
    @Resource
    EmailDisablerDAO disabler_dao;

    @Test
    public void testFind() {
        EmailGroupSearchCriteria empty_crit = new EmailGroupSearchCriteria();

        SearchResult<EmailGroup> search_res = service.findEmailGroups(empty_crit, null);
        Assert.assertEquals("Find should return whole list of groups", 10, search_res.getResults().size());

        LimitedSearchResult<EmailGroup> l_search_res = service.findEmailGroups(empty_crit, 0, 1, null);
        Assert.assertEquals("Limited Find should return only limit", 1, l_search_res.getResults().size());
        Assert.assertEquals("Limited Find should return total count", 10, l_search_res.getTotalResults());
    }

    @Test
    public void testGet() {
        EmailGroup grp = new EmailGroup();
        grp.setName("grp1");
        grp_dao.create(grp);

        EmailGroup sgrp = service.get(grp.getId());
        Assert.assertNotNull("Service should be able to get group", sgrp);
        Assert.assertEquals("Group should have created group's name", grp.getName(), sgrp.getName());
    }

    @Test
    public void testCreate() {
        EmailGroup grp = new EmailGroup();
        grp.setName("grp1");

        OpInfo actor = new OpInfo("u1");
        service.create(grp, actor);
        EmailGroup sgrp = service.get(grp.getId());
        Assert.assertNotNull("Service should be able to get group", sgrp);
        Assert.assertEquals("Group should have created group's name", grp.getName(), sgrp.getName());

        HistoricalEmailGroupSearchCriteria find_grp = new HistoricalEmailGroupSearchCriteria();
        find_grp.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> hist_grps = hist_grp_dao.find(find_grp);
        Assert.assertEquals("There should be one historical entry for freshly created group", 1, hist_grps.getResults().size());
        HistoricalObject<EmailGroup> hist_grp = hist_grps.getResults().get(0);
        Assert.assertEquals("Changed by should be set", actor.getActorName(), hist_grp.getChangedBy());
        Assert.assertEquals("Objects id should be the one just created", grp.getId(), hist_grp.getObject().getId());
    }

    @Test
    public void testUpdate() {
        EmailGroup grp = new EmailGroup();
        grp.setName("grp1");
        grp_dao.create(grp);

        EmailTemplate tpl = email_dao.get(1);
        tpl.setGroup(grp);
        email_dao.update(tpl);
        EmailDisablerSuppressInfo disable = new EmailDisablerSuppressInfo(tpl.getId(), "IDL2-IEDR", true);
        disabler_dao.insertOrUpdate(Collections.singletonList(disable));

        EmailDisabler disabler = disabler_dao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNotNull("Disabling should exist", disabler);
        Assert.assertTrue("Disablings for template should be gone as it's group is invisible now", disabler.isDisabled());

        grp.setName("grp1_changed");
        grp.setVisible(false);
        OpInfo actor = new OpInfo("u1");
        service.update(grp, actor);
        EmailGroup sgrp = service.get(grp.getId());
        Assert.assertNotNull("Service should be able to get group", sgrp);
        Assert.assertEquals("Group should have created group's name", grp.getName(), sgrp.getName());
        Assert.assertFalse("Group should be marked as invisible", sgrp.getVisible());

        HistoricalEmailGroupSearchCriteria find_grp = new HistoricalEmailGroupSearchCriteria();
        find_grp.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> hist_grps = hist_grp_dao.find(find_grp);
        Assert.assertEquals("There should be one historical entry for updated group", 1, hist_grps.getResults().size());
        HistoricalObject<EmailGroup> hist_grp = hist_grps.getResults().get(0);
        Assert.assertEquals("Changed by should be set", actor.getActorName(), hist_grp.getChangedBy());
        Assert.assertEquals("Objects id should be the one just created", grp.getId(), hist_grp.getObject().getId());
        Assert.assertEquals("History should have current name", grp.getName(), hist_grp.getObject().getName());
        Assert.assertFalse("History should have current visibility", hist_grp.getObject().getVisible());

        // email should no longer be blocked since it's group is no longer visible
        disabler = disabler_dao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNotNull("Disabling still exists for this template", disabler);
        Assert.assertFalse("But it's now enabled as it's group is invisible", disabler.isDisabled());
    }

    @Test
    public void testDelete() {
        EmailGroup grp = new EmailGroup();
        grp.setName("grp1");
        grp_dao.create(grp);

        final int TEMPLATE_ID = 1;
        EmailTemplate e = email_dao.get(TEMPLATE_ID);
        e.setGroup(grp);
        email_dao.update(e);

        OpInfo actor = new OpInfo("u1");
        service.delete(grp, actor);
        EmailGroup sgrp = service.get(grp.getId());
        Assert.assertNull("Service should not be able to get group", sgrp);

        HistoricalEmailGroupSearchCriteria find_grp = new HistoricalEmailGroupSearchCriteria();
        find_grp.setId(grp.getId());
        SearchResult<HistoricalObject<EmailGroup>> hist_grps = hist_grp_dao.find(find_grp);
        Assert.assertEquals("There should be one historical entry for updated group", 1, hist_grps.getResults().size());
        HistoricalObject<EmailGroup> hist_grp = hist_grps.getResults().get(0);
        Assert.assertEquals("Changed by should be set", actor.getActorName(), hist_grp.getChangedBy());
        Assert.assertEquals("Objects id should be the one just deleted", grp.getId(), hist_grp.getObject().getId());
        Assert.assertEquals("History should have last name", "grp1", hist_grp.getObject().getName());

        e = email_dao.get(TEMPLATE_ID);
        Assert.assertNull("Template's group should be null after it was deleted", e.getGroup());
        HistoricalEmailTemplateSearchCriteria find_tpl = new HistoricalEmailTemplateSearchCriteria();
        find_tpl.setId(e.getId());
        SearchResult<HistoricalObject<EmailTemplate>> hist_emails = hist_email_dao.find(find_tpl);
        Assert.assertEquals("History of template should show new entry for deleted group", 1, hist_emails.getResults().size());
        HistoricalObject<EmailTemplate> hist_tpl = hist_emails.getResults().get(0);
        Assert.assertEquals("Changed by should be set", actor.getActorName(), hist_tpl.getChangedBy());
        Assert.assertNull("History of tpl should show null group", hist_tpl.getObject().getGroup());
    }
}
