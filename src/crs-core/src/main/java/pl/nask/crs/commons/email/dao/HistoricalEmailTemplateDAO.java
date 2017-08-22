package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.HistoricalEmailTemplateKey;
import pl.nask.crs.history.HistoricalObject;

public interface HistoricalEmailTemplateDAO extends GenericDAO<HistoricalObject<EmailTemplate>, HistoricalEmailTemplateKey> {
}
