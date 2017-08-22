package pl.nask.crs.api.vo;

import pl.nask.crs.entities.ClassCategoryEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassCategoryVO {

    private String className;
    private long classId;
    private String categoryName;
    private long categoryId;

    public ClassCategoryVO() {}

    public ClassCategoryVO(ClassCategoryEntity classCategoryEntity) {
        this.className = classCategoryEntity.getClassName();
        this.classId = classCategoryEntity.getClassId();
        this.categoryName = classCategoryEntity.getCategoryName();
        this.categoryId = classCategoryEntity.getCategoryId();
    }

    public String getClassName() {
        return className;
    }

    public long getClassId() {
        return classId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
