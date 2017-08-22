package pl.nask.crs.web.ticket.wrappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntityClassFactory;

public class EntityWrapper {

    private final EntityClassFactory entityClassFactory;

    private Map<String, EnchancedEntityClass> classesMap;
    private HashMap<String, Map<String, EntityCategory>> categoriesMap;
    private Map<Long, EntityCategory> categoriesMap2;
    private Map<Long, EnchancedEntityClass> classesMap2;
    
    public EntityWrapper(EntityClassFactory entityClassFactory) {
        this.entityClassFactory = entityClassFactory;
    }
    
    private void initEntityMaps() {
        if (classesMap == null) {
            List<EnchancedEntityClass> classes = entityClassFactory.getEntries();
            classesMap = new HashMap<String, EnchancedEntityClass>();
            classesMap2 = new HashMap<Long, EnchancedEntityClass>();
            categoriesMap = new HashMap<String, Map<String, EntityCategory>>();
            categoriesMap2 = new HashMap<Long, EntityCategory>();
            for (EnchancedEntityClass c : classes) {
                classesMap.put(c.getName().toLowerCase(), c);
                classesMap2.put(c.getId(), c);
                HashMap<String, EntityCategory> categoriesSubMap = new HashMap<String, EntityCategory>();
                categoriesMap.put(c.getName().toLowerCase(), categoriesSubMap);
                for (EntityCategory ec : c.getCategories()) {
                    categoriesSubMap.put(ec.getName().toLowerCase(), ec);
                    categoriesMap2.put(ec.getId(), ec);
                }
            }
        }
    }

    
    private Map<String, EnchancedEntityClass> getString2ClassMap() {
        initEntityMaps();
        return classesMap;
    }

    private Map<Long, EnchancedEntityClass> getLong2ClassMap() {
        initEntityMaps();
        return classesMap2;
    }

    private HashMap<String, Map<String, EntityCategory>> getString2CategoryMap() {
        initEntityMaps();
        return categoriesMap;
    }

    private Map<Long, EntityCategory> getLong2CategoryMap() {
        initEntityMaps();
        return categoriesMap2;
    }
    
    public EnchancedEntityClass getClass(String name) {
        if (name == null)
            return null;
        return getString2ClassMap().get(name.toLowerCase());
    }
    
    public EntityClass getClass(Long id) {
        return getLong2ClassMap().get(id);
    }

    public EntityCategory getCategory(String className, String categoryName) {
        if (categoryName == null || className == null)
            return null;
        Map<String, EntityCategory> m = getString2CategoryMap().get(className.toLowerCase());
        if(m==null)
            return null;
        return m.get(categoryName.toLowerCase());
    }

    public EntityCategory getCategory(Long id) {
        return getLong2CategoryMap().get(id);
    }

    
    
}
