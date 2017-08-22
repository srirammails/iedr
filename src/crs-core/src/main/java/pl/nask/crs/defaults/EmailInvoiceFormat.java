package pl.nask.crs.defaults;

/**
* (C) Copyright 2013 NASK
* Software Research & Development Department
*/
public enum EmailInvoiceFormat {
    XML("xml"), PDF("pdf"), BOTH("both"), NONE("none");

    private final String format;

    EmailInvoiceFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
