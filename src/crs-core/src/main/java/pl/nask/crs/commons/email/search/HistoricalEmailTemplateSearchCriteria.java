package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

public class HistoricalEmailTemplateSearchCriteria implements SearchCriteria<HistoricalObject<EmailTemplate>> {
    protected Integer id;
    protected String histChangedBy;
    protected Date histChangeDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
