package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.Domain;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainSearchResultVO {

    private long totalResults;
    private List<DomainVO> results = new ArrayList<DomainVO>();
    private List<String> nameResults = new ArrayList<String>();

    /**
     * Use {@link #DomainSearchResultVO(LimitedSearchResult)} or {@link #DomainSearchResultVO(LimitedSearchResult, long)} instead - this is left public only to let Enunciate generate WS documentation 
     */
    public DomainSearchResultVO() {
		// for jax-ws - 
	}
    
    public DomainSearchResultVO(LimitedSearchResult<Domain> searchRes, boolean allData) {
        this.totalResults = searchRes.getTotalResults();
        for (Domain d : searchRes.getResults()) {
            results.add(new DomainVO(d, allData));
        }
    }
    
    public DomainSearchResultVO(LimitedSearchResult<Domain> searchRes) {
    	this(searchRes, true);
    }

    public DomainSearchResultVO(LimitedSearchResult<String> searchRes, long total) {
        this.totalResults = total;
        this.nameResults.addAll(searchRes.getResults());
    }
        
    public long getTotalResults() {
        return totalResults;
    }

    public List<DomainVO> getResults() {
        return results;
    }

    public List<String> getNameResults() {
        return nameResults;
    }
}
