package pl.nask.crs.commons.email.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailTemplateIbatisDAO extends GenericIBatisDAO<HistoricalObject<EmailTemplate>, HistoricalEmailTemplateKey> implements HistoricalEmailTemplateDAO {

    HistoricalEmailTemplateIbatisDAO() {
        setGetQueryId("histemailtemplate.get");
        setFindQueryId("histemailtemplate.find");
        setCountFindQueryId("histemailtemplate.count");
        setCreateQueryId("histemailtemplate.create");
    }

}
