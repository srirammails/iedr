package pl.nask.crs.user;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalLoginUser {

    private String nicHandleId;
    private String ipAddress;

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
