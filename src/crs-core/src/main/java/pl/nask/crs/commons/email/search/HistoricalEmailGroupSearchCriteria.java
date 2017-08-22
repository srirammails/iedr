package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

public class HistoricalEmailGroupSearchCriteria implements SearchCriteria<HistoricalObject<EmailGroup>> {
    protected Long id;
    protected String name;
    protected Date changeDate;
    protected String histChangedBy;
    protected Date histChangeDate;

    public HistoricalEmailGroupSearchCriteria() {}

    public HistoricalEmailGroupSearchCriteria(Long id, String name, Date changeDate, String histChangedBy, Date histChangeDate) {
        this.id = id;
        this.name = name;
        this.changeDate = changeDate;
        this.histChangedBy = histChangedBy;
        this.histChangeDate = histChangeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getHistChangedBy() {
        return histChangedBy;
    }

    public void setHistChangedBy(String histChangedBy) {
        this.histChangedBy = histChangedBy;
    }

    public Date getHistChangeDate() {
        return histChangeDate;
    }

    public void setHistChangeDate(Date histChangeDate) {
        this.histChangeDate = histChangeDate;
    }
}
