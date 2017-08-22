package pl.nask.crs.nichandle.dao.ibatis.objects;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class InternalHistoricalNicHandle extends InternalNicHandle{

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
