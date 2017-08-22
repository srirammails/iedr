package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.history.HistoricalObject;

public interface HistoricalEmailGroupDAO
        extends GenericDAO<HistoricalObject<EmailGroup>, HistoricalEmailGroupKey> {
}
