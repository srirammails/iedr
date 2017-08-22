package pl.nask.crs.entities.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.dao.EntityClassDAO;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EntityClassIBatisDAO extends GenericIBatisDAO<EntityClass, Long> implements
        EntityClassDAO {

    public EntityClassIBatisDAO() {
        setGetQueryId("entities.getClass");
        setFindQueryId("entities.getClassList");
    }

    public String getClassByName(String className) {
        return performQueryForObject("entities.getClassByName", className);
    }

    public boolean isClassMatchCategory(String className, String categoryName) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("cName", className);
        parameterMap.put("ctName", categoryName);
        return performQueryForObject("entities.isClassMatchCategory", parameterMap) != null;
    }

    @Override
    public List<ClassCategoryEntity> getClassCategoryEntity() {
        return performQueryForList("entities.getClassCategoryEntities");
    }
}
