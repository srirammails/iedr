package pl.nask.crs.entities.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.EntityClass;

import java.util.List;

public interface EntityClassDAO extends GenericDAO<EntityClass, Long> {

    String getClassByName(String className);

    boolean isClassMatchCategory(String className, String categoryName);

    List<ClassCategoryEntity> getClassCategoryEntity();
}
