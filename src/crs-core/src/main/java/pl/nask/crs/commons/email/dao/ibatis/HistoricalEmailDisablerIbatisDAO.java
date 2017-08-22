package pl.nask.crs.commons.email.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.HistoricalEmailDisablerKey;
import pl.nask.crs.history.HistoricalObject;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricalEmailDisablerIbatisDAO
        extends GenericIBatisDAO<HistoricalObject<EmailDisabler>, HistoricalEmailDisablerKey>
        implements HistoricalEmailDisablerDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("emailId", "emailId");
        sortMap.put("nicHandle", "nicHandle");
        sortMap.put("disabled", "disabled");
        sortMap.put("changeDate", "changeDate");
        sortMap.put("histChangedBy", "histChangedBy");
        sortMap.put("histChangeDate", "histChangeDate");
        sortMap.put("changeId", "changeId");
    }

    HistoricalEmailDisablerIbatisDAO() {
        setGetQueryId("histemaildisabler.get");
        setCreateQueryId("histemaildisabler.create");
        setFindQueryId("histemaildisabler.find");
        setCountFindQueryId("histemaildisabler.count");
        setSortMapping(sortMap);
    }

    @Override
    public void createForAll(List<Integer> affectedEmailIds, List<String> affectedUsers, Date changeDate, String changedBy) {
        if (affectedEmailIds.isEmpty())
            throw new IllegalArgumentException("List of affected emails cannot be empty");
        if (affectedUsers.isEmpty())
            throw new IllegalArgumentException("List of affected users cannot be empty");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("changedBy", changedBy);
        params.put("changeDate", changeDate);
        params.put("eids", affectedEmailIds);
        params.put("nics", affectedUsers);

        performInsert("histemaildisabler.createForAllFound", params);
    }

}
