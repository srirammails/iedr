package pl.nask.crs.entities.service.impl;

import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.service.EntitiesService;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.commons.utils.Validator;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class EntitiesServiceImpl implements EntitiesService {

    EntityClassDAO classDAO;
    EntityCategoryDAO categoryDAO;

    public EntitiesServiceImpl(EntityClassDAO entityClassDAO, EntityCategoryDAO entityCategoryDAO) {
        this.classDAO = entityClassDAO;
        this.categoryDAO = entityCategoryDAO;
    }

    @Override
    public ClassCategoryEntity checkHolderEntities(String className, String categoryName, String nicHandleId) throws HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException {
        Validator.assertNotEmpty(className, "holder class name");
        Validator.assertNotEmpty(categoryName, "holder category name");
        String clazz = classDAO.getClassByName(className);
        if (clazz == null)
            throw new HolderClassNotExistException(className);
        String category = categoryDAO.getCategoryByName(categoryName);
        if (category == null)
            throw new HolderCategoryNotExistException(categoryName);
        if (!classDAO.isClassMatchCategory(className, categoryName))
            throw new ClassDontMatchCategoryException(className, categoryName);
        return new ClassCategoryEntity(clazz, category);
    }

    @Override
    public void checkHolderEntitiesPermissions(String className, String categoryName, String nicHandleId) throws ClassCategoryPermissionException {
        Validator.assertNotEmpty(className, "holder class name");
        Validator.assertNotEmpty(categoryName, "holder category name");
        if (nicHandleId != null) {
            if (!categoryDAO.hasPermissionToClassCategory(nicHandleId, className, categoryName))
                throw new ClassCategoryPermissionException();
        }
    }

    @Override
    public List<ClassCategoryEntity> getClassCategory() {
        return classDAO.getClassCategoryEntity();
    }
}
