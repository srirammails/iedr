package pl.nask.crs.domains.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Kasia Fulara
 */
public class HistoricalDomainSearchCriteria implements SearchCriteria<HistoricalObject<Domain>> {

    private String domainName;
    private String domainHolder;
    private String nicHandle;
    private Long accountId;
    private String holderClass;
    private String holderCategory;
    private Date changeDate;
    private Date histChangeDate;

    public HistoricalDomainSearchCriteria() {
	}
    
    public HistoricalDomainSearchCriteria(String domainName) {
    	this.domainName = domainName;
    }
    
    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getDomainName() {
        return ("".equals(domainName)) ? null : domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return ("".equals(domainHolder)) ? null : domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getNicHandle() {
        return ("".equals(nicHandle)) ? null : nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Long getAccountId() {
        return (accountId == null || accountId < 0) ? null : accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        if ("".equals(holderClass) || "(select class)".equals(holderClass)) {
            this.holderClass = null;
        } else {
            this.holderClass = holderClass;
        }
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        if ("".equals(holderCategory) || "(select category)".equals(holderCategory)) {
            this.holderCategory = null;
        } else {
            this.holderCategory = holderCategory;
        }
    }

    public Date getHistChangeDate() {
        return histChangeDate;
    }

    public void setHistChangeDate(Date histChangeDate) {
        this.histChangeDate = histChangeDate;
    }
}
