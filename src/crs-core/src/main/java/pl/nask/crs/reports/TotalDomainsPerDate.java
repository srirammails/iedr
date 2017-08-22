package pl.nask.crs.reports;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsPerDate extends TotalDomains {

    private Date date;
    private String domainClass;
    private String domainCategory;
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	public String getDomainClass() {
		return domainClass;
	}

	public void setDomainClass(String domainClass) {
		this.domainClass = domainClass;
	}

	public String getDomainCategory() {
		return domainCategory;
	}

	public void setDomainCategory(String domainCategory) {
		this.domainCategory = domainCategory;
	}
    
    
}
