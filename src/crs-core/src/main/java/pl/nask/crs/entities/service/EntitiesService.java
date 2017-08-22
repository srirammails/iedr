package pl.nask.crs.entities.service;

import pl.nask.crs.entities.ClassCategoryEntity;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public interface EntitiesService {
    /**
     * Checks holder class and category
     *
     *
     * @param className holder class name
     * @param categoryName holder category name
     * @param nicHandleId nicHandle id to check category permission for, if null permissions will not be check
     * @throws HolderClassNotExistException
     * @throws HolderCategoryNotExistException
     * @throws ClassDontMatchCategoryException
     * @throws pl.nask.crs.entities.exceptions.ClassCategoryPermissionException thrown when nicHasndle do not have permission to use category
     */
    ClassCategoryEntity checkHolderEntities(String className, String categoryName, String nicHandleId)
            throws HolderClassNotExistException, HolderCategoryNotExistException, ClassDontMatchCategoryException;

    /**
     * Checks if holder has permission to class/category
     *
     * @param className
     * @param categoryName
     * @param nicHandleId
     * @throws ClassCategoryPermissionException
     */
    void checkHolderEntitiesPermissions(String className, String categoryName, String nicHandleId)
            throws ClassCategoryPermissionException;

    List<ClassCategoryEntity> getClassCategory();
}
