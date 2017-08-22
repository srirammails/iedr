package pl.nask.crs.api.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSearchResultVO {

	private List<TicketVO> list;
	private long totalResults;

	/**
	 * use {@link #TicketSearchResultVO(List, long)} instead - this is left public only to let Enunciate generate WS documentation properly
	 */
	public TicketSearchResultVO() {
		// for jax-ws
	}
	
	public TicketSearchResultVO(List<TicketVO> list, long totalResults) {
		this.list = list;
		this.totalResults = totalResults;
	}

	public List<TicketVO> getList() {
		return list;
	}

	public long getTotalResults() {
		return totalResults;
	}

}
