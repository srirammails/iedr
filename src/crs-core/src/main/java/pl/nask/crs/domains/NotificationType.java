package pl.nask.crs.domains;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum NotificationType {

    RENEWAL("renewal date passes"), SUSPENSION("suspension date passes");

    private String desc;

    private NotificationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
