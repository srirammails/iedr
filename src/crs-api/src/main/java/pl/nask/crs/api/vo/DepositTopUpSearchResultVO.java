package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.DepositTopUp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositTopUpSearchResultVO {

    private long totalResults;
    private List<DepositTopUpVO> results = new ArrayList<DepositTopUpVO>();

    public DepositTopUpSearchResultVO() {
    }

    public DepositTopUpSearchResultVO(LimitedSearchResult<DepositTopUp> limitedSearchResult) {
        this.totalResults = limitedSearchResult.getTotalResults();
        for (DepositTopUp depositTopUp : limitedSearchResult.getResults() ) {
            results.add(new DepositTopUpVO(depositTopUp));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<DepositTopUpVO> getResults() {
        return results;
    }
}
