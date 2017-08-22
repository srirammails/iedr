package pl.nask.crs.api.vo;

import pl.nask.crs.entities.EntityCategory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityCategoryVO {

    private long id;
    private String name;

    public EntityCategoryVO() {
    }

    public EntityCategoryVO(EntityCategory entityCategory) {
        this.id = entityCategory.getId();
        this.name = entityCategory.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
