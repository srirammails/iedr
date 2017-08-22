package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.email.EmailDisabler;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmailDisablerSearchResultVO {

    private List<EmailDisabler> emailDisablers;

    /**
     * use {@link #EmailDisablerSearchResultVO(List, long)} instead - this is
     * left public only to let Enunciate generate WS documentation properly
     */
    public EmailDisablerSearchResultVO() {
        // for jax-ws
    }

    public EmailDisablerSearchResultVO(List<EmailDisabler> emailDisablers) {
        this.emailDisablers = emailDisablers;
    }

    public List<EmailDisabler> getList() {
        return emailDisablers;
    }
}
