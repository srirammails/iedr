package pl.nask.crs.commons.email.service;

import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerSearchCriteria;

import javax.annotation.Resource;

public class EmailDisablerServiceTest extends AbstractTest {
    @Resource
    EmailDisablerService disablerService;

    @Resource
    EmailDisablerCheckService disablerCheckService;

    @Resource
    EmailDisablerDAO dis_dao;
    @Resource
    HistoricalEmailDisablerDAO hist_dis_dao;

    @Resource
    EmailTemplateDAO email_dao;
    @Resource
    EmailGroupDAO group_dao;

    @Test
    public void simpleServiceTest() throws Exception {
        disablerService.isTemplateDisabledForNH(44, "pizaonline.ie");

        // Should not send
        Assert.assertFalse(disablerCheckService.shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - nonsuppressible
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - nonsuppressible but disabled
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - suppressible but not disabled
        Assert.assertTrue(
                disablerCheckService.shouldSendToExternal(902, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - suppressible, disabled but user is tech or admin
        Assert.assertTrue(
                disablerCheckService.shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "AGZ082-IEDR"));
        // Should send - unknown email template
        Assert.assertTrue(
                disablerCheckService.shouldSendToExternal(999, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        // Should send - unknown owner
        Assert.assertTrue(
                disablerCheckService.shouldSendToExternal(42, "pizzaonline.ie", "N/A", "IDL2-IEDR"));
        // And some null testing:
        Assert.assertFalse(
                disablerCheckService.shouldSendToExternal(900, null, "IDL2-IEDR", null));
    }

    @Test
    public void updateEmailStatusTest() throws Exception {

        Assert.assertFalse(disablerCheckService.shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));

        List<EmailDisablerSuppressInfo> emaildisablerInfo = new LinkedList<EmailDisablerSuppressInfo>();
        emaildisablerInfo.add(new EmailDisablerSuppressInfo(900, "IDL2-IEDR", false));
        emaildisablerInfo.add(new EmailDisablerSuppressInfo(901, "IDL2-IEDR", true));

        List<EmailDisablerSuppressInfo> persistedEmailDisablerInfo = this.disablerService.updateEmailStatus(emaildisablerInfo, new OpInfo("IDL2-IEDR"));
        Assert.assertTrue(persistedEmailDisablerInfo.size() == 1);
        Assert.assertTrue(persistedEmailDisablerInfo.get(0).getEmailId() == 900);

        Assert.assertTrue(disablerCheckService.shouldSendToExternal(900, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(901, "pizzaonline.ie", "IDL2-IEDR", "IDL2-IEDR"));
    }

    @Test
    public void testShouldntBlockTemplateInInvisibleGroup() {
        EmailTemplate tpl = email_dao.get(1);
        EmailGroup grp = new EmailGroup("Test group");
        grp.setVisible(false);
        group_dao.create(grp);
        tpl.setGroup(grp);
        email_dao.update(tpl);

        EmailDisablerSuppressInfo suppressInfo = new EmailDisablerSuppressInfo(tpl.getId(), "IDL2-IEDR", true);
        EmailDisabler dis = dis_dao.get(new EmailDisablerKey(tpl.getId(), "IDL2-IEDR"));
        Assert.assertNull(dis, "Template is not yet disabled");
    }

    @Test
    public void testRemoveDisablingsOfTemplate() {
        EmailTemplate tpl = new EmailTemplate();
        tpl.setId(1);
        EmailDisabler ed1 = new EmailDisabler(tpl, "NTG1-IEDR", true);
        dis_dao.create(ed1);
        EmailDisabler ed2 = new EmailDisabler(tpl, "ABC718-IEDR", true);
        dis_dao.create(ed2);
        EmailDisabler ed3 = new EmailDisabler(tpl, "APIT5-IEDR", false);
        dis_dao.create(ed3);

        HistoricalEmailDisablerSearchCriteria find_for_u1 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u1.setNicHandle("NTG1-IEDR");
        find_for_u1.setEmailId(1l);
        int hist_u1_count = hist_dis_dao.find(find_for_u1).getResults().size();
        HistoricalEmailDisablerSearchCriteria find_for_u2 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u2.setNicHandle("ABC718-IEDR");
        find_for_u2.setEmailId(1l);
        int hist_u2_count = hist_dis_dao.find(find_for_u2).getResults().size();
        HistoricalEmailDisablerSearchCriteria find_for_u3 = new HistoricalEmailDisablerSearchCriteria();
        find_for_u3.setNicHandle("APIT5-IEDR");
        find_for_u3.setEmailId(1l);
        int hist_u3_count = hist_dis_dao.find(find_for_u3).getResults().size();

        disablerService.removeDisablingsOfTemplate(1, new OpInfo("actor"));

        ed1 = dis_dao.get(new EmailDisablerKey(1, "NTG1-IEDR"));
        ed2 = dis_dao.get(new EmailDisablerKey(1, "ABC718-IEDR"));
        ed3 = dis_dao.get(new EmailDisablerKey(1, "APIT5-IEDR"));
        Assert.assertFalse(ed1.isDisabled(), "u1 should have email enabled again");
        Assert.assertFalse(ed2.isDisabled(), "u2 should have email enabled again");
        Assert.assertFalse(ed3.isDisabled(), "u3 should have email enabled still");

        int u1_count = hist_dis_dao.find(find_for_u1).getResults().size();
        int u2_count = hist_dis_dao.find(find_for_u2).getResults().size();
        int u3_count = hist_dis_dao.find(find_for_u3).getResults().size();
        Assert.assertEquals(hist_u1_count + 1, u1_count, "First user should have new history entry");
        Assert.assertEquals(hist_u2_count + 1, u2_count, "Second user should have new history entry");
        Assert.assertEquals(hist_u3_count, u3_count, "Third user was already enabled, no history for him");
    }

    @Test
    public void testShouldSendToExternal() {
        String domain = "autocreated.ie";
        String owner = "APIT5-IEDR";
        String tech = "APIT2-IEDR";
        String admin = "APIT1-IEDR";
        String randomNH = "random";
        int templateId = 11;

        // without disabling should sent to all
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, tech), "Missing disabling, should send to tech");
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, admin), "Missing disabling, should send to admin");
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, randomNH), "Missing disabling, should send to random");

        EmailTemplate template = new EmailTemplate();
        template.setId(templateId);
        EmailDisabler disabling = new EmailDisabler();
        disabling.setNicHandle(owner);
        disabling.setEmailTemplate(template);
        disabling.setDisabled(false);
        dis_dao.create(disabling);

        //disabling exists but passes, should sent to everyone
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, tech), "Enabled, should send to tech");
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, admin), "Enabled, should send to admin");
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, randomNH), "Enabled, should send to random");

        disabling.setDisabled(true);
        dis_dao.update(disabling);
        // disabled, should send to tech and admin and not to random
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, tech), "Disabled, should send to tech");
        Assert.assertTrue(disablerCheckService.shouldSendToExternal(templateId, domain, owner, admin), "Disabled, should send to tech");
        Assert.assertFalse(disablerCheckService.shouldSendToExternal(templateId, domain, owner, randomNH), "Disabled, should not send to random");
    }

}
