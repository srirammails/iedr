package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.TransferedDomain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@Deprecated
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransferedDomainSearchResultVO {

    private long totalResults;
    private List<TransferedDomainVO> results = new ArrayList<TransferedDomainVO>();

    public TransferedDomainSearchResultVO() {
    }

    public TransferedDomainSearchResultVO(LimitedSearchResult<TransferedDomain> searchRes) {
        this.totalResults = searchRes.getTotalResults();
        for (TransferedDomain d : searchRes.getResults()) {
            results.add(new TransferedDomainVO(d));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<TransferedDomainVO> getResults() {
        return results;
    }
}
