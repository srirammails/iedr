package pl.nask.crs.entities.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;

import java.util.Map;
import java.util.HashMap;

public class EntityCategoryIBatisDAO extends GenericIBatisDAO<EntityCategory, Long> implements
        EntityCategoryDAO {

    public EntityCategoryIBatisDAO() {
        setGetQueryId("entities.getCategory");
        setFindQueryId("entities.getCategoriesList");
    }

    public String getCategoryByName(String categoryName) {
        return performQueryForObject("entities.getCategoryByName", categoryName);
    }

    public boolean hasPermissionToClassCategory(String nicHandleId, String className, String categoryName) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("nicHandleId", nicHandleId);
        parameterMap.put("clName", className);
        parameterMap.put("ctName", categoryName);
        return performQueryForObject("entities.hasPermissionToClassCategory", parameterMap) != null;
    }
}
