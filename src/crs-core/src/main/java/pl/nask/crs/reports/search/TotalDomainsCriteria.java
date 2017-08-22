package pl.nask.crs.reports.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.reports.TotalDomains;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class TotalDomainsCriteria implements SearchCriteria<TotalDomains> {

    private List<String> excludedRegistrarsNic;

    public TotalDomainsCriteria() {}

    public TotalDomainsCriteria(List<String> excludedRegistrarsNic) {
        this.excludedRegistrarsNic = excludedRegistrarsNic;
    }

    public List<String> getExcludedRegistrarsNic() {
        return excludedRegistrarsNic;
    }

    public void setExcludedRegistrarsNic(String... excludedRegistrarsNic) {
        this.excludedRegistrarsNic = Arrays.asList(excludedRegistrarsNic);
    }
}
