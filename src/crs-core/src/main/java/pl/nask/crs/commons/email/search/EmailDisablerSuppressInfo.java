package pl.nask.crs.commons.email.search;


import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class EmailDisablerSuppressInfo {
        private int emailId;
        private String nicHandle;
        private boolean disabled;

    public EmailDisablerSuppressInfo() {
        // for jax-ws
    }

    public EmailDisablerSuppressInfo(int emailId, String nicHandle, boolean disabled) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
        this.disabled = disabled;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
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

    public String getDisabled() {
        if (this.disabled) {
            return "Y";
        }
        else {
            return "N";
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { Long.valueOf(emailId), nicHandle, Boolean.valueOf(disabled) });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EmailDisablerSuppressInfo)) {
            return false;
        }
        EmailDisablerSuppressInfo other = (EmailDisablerSuppressInfo) obj;
        return this.emailId == other.emailId
                && this.nicHandle.equals(nicHandle)
                && this.disabled == other.disabled;
    }

}
