package pl.nask.crs.payment;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum OperationType {
    REGISTRATION("registration"), RENEWAL("renewal"), TRANSFER("transfer");

    private String typeName;

    OperationType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
