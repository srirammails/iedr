package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.ReauthoriseTransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReauthoriseTransactionSearchResultVO {
    private long totalResults;
    private List<ReauthoriseTransactionVO> results = new ArrayList<ReauthoriseTransactionVO>();

    public ReauthoriseTransactionSearchResultVO() {
    }

    public ReauthoriseTransactionSearchResultVO(LimitedSearchResult<ReauthoriseTransaction> limitedSearchResult) {
        this.totalResults = limitedSearchResult.getTotalResults();
        for (ReauthoriseTransaction transaction : limitedSearchResult.getResults()) {
            results.add(new ReauthoriseTransactionVO(transaction));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<ReauthoriseTransactionVO> getResults() {
        return results;
    }

}
