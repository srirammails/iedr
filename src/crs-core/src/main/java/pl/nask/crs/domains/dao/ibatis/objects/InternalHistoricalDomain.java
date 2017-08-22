package pl.nask.crs.domains.dao.ibatis.objects;

import java.util.Date;

import pl.nask.crs.domains.DomainStatus;

public class InternalHistoricalDomain extends InternalDomain {

    private long changeId;

    private Date histChangeDate;

    private String changedByNicHandle;

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public Date getHistChangeDate() {
        return histChangeDate;
    }

    public void setHistChangeDate(Date histChangeDate) {
        this.histChangeDate = histChangeDate;
    }

    public String getChangedByNicHandle() {
        return changedByNicHandle;
    }

    public void setChangedByNicHandle(String changedByNicHandle) {
        this.changedByNicHandle = changedByNicHandle;
    }

}
