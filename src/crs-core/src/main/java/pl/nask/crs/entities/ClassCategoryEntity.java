package pl.nask.crs.entities;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ClassCategoryEntity {

    private String className;
    private long classId;
    private String categoryName;
    private long categoryId;

    public ClassCategoryEntity() {}

    public ClassCategoryEntity(String className, String categoryName) {
        this.className = className;
        this.categoryName = categoryName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return String.format("ClassCategoryEntity[className=%s, classId=%s, categoryName=%s, categoryId=%s]", className, classId, categoryName, categoryId);
    }
}
