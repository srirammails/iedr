package pl.nask.crs.api.vo;

import pl.nask.crs.entities.EntityClass;

import javax.swing.text.html.parser.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityClassVO {

    private long id;
    private String name;

    public EntityClassVO() {
    }

    public EntityClassVO(EntityClass entityClass) {
        this.id = entityClass.getId();
        this.name = entityClass.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
