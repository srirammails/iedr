package pl.nask.crs.api.vo;

import pl.nask.crs.app.commons.DomainSettings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainSettingsVO {
    private int minCount;
    private int maxCount;
    private int failureLimit;

    public DomainSettingsVO() {
    }

    public DomainSettingsVO(DomainSettings domainSettings) {
        this.minCount = domainSettings.getMinNameserverCount();
        this.maxCount = domainSettings.getMaxNameserverCount();
        this.failureLimit = domainSettings.getFailureLimit();
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getFailureLimit() {
        return failureLimit;
    }
}
