package pl.nask.crs.api.vo;

import pl.nask.crs.statuses.Status;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusVO {

    private int id;
    private String description;

    public StatusVO() {
    }

    public StatusVO(Status status) {
        this.id = status.getId();
        this.description = status.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
