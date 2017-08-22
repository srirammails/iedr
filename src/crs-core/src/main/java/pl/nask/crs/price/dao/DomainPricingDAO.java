package pl.nask.crs.price.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DomainPricingDAO extends GenericDAO<DomainPrice, String> {

    public List<DomainPrice> getDomainPriceList(Date forDate);

    public DomainPrice getDomainPriceByCode(String productCode, Date forDate);

    public List<DomainPrice> getAll();

    public LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy);
}
