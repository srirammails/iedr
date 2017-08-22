package pl.nask.crs.price.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.dao.DomainPricingDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPricingIBatisDAO extends GenericIBatisDAO<DomainPrice, String> implements DomainPricingDAO {

    public DomainPricingIBatisDAO() {
        setCreateQueryId("domain-price.create");
        setGetQueryId("domain-price.getById");
        setUpdateQueryId("domain-price.update");
    }

    @Override
    public List<DomainPrice> getDomainPriceList(Date forDate) {
        return performQueryForList("domain-price.getDomainPriceList", forDate);
    }

    @Override
    public DomainPrice getDomainPriceByCode(String productCode, Date forDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productCode", productCode);
        params.put("forDate", forDate);
        return performQueryForObject("domain-price.getDomainPriceByCode", params);
    }

    @Override
    public List<DomainPrice> getAll() {
        return performQueryForList("domain-price.getAll");
    }

    @Override
    public LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters().setLimit(offset, limit).setOrderBy(sortBy);
        return performFind("domain-price.findAll", "domain-price.countFindAll", parameters);
    }
}
