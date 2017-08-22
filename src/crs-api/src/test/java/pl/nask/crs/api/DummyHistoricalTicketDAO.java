package pl.nask.crs.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.dao.ibatis.InternalTicketIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;

public class DummyHistoricalTicketDAO implements GenericDAO<InternalHistoricalTicket, Long> {
	private InternalTicketIBatisDAO ticketDAO;

	private List<InternalHistoricalTicket> tickets = new LinkedList<InternalHistoricalTicket>();

    private long chngIdSeq = 0;

	@Override
	public void create(InternalHistoricalTicket dto) {
		InternalTicket ticket = ticketDAO.get(dto.getId());
		BeanUtils.copyProperties(ticket, dto);
		dto.setChangeId(chngIdSeq++);
		tickets.add(dto);
	}

	@Override
	public InternalHistoricalTicket get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean lock(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(InternalHistoricalTicket dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(InternalHistoricalTicket dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LimitedSearchResult<InternalHistoricalTicket> find(
			SearchCriteria<InternalHistoricalTicket> criteria, long offset,
			long limit) {
		return find(criteria, offset, limit, null);
	}

	@Override
	public LimitedSearchResult<InternalHistoricalTicket> find(
			SearchCriteria<InternalHistoricalTicket> criteria, long offset,
			long limit, List<SortCriterion> sortBy) {
		SearchResult<InternalHistoricalTicket> partialResult = find(criteria, sortBy);
		return new LimitedSearchResult<InternalHistoricalTicket>(
				criteria, sortBy, limit, offset, 
				partialResult.getResults().subList((int) offset, Math.min((int) (offset + limit), partialResult.getResults().size())), 
				partialResult.getResults().size()); 
	}

	@Override
	public SearchResult<InternalHistoricalTicket> find(
			SearchCriteria<InternalHistoricalTicket> criteria) {
		return find(criteria, null);
	}

	@Override
	public SearchResult<InternalHistoricalTicket> find(
			SearchCriteria<InternalHistoricalTicket> criteria,
			List<SortCriterion> sortBy) {
		List<InternalHistoricalTicket> partial = new ArrayList<InternalHistoricalTicket>();
		for (InternalHistoricalTicket t: tickets) {
			if (isMatch(criteria, t)) {
				partial.add(t);
			}
		}
		
		if (!Validator.isEmpty(sortBy) && sortBy.size() > 1) {
			throw new IllegalArgumentException("not supported sortBy size > 1");
		} else if (!Validator.isEmpty(sortBy)) {
			final SortCriterion c = sortBy.get(0);
			if (!"Chng_ID".equals(c.getSortBy())) {
				throw new IllegalArgumentException("Only Chng_ID criterion is supported");
			}
			Collections.sort(partial, new Comparator<InternalHistoricalTicket>() {
				@Override
				public int compare(InternalHistoricalTicket o1,
						InternalHistoricalTicket o2) {
					return (c.isAscending()?1:-1) * Long.valueOf(o1.getChangeId()).compareTo(Long.valueOf(o2.getChangeId()));
				}
			});
		}
		
		
		return new SearchResult<InternalHistoricalTicket>(criteria, sortBy, partial);
	}

	private boolean isMatch(SearchCriteria criteria,
			InternalHistoricalTicket t) {
		HistoricTicketSearchCriteria crit = (HistoricTicketSearchCriteria) criteria;
		
		if (crit.getTicketId() != null && t.getId() != (long) crit.getTicketId())
			return false;
		if (crit.getDomainName() != null && !t.getDomainName().startsWith(crit.getDomainName()))
			return false;
		if (crit.getAccountId() != null && t.getResellerAccountId() != (long) crit.getAccountId())
			return false;
		if (crit.getDomainHolder() != null && !t.getDomainHolder().startsWith(crit.getDomainHolder()))
			return false;
		if (crit.getClazz() != null && !t.getDomainHolderClass().equals(crit.getClazz()))
			return false;
		if (crit.getCategory() != null && !t.getDomainHolderCategory().equals(crit.getCategory()))
			return false;
		
		return true;
	}
	
	public void setTicketDAO(InternalTicketIBatisDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}
}
