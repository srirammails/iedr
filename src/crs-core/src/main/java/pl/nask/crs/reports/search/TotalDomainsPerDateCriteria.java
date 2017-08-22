package pl.nask.crs.reports.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.ReportTypeGranulation;
import pl.nask.crs.reports.TotalDomainsPerDate;

import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsPerDateCriteria implements SearchCriteria<TotalDomainsPerDate> {

    private Date registrationFrom;
    private Date registrationTo;

    //report granulation is required
    private ReportTimeGranulation reportTimeGranulation;
    private ReportTypeGranulation reportTypeGranulation;

    private CustomerType customerType;

    private String holderClass;
    private String holderCategory;
    private Long accountId;

    public enum CustomerType {
        REGISTRAR, DIRECT
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = registrationFrom;
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = registrationTo;
    }

    public ReportTimeGranulation getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public void setReportTimeGranulation(ReportTimeGranulation reportTimeGranulation) {
        this.reportTimeGranulation = reportTimeGranulation;
    }

    public ReportTypeGranulation getReportTypeGranulation() {
        return reportTypeGranulation;
    }

    public void setReportTypeGranulation(ReportTypeGranulation reportTypeGranulation) {
        this.reportTypeGranulation = reportTypeGranulation;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        this.holderClass = holderClass;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        this.holderCategory = holderCategory;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
