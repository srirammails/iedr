package pl.nask.crs.commons.email.search;

public class HistoricalEmailGroupKey {
    protected long id;
    protected long changeId;

    public HistoricalEmailGroupKey(long id, long changeId) {
        this.id = id;
        this.changeId = changeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }
}
