package pl.nask.crs.accounts.dao.ibatis.objects;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class InternalHistoricalAccount extends InternalAccount {

    private Long changeId;
    private Date histChangeDate;
    private String changedByNicHandle;

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
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
