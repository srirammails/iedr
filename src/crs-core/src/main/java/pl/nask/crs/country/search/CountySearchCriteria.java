package pl.nask.crs.country.search;

import pl.nask.crs.commons.search.SearchCriteria;

public class CountySearchCriteria implements SearchCriteria<String> {
    private Long code;
    private String name;

    public CountySearchCriteria(Long countryCode) {
        this.code = countryCode;
    }

    public CountySearchCriteria(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
