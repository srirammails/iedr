package pl.nask.crs.user.dao.ibatis.objects;

import java.util.Date;

public class InternalHistoricalUser extends InternalUser {

    private Date changeDate;
    private String changedBy;
    private long changeId;

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

}
