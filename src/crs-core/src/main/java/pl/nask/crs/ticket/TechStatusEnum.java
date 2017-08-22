package pl.nask.crs.ticket;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public enum TechStatusEnum implements TechStatus {
    NEW(0, "New"),
    PASSED(1, "Passed"),
    STALLED(2, "Stalled");

    private final int id;
    private final String desc;

    TechStatusEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    public static TechStatus valueForId(int id) {
        switch (id) {
        case 0: return NEW;
        case 1: return PASSED;
        case 2: return STALLED;
        default:
            throw new IllegalArgumentException("Unexpected status id: " + id);
        }
    }

    public static TechStatusEnum valueOf(TechStatus techStatus) {
        switch (techStatus.getId()) {
            case 0: return NEW;
            case 1: return PASSED;
            case 2: return STALLED;
            default:
                throw new IllegalArgumentException("Unexpected tech status id: " + techStatus.getId());
        }
    }

}
