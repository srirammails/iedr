package pl.nask.crs.commons.email.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.dao.HistoricalEmailGroupDAO;
import pl.nask.crs.commons.email.search.HistoricalEmailGroupKey;
import pl.nask.crs.history.HistoricalObject;

import java.util.HashMap;
import java.util.Map;

public class HistoricalEmailGroupIbatisDAO
        extends GenericIBatisDAO<HistoricalObject<EmailGroup>, HistoricalEmailGroupKey>
        implements HistoricalEmailGroupDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("id", "EG_ID");
        sortMap.put("name", "EG_Name");
        sortMap.put("changeDate", "EG_TS");
        sortMap.put("histChangedBy", "histChangedBy");
        sortMap.put("histChangeDate", "histChangeDate");
        sortMap.put("changeId", "changeId");
    }
    HistoricalEmailGroupIbatisDAO() {
        setGetQueryId("histemailgroup.get");
        setCreateQueryId("histemailgroup.create");
        setFindQueryId("histemailgroup.find");
        setCountFindQueryId("histemailgroup.count");
        setSortMapping(sortMap);
    }
}
