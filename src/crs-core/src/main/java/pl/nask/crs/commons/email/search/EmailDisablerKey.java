package pl.nask.crs.commons.email.search;

public class EmailDisablerKey {
    protected long emailId;
    protected String nicHandle;

    public EmailDisablerKey(long emailId, String nicHandle) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
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
}
