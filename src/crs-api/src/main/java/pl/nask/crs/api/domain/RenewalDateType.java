package pl.nask.crs.api.domain;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum RenewalDateType {

    OVERDUE("A renewal date of yesterday"),
    RENEWAL_TODAY("A renewal date of today"),
    RENEWAL_THIS_MONTH("A renewal date in the current month");

    private String fullName;

    private RenewalDateType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
