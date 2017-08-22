package pl.nask.crs.accounts.search;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountSearchCriteria implements SearchCriteria<HistoricalObject<Account>> {

    private Long id;
    private Date changeDate;

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
