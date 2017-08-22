package pl.nask.crs.payment;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum FileType {

    XML("xml"), PDF("pdf");

    private String type;

    private FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
