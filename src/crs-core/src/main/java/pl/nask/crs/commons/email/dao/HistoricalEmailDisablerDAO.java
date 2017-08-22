package pl.nask.crs.commons.email.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;
import java.util.List;

public interface HistoricalEmailDisablerDAO
    extends GenericDAO<HistoricalObject<EmailDisabler>, HistoricalEmailDisablerKey> {

    /**
     * Creates historial entries for all rows from EmailDisabler that match passed affectedEmailIds and affectedUsers lists.
     * @param affectedEmailIds list of email ids that were affected and should have historical entries created. Cannot be empty
     * @param affectedUsers list of users whose disablings were affected and should have histrical entries created. Cannot be empty.
     * @param changeDate datetime of change
     * @param changedBy user causing the change
     * @throws java.lang.IllegalArgumentException if any of passed lists are empty
     */
    void createForAll(List<Integer> affectedEmailIds, List<String> affectedUsers, Date changeDate, String changedBy)
        throws IllegalArgumentException;
}
