package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.DeletedDomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DeletedDomainsSearchResultVO {

    private long total;
    private List<DeletedDomainVO> deletedDomains;

    public DeletedDomainsSearchResultVO() {
    }

    public DeletedDomainsSearchResultVO(LimitedSearchResult<DeletedDomain> result) {
        this.total = result.getTotalResults();
        setDeletedDomains(result.getResults());
    }

    private void setDeletedDomains(List<DeletedDomain> deletedDomains) {
        if (deletedDomains == null) {
            this.deletedDomains = null;
        } else {
            this.deletedDomains = new ArrayList<DeletedDomainVO>();
            for (DeletedDomain deletedDomain : deletedDomains) {
                this.deletedDomains.add(new DeletedDomainVO(deletedDomain));
            }
        }
    }
}
