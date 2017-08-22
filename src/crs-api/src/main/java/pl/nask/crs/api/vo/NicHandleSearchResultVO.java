package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.nichandle.NicHandle;


@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NicHandleSearchResultVO {

    private long totalResults;
    private List<NicHandleVO> results = new ArrayList<NicHandleVO>();
    
    /**
     * use {@link #NicHandleSearchResultVO(LimitedSearchResult)} instead - this is left public only to let Enunciate generate WS documentation properly
     */
    public NicHandleSearchResultVO() {
		// for jax-ws
	}
    
    public NicHandleSearchResultVO(LimitedSearchResult<NicHandle> searchRes) {
        this.totalResults = searchRes.getTotalResults();
        for (NicHandle nh: searchRes.getResults()) {
            results.add(new NicHandleVO(nh));
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<NicHandleVO> getResults() {
        return results;
    }
    
    

}
