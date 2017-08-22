package pl.nask.crs.entities;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.entities.dao.ibatis.InternalEntityClass;
import pl.nask.crs.entities.search.EntityCategorySearchCriteria;

public class EntityClassFactory implements
        Dictionary<Long, EnchancedEntityClass> {

    private EntityClassDAO classDao;
    private EntityCategoryDAO categoryDao;

    private static final int CHARITY_CLASS_ID = -200;
    
    public EntityClassFactory(EntityClassDAO classDao,
                              EntityCategoryDAO categoryDao) {
        this.classDao = classDao;
        this.categoryDao = categoryDao;
    }

    private EnchancedEntityClass prepareCharityClass() {
        InternalEntityClass ec = new InternalEntityClass();
        ec.setId(CHARITY_CLASS_ID);
        ec.setName("Charity");

        return new EnchancedEntityClass(ec, new ArrayList<EntityCategory>(categoryDao.find(new EntityCategorySearchCriteria()).getResults()));        
    }

    public List<EnchancedEntityClass> getEntries() {
        SearchResult<EntityClass> l = classDao.find(null);
        List<EnchancedEntityClass> res = new ArrayList<EnchancedEntityClass>();

        for (EntityClass e : l.getResults()) {
            res.add(repack(e));
        }

        // add charity class to the result!
        res.add(prepareCharityClass());
        return res;
    }

    private EnchancedEntityClass repack(EntityClass e) {

        EntityCategorySearchCriteria criteria = new EntityCategorySearchCriteria();
        criteria.setEntityClass(e.getId());
        SearchResult<EntityCategory> l = categoryDao.find(criteria);
        return new EnchancedEntityClass(e, new ArrayList<EntityCategory>(l.getResults()));
    }

    public EnchancedEntityClass getEntry(Long key) {
        if (key == CHARITY_CLASS_ID) {
            return prepareCharityClass();
        } else
            return repack(classDao.get(key));
    }

}
