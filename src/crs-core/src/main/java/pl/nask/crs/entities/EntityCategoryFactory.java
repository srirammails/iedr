package pl.nask.crs.entities;

import java.util.List;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.entities.dao.EntityCategoryDAO;

public class EntityCategoryFactory implements Dictionary<Long, EntityCategory> {
    private EntityCategoryDAO categoryDao;

    public EntityCategoryFactory(EntityCategoryDAO categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * returns list of all categories
     */
    public List<EntityCategory> getEntries() {
        return categoryDao.find(null).getResults();
    }

    /**
     * returns category with given id
     */
    public EntityCategory getEntry(Long key) {
        return categoryDao.get(key);
    }

}
