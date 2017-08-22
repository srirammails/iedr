package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.search.SearchCriteria;

import java.util.Date;

public class EmailDisablerSearchCriteria implements SearchCriteria<EmailDisabler> {
    protected Integer emailId;
    protected String nicHandle;
    protected Date changeDate;

    public EmailDisablerSearchCriteria() {}

    public EmailDisablerSearchCriteria(Integer emailId, String nicHandle, Date changeDate) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
        this.changeDate = changeDate;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}
