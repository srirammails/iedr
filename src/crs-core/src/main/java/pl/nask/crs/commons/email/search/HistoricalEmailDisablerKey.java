package pl.nask.crs.commons.email.search;

public class HistoricalEmailDisablerKey {
    protected long emailId;
    protected String nicHandle;
    protected long changeId;

    public HistoricalEmailDisablerKey(long emailId, String nicHandle, long changeId) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
        this.changeId = changeId;
    }

    public long getEmailId() {
        return emailId;
    }

    public void setEmailId(long emailId) {
        this.emailId = emailId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeDate(long changeDate) {
        this.changeId = changeId;
    }
}
