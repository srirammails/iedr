package pl.nask.crs.entities.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.entities.EntityCategory;

public class EntityCategorySearchCriteria implements
        SearchCriteria<EntityCategory> {
    private Long entityClassId;

    public EntityCategorySearchCriteria() {
    }

    /**
     * @param clazz entity class, which categories are to be fetched.
     */
    public EntityCategorySearchCriteria(Long entityClassId) {
        this.entityClassId = entityClassId;
    }

    public Long getEntityClass() {
        return entityClassId;
    }

    public void setEntityClass(Long entityClass) {
        this.entityClassId = entityClass;
    }

}
