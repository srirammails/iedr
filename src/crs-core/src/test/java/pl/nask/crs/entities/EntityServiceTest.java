package pl.nask.crs.entities;

import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.service.EntitiesService;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class EntityServiceTest extends AbstractTest {

    @Resource
    private EntitiesService entitiesService;

    @Test
    public void checkHolderEntitiesTest() throws Exception {
        entitiesService.checkHolderEntities("Natural Person", "Personal Name", "APIT4-IEDR");
    }

    @Test(expectedExceptions = HolderClassNotExistException.class)
    public void holderClassNotExistTest() throws Exception {
        entitiesService.checkHolderEntities("Not existing class", "Personal Name", "APIT4-IEDR");
    }

    @Test(expectedExceptions = HolderCategoryNotExistException.class)
    public void holderCategoryNotExistsTest() throws Exception {
        entitiesService.checkHolderEntities("Natural Person", "Not existing category", "APIT4-IEDR");
    }

    @Test(expectedExceptions = ClassDontMatchCategoryException.class)
    public void holderClassDontMatchCategoryTest() throws Exception {
        entitiesService.checkHolderEntities("Natural Person", "Personal Trading Name", "APIT4-IEDR");
    }

    @Test(expectedExceptions = ClassCategoryPermissionException.class)
    public void classCategoryPErmissionTest() throws Exception {
        entitiesService.checkHolderEntitiesPermissions("Natural Person", "Discretionary Name", "APIT4-IEDR");
    }

    @Test
    public void getClassCategoryTest() {
        List<ClassCategoryEntity> entities = entitiesService.getClassCategory();
        AssertJUnit.assertEquals(32, entities.size());
    }

}
