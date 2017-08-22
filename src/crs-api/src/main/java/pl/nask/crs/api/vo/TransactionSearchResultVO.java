package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.Transaction;

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
public class TransactionSearchResultVO {

    private long totalResults;
    private List<TransactionVO> results = new ArrayList<TransactionVO>();

    public TransactionSearchResultVO() {}

    public TransactionSearchResultVO(LimitedSearchResult<Transaction> limitedSearchResult) {
        this.totalResults = limitedSearchResult.getTotalResults();
        for (Transaction transaction : limitedSearchResult.getResults()) {
            results.add(new TransactionVO(transaction));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<TransactionVO> getResults() {
        return results;
    }
}
