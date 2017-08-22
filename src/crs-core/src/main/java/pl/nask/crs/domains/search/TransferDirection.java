package pl.nask.crs.domains.search;

/**
* (C) Copyright 2013 NASK
* Software Research & Development Department
*/
public enum TransferDirection {
    INBOUND("inbound"),OUTBOUND("outbound");
    private String direction;

    TransferDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
