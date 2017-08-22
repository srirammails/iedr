package pl.nask.crs.payment;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum PaymentMethod {
    ADP("Deposit"), CC("Credit Card"), DEB("Debit Card");

    private PaymentMethod(String fullName) {
        this.fullName = fullName;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }
}
