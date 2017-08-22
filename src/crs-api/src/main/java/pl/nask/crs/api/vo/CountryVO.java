package pl.nask.crs.api.vo;

import pl.nask.crs.country.Country;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CountryVO {

    private int code;
    private String name;
    private List<String> counties;

    public CountryVO() {}

    public CountryVO(Country country) {
        this.code = country.getCode();
        this.name = country.getName();
        this.counties = country.getCounties();
    }
}
