package pl.nask.crs.user.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;

public class HistoricalUserSearchCriteria implements SearchCriteria<HistoricalObject<User>> {
    private String nicHandleId;

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public HistoricalUserSearchCriteria(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }
}
