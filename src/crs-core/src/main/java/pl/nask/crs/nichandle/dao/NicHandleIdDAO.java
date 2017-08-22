package pl.nask.crs.nichandle.dao;

import pl.nask.crs.commons.dao.GenericDAO;

/**
 * @author Marianna Mysiorska
 */
public interface NicHandleIdDAO extends GenericDAO<Long, Long> {

    public Long get();

    public void lockTable();

    public void unlockTable();

}
