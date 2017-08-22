package pl.nask.crs.user;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum NRCLevel {
    UNDEFINED(0, "Undefined"),
    DIRECT(1, "Direct"),
    REGISTRAR(2, "Registrar"),
    SUPER_REGISTRAR(3, "SuperRegistrar"), TAC(4, "TechOrAdminContact");

    private int level;
    private String name;

    private NRCLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
