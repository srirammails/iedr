package pl.nask.crs.user.dao.ibatis;

import java.util.Date;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;

public class InternalHistoricalUserIBatisDAO extends GenericIBatisDAO<InternalHistoricalUser, String> {
    
    public InternalHistoricalUserIBatisDAO() {
        setCountFindQueryId("historicalUser.countFind");
        setLimitedFindQueryId("historicalUser.find");
        setCreateQueryId("historicalUser.createHistoricalUser");
    }
    
     public void create(String username, String changedBy) {
        InternalHistoricalUser user = new InternalHistoricalUser();
        user.setChangeDate(new Date());
        user.setChangedBy(changedBy);
        user.setUsername(username);
        performInsert("historicalUser.createHistoricalUser", user);
    }
}
