package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;

import java.util.Collections;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleIdIBatisDAO extends GenericIBatisDAO<Long, Long> implements NicHandleIdDAO {

    public NicHandleIdIBatisDAO() {
        setUpdateQueryId("nicHandleId.updateNicHandleId");
    }

    public Long get(){
        return performQueryForObject("nicHandleId.getNicHandleId", Collections.emptyMap());
    }

    public void lockTable(){
        performUpdate("nicHandleId.lockTable", Collections.emptyMap());
    }

    public void unlockTable(){
        performUpdate("nicHandleId.unlockTable", Collections.emptyMap());
    }

}
