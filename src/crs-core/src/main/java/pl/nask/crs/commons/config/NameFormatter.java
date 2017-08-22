package pl.nask.crs.commons.config;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NameFormatter {

    private final static String format = String.format("%%0%dd", 7);

    public enum NamePrefix {
        INV, ACC, DOA
    }

    public enum NamePostfix {
        xml, pdf
    }

    public static String getFormattedName(long id, NamePrefix prefix, NamePostfix postfix) {
        return prefix + String.format(format, id) + "." + postfix;
    }

    public static String getFormattedName(long id, NamePrefix prefix) {
        return prefix + String.format(format, id);
    }
    
    public static String getFormattedName(String name, NamePostfix postfix) {
        return name + "." + postfix;
    }
}
