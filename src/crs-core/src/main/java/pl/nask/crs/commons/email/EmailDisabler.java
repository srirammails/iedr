package pl.nask.crs.commons.email;

import java.util.Date;

public class EmailDisabler {
    private EmailTemplate emailTemplate;
    private String nicHandle;
    private boolean disabled;
    private Date changeDate;

    public EmailDisabler() {}

    public EmailDisabler(
        EmailTemplate emailTemplate, 
        String nicHandle, 
        boolean disabled
    ) {
        this.emailTemplate = emailTemplate;
        this.nicHandle = nicHandle;
        this.disabled = disabled;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmailDisabler(");
        sb.append("emailTemplate=");
        sb.append(emailTemplate);
        sb.append(",nicHandle=");
        sb.append(nicHandle);
        sb.append(",disabled=");
        sb.append(disabled);
        sb.append(",changeDate=");
        sb.append(changeDate);
        sb.append(")");
        return sb.toString();
    }

}
