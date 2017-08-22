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
import pl.nask.crs.domains.dao.ibatis.InternalDomainIBatisDAO;
import pl.nask.crs.domains.dao.ibatis.InternalHistoricalDomainIbatisDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;

public class DummyHistoricalDomainDAO extends InternalHistoricalDomainIbatisDAO implements GenericDAO<InternalHistoricalDomain, String> {

	private List<InternalHistoricalDomain> domains = new LinkedList<InternalHistoricalDomain>();
	private InternalDomainIBatisDAO domainDAO;

	private long chngIdSeq = 0;

	@Override
	public void create(InternalHistoricalDomain dto) {
		InternalDomain domain = domainDAO.get(dto.getName());
		BeanUtils.copyProperties(domain, dto);
		dto.setChangeId(chngIdSeq++);
		domains.add(dto);
	}

	public void setDomainDAO(InternalDomainIBatisDAO domainDAO) {
		this.domainDAO = domainDAO;
	}

	@Override
	public InternalHistoricalDomain get(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean lock(String id) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void update(InternalHistoricalDomain dto) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void deleteById(String id) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void delete(InternalHistoricalDomain dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SearchResult<InternalHistoricalDomain> find(
			SearchCriteria<InternalHistoricalDomain> criteria) {	
		return find(criteria, null);
	}
	
	@Override
	public LimitedSearchResult<InternalHistoricalDomain> find(
			SearchCriteria<InternalHistoricalDomain> criteria, 
			long offset, long limit) {
		// TODO Auto-generated method stub
		return find(criteria, offset, limit, null);
	}
	
	@Override
	public LimitedSearchResult<InternalHistoricalDomain> find(
			SearchCriteria<InternalHistoricalDomain> criteria, long offset,
			long limit, List<SortCriterion> sortBy) {
		SearchResult<InternalHistoricalDomain> partialResult = find(criteria, sortBy);
		return new LimitedSearchResult<InternalHistoricalDomain>(
				criteria, sortBy, limit, offset, 
				partialResult.getResults().subList((int) offset, Math.min((int) (offset + limit), partialResult.getResults().size())), 
				partialResult.getResults().size()); 
	}

	@Override
	public SearchResult<InternalHistoricalDomain> find(
			SearchCriteria<InternalHistoricalDomain> criteria,
			List<SortCriterion> sortBy) {
		List<InternalHistoricalDomain> partial = new ArrayList<InternalHistoricalDomain>();
		for (InternalHistoricalDomain d: domains) {
			if (isMatch(criteria, d)) {
				partial.add(d);
			}
		}
		
		if (!Validator.isEmpty(sortBy) && sortBy.size() > 1) {
			throw new IllegalArgumentException("not supported sortBy size > 1");
		} else if (!Validator.isEmpty(sortBy)) {
			final SortCriterion c = sortBy.get(0);
			if (!"Chng_ID".equals(c.getSortBy())) {
				throw new IllegalArgumentException("Only Chng_ID criterion is supported");
			}
			Collections.sort(partial, new Comparator<InternalHistoricalDomain>() {
				@Override
				public int compare(InternalHistoricalDomain o1,
						InternalHistoricalDomain o2) {
					return (c.isAscending()?1:-1) * Long.valueOf(o1.getChangeId()).compareTo(Long.valueOf(o2.getChangeId()));
				}
			});
		}
		
		
		return new SearchResult<InternalHistoricalDomain>(criteria, sortBy, partial);
	}

	private boolean isMatch(SearchCriteria criteria,
			InternalHistoricalDomain d) {
		HistoricalDomainSearchCriteria crit = (HistoricalDomainSearchCriteria) criteria;
		
		if (crit.getDomainName() != null && !d.getName().startsWith(crit.getDomainName()))
			return false;
		if (crit.getAccountId() != null && d.getResellerAccountId() != (long) crit.getAccountId())
			return false;
		if (crit.getDomainHolder() != null && !d.getHolder().startsWith(crit.getDomainHolder()))
			return false;
		if (crit.getHolderClass() != null && !d.getHolderClass().equals(crit.getHolderClass()))
			return false;
		if (crit.getHolderCategory() != null && !d.getHolderCategory().equals(crit.getHolderCategory()))
			return false;
		
		return true;
	}
	

	/*
	 * only part of the search criteria is handled by this DAO - it must be assured that the caller will not try to use unimplemented parts 	 
	 */
	private void assertCriteriaContainsOnlyDomainName(
			SearchCriteria criteria) { 
		HistoricalDomainSearchCriteria c = (HistoricalDomainSearchCriteria) criteria;
		Validator.assertNull(c.getChangeDate(), "changeDate");
		Validator.assertNull(c.getHistChangeDate(), "histChangeDate");
		Validator.assertNull(c.getNicHandle(), "nicHandle");
	}
}
