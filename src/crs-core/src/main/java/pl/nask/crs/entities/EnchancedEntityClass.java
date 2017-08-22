package pl.nask.crs.entities;

import java.util.List;

public class EnchancedEntityClass implements EntityClass {

    private EntityClass entityClass;
    private List<EntityCategory> categories;

    public EnchancedEntityClass(EntityClass entityClass,
                                List<EntityCategory> categories) {
        this.entityClass = entityClass;
        this.categories = categories;
    }

    public long getId() {
        return entityClass.getId();
    }

    public String getName() {
        return entityClass.getName();
    }

    public List<EntityCategory> getCategories() {
        return categories;
    }

    public EntityClass getEntityClass() {
        return entityClass;
    }
}
