package pl.nask.crs.nichandle.dao;

public class HistoricalNicHandleKey {
    private final String nicHandle;
    private final long changeId;

    public HistoricalNicHandleKey(String nicHandle, long changeId) {
        this.nicHandle = nicHandle;
        this.changeId = changeId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public long getChangeId() {
        return changeId;
    }
}
