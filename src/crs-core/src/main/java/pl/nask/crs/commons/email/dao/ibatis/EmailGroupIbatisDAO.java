package pl.nask.crs.commons.email.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;

import java.util.HashMap;
import java.util.Map;

public class EmailGroupIbatisDAO extends GenericIBatisDAO<EmailGroup, Long> implements EmailGroupDAO {
    public static Map<String, String> sortMap = new HashMap<String, String>();

    static {
        sortMap.put("id", "EG_ID");
        sortMap.put("name", "EG_Name");
        sortMap.put("changeDate", "EG_TS");
    }

    EmailGroupIbatisDAO() {
        setGetQueryId("emailgroup.get");
        setCreateQueryId("emailgroup.create");
        setUpdateQueryId("emailgroup.update");
        setDeleteQueryId("emailgroup.delete");
        setFindQueryId("emailgroup.find");
        setCountFindQueryId("emailgroup.count");
        setSortMapping(sortMap);
    }
}
