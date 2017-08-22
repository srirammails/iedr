package pl.nask.crs.entities.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.entities.EntityCategory;

public interface EntityCategoryDAO extends GenericDAO<EntityCategory, Long> {

    String getCategoryByName(String categoryName);

    boolean hasPermissionToClassCategory(String nicHandleId, String className, String categoryName);

}
