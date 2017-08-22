package pl.nask.crs.nichandle.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalNicHandleSearchCriteria implements SearchCriteria<HistoricalObject<NicHandle>> {

    private String nicHandleId;
    private Date changeDate;

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getNicHandleId() {
        return ("".equals(nicHandleId))? null : nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }
}
