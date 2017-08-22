package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalHistoricalNicHandle;

/**
 * @author Marianna Mysiorska
 */
public class InternalHistoricalNicHandleIBatisDAO extends GenericIBatisDAO<InternalHistoricalNicHandle, Long> {

    public InternalHistoricalNicHandleIBatisDAO() {
        setFindQueryId("historicalNicHandle.findHistoricalNicHandle");
        setCountFindQueryId("historicalNicHandle.findCount");
        setGetQueryId("historicalNicHandle.get");
    }

    @Override
    public void create(InternalHistoricalNicHandle internalHistoricalNicHandle){
        performInsert("historicalNicHandle.createHistoricalNicHandle", internalHistoricalNicHandle);
        performInsert("historicalNicHandle.insertHistoricalTelecoms", internalHistoricalNicHandle);
        if (internalHistoricalNicHandle.getVatNo() != null)
            performInsert("historicalNicHandle.insertHistoricalPayment", internalHistoricalNicHandle);
    }

}
