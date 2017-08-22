package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailDisablerSuppressResultVO {

    private List<EmailDisablerSuppressInfo> persistedEmailDisablerSuppressInfo;
    private List<EmailDisablerSuppressInfo> rejectedEmailDisablerSuppressInfo;

    /**
     * use {@link #EmailDisablerSearchResultVO(List, long)} instead - this is
     * left public only to let Enunciate generate WS documentation properly
     */
    public EmailDisablerSuppressResultVO() {
        // for jax-ws
    }

    public EmailDisablerSuppressResultVO(
            List<EmailDisablerSuppressInfo> persistedEmailDisablerSuppressInfo, 
            List<EmailDisablerSuppressInfo> rejectedEmailDisablerSuppressInfo) {
        this.persistedEmailDisablerSuppressInfo = persistedEmailDisablerSuppressInfo;
        this.rejectedEmailDisablerSuppressInfo = rejectedEmailDisablerSuppressInfo;
    }

    public List<EmailDisablerSuppressInfo> getPersistedEmailDisablerSuppressInfo() {
        return persistedEmailDisablerSuppressInfo;
    }

    public List<EmailDisablerSuppressInfo> getRejectedEmailDisablerSuppressInfo() {
        return rejectedEmailDisablerSuppressInfo;
    }
}
