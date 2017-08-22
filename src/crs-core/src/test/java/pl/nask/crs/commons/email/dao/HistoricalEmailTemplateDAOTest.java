package pl.nask.crs.commons.email.dao;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.ibatis.HistoricalEmailTemplateIbatisDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;
import pl.nask.crs.history.HistoricalObject;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

public class HistoricalEmailTemplateDAOTest extends AbstractTest {
    @Resource
    HistoricalEmailTemplateIbatisDAO historicalEmailTemplateDAO;

    @Test
    public void basicDAOTests() {
        long origCount = historicalEmailTemplateDAO.find(null, 0, 1).getTotalResults();

        String nicHandle = "AA11-IEDR";
        int emailId = 42;
        EmailTemplate emailTemplate = newEmailTemplateWithId(emailId);

        Date aDate = DateUtils.setMilliseconds(new Date(), 999);

        final HistoricalObject<EmailTemplate> histEmail = new HistoricalObject<EmailTemplate>(emailTemplate, aDate, nicHandle);
        historicalEmailTemplateDAO.create(histEmail);

        HistoricalObject<EmailTemplate> histEmailDb =
                historicalEmailTemplateDAO.get(new HistoricalEmailTemplateKey(emailTemplate.getId(), histEmail.getChangeId()));
        Assert.assertNotNull(histEmailDb);
        Assert.assertEquals(histEmailDb.getChangeDate(), DateUtils.truncate(aDate, Calendar.SECOND));
    }

    private EmailTemplate newEmailTemplateWithId(int emailId) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(emailId);
        return emailTemplate;
    }
}
