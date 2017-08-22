package pl.nask.crs.web.domains;

import pl.nask.crs.domains.dsm.DsmEventName;

import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ForceDSMChangeWrapper {

    private String domainNames;

    private DsmEventName eventName;

    private int state;

    private String remark;

    public String getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(String domainNames) {
        this.domainNames = domainNames;
    }

    public DsmEventName getEventName() {
        return eventName;
    }

    public void setEventName(DsmEventName eventName) {
        this.eventName = eventName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getDomainNamesAsList() {
        String[] domains = domainNames.split(",");
        List<String> ret = new ArrayList<String>();
        for (String name : domains) {
            ret.add(name.trim());
        }
        return ret;
    }
}
