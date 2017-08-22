package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;

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
public class DepositSearchResultVO {

    private long totalResults;
    private List<DepositVO> results = new ArrayList<DepositVO>();

    public DepositSearchResultVO() {
    }

//    public static DepositSearchResultVO newDepositInstance(LimitedSearchResult<Deposit> searchResult) {
//        return new DepositSearchResultVO(searchResult);
//    }
//
//    public static DepositSearchResultVO newDepositInfoInstance(LimitedSearchResult<DepositInfo> searchResult) {
//        return new DepositSearchResultVO(searchResult);
//    }

    public DepositSearchResultVO(LimitedSearchResult<? extends Deposit> searchResult) {
        this.totalResults = searchResult.getTotalResults();
        for (Deposit deposit : searchResult.getResults()) {
            if (deposit instanceof DepositInfo) {
                results.add(new DepositVO((DepositInfo)deposit));
            } else {
                results.add(new DepositVO(deposit));
            }
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<DepositVO> getResults() {
        return results;
    }
}
