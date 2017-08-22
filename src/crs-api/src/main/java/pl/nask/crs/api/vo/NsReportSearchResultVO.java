package pl.nask.crs.api.vo;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.nameservers.NsReport;

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
public class NsReportSearchResultVO {

    private long totalResults;
    private List<NsReportVO> results = new ArrayList<NsReportVO>();

    public NsReportSearchResultVO() {
    }

    public NsReportSearchResultVO(LimitedSearchResult<NsReport> searchResult) {
        this.totalResults = searchResult.getTotalResults();
        for (NsReport report : searchResult.getResults()) {
            results.add(new NsReportVO(report));
        }
    }
}
