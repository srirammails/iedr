package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

public class HistoricalEmailDisablerSearchCriteria implements SearchCriteria<HistoricalObject<EmailDisabler>> {
    protected Long emailId;
    protected String nicHandle;
    protected Boolean disabled;
    protected Date changeDate;
    protected String histChangedBy;
    protected Date histChangeDate;

    public HistoricalEmailDisablerSearchCriteria() {}

    public HistoricalEmailDisablerSearchCriteria(Long emailId, String nicHandle, Boolean disabled, Date changeDate, String histChangedBy, Date histChangeDate) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
        this.disabled = disabled;
        this.changeDate = changeDate;
        this.histChangedBy = histChangedBy;
        this.histChangeDate = histChangeDate;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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
