package pl.nask.crs.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClassFactory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.entities.dao.ibatis.InternalEntityCategory;
import pl.nask.crs.entities.dao.ibatis.InternalEntityClass;

/**
 * Decorated version of EntityClassFactory - each list starts with artificial
 * "select option" (both classes and categories). (C) Copyright 2008 NASK
 * Software Research & Development Department
 * 
 * @author Artur Gniadzik
 * 
 */
public class StrutsEntityClassFactory extends EntityClassFactory {

    private static final EntityCategory SELECT_CATEGORY_OPTION = new InternalEntityCategory(-1, "(select category)");
    private static final EnchancedEntityClass SELECT_CLASS_OPTION = new EnchancedEntityClass(
            new InternalEntityClass(-1, "(select class)"), 
            Arrays.asList(new EntityCategory[] { SELECT_CATEGORY_OPTION }));

    public StrutsEntityClassFactory(EntityClassDAO classDao, EntityCategoryDAO categoryDao) {
        super(classDao, categoryDao);
    }
    
    /**
     * returns decorated (with 'select option) version of Class/Categories
     */
    @Override
    public List<EnchancedEntityClass> getEntries() {
        List<EnchancedEntityClass> l = super.getEntries();
        for (EnchancedEntityClass c : l) {
            repack(c);
        }
        l.add(0, SELECT_CLASS_OPTION);
        
        return l;
    }

    private void repack(EnchancedEntityClass c) {
        List<EntityCategory> l = c.getCategories();
        ArrayList<EntityCategory> ll = new ArrayList<EntityCategory>(l.size());
        for(EntityCategory e: l) {
           ll.add(new InternalEntityCategory(c.getId() * 1000 + e.getId(), e.getName()));
        }
        addOption(ll);
        c.getCategories().clear();
        c.getCategories().addAll(ll);       
    }
    
    @Override
    public EnchancedEntityClass getEntry(Long key) {
        EnchancedEntityClass c = super.getEntry(key);
        repack(c);
        return c;
    }

    private void addOption(List<EntityCategory> categories) {
        categories.add(0, SELECT_CATEGORY_OPTION);
    }

}
