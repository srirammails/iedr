package pl.nask.crs.commons.email.dao;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;

import java.util.Collections;

/**
 * @author Kasia Fulara
 */
public class EmailTemplateDAOTest extends AbstractTest {

    @Resource
    EmailTemplateDAO emailTemplateDAO;

    @BeforeClass
    public static void init() {
    }

    @Test
    public void testGetEmailTemplateById() {
        EmailTemplate template = emailTemplateDAO.get(73);
        assertNotNull(template);
    }

    @Test
    public void testGetNameById() {
        String name = emailTemplateDAO.getNameById(900);
        assertEquals("Test Template 1", name);
    }

    @Test
    public void testUpdateOnlyAllowedFields() {
        EmailTemplate tpl = emailTemplateDAO.get(1);
        tpl.setSubject("Test subject");
        tpl.setText("Test text");
        tpl.setToList(Collections.singletonList("totest@test.ie"));
        tpl.setCcList(Collections.singletonList("cctest@test.ie"));
        tpl.setBccList(Collections.singletonList("bcctest@test.ie"));
        tpl.setInternalToList(Collections.singletonList("itotest@test.ie"));
        tpl.setInternalCcList(Collections.singletonList("icctest@test.ie"));
        tpl.setInternalBccList(Collections.singletonList("ibcctest@test.ie"));
        tpl.setActive(false);
        tpl.setHtml(true);
        tpl.setSendReason("Test send reason");
        tpl.setSuppressible(false);
        tpl.setNonSuppressibleReason("Test non suppressible reason");

        emailTemplateDAO.update(tpl);

        EmailTemplate db_tpl = emailTemplateDAO.get(1);
        assertEquals("Subject should not be changed", "Deposit Top-up (DOA) - $ORDER_ID$", db_tpl.getSubject());
        assertTrue("Text should not be changed", db_tpl.getText().startsWith("Dear $BILL-C_NAME$"));
        assertTrue("To list should not be changed", db_tpl.getToList().size() == 1 && db_tpl.getToList().get(0).equals("$BILL-C_EMAIL$"));
        assertTrue("Cc list should not be changed", db_tpl.getCcList().isEmpty());
        assertTrue("Bcc list should not be changed", db_tpl.getBccList().isEmpty());
        assertTrue("Internal To list should not be changed", db_tpl.getInternalToList().isEmpty());
        assertTrue("Internal Cc list should not be changed", db_tpl.getInternalCcList().size() == 1 && db_tpl.getInternalCcList().get(0).equals("receipts@iedr.ie"));
        assertTrue("Internal Bcc list should not be changed", db_tpl.getInternalBccList().size() == 1 && db_tpl.getInternalBccList().get(0).equals("receipts@iedr.ie"));
        assertEquals("Active should not change", true, db_tpl.isActive());
        assertEquals("Html should not change", false, db_tpl.isHtml());
        assertEquals("Send reason should change", "Test send reason", db_tpl.getSendReason());
        assertEquals("Suppressible should change", false, db_tpl.isSuppressible());
        assertEquals("NonSuppressibleReason should change", "Test non suppressible reason", db_tpl.getNonSuppressibleReason());
    }


    @Test
    public void testGet() {
        EmailTemplate tpl = emailTemplateDAO.get(1);
        assertNotNull(tpl);
        assertEquals("Deposit Top-up (DOA) - $ORDER_ID$", tpl.getSubject());
    }

    @Test
    public void testLock() {
        assertTrue(emailTemplateDAO.lock(1));
        EmailTemplate tpl = emailTemplateDAO.get(1);
        assertNotNull(tpl);
        assertEquals("Deposit Top-up (DOA) - $ORDER_ID$", tpl.getSubject());
    }
}
