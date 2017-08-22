package pl.nask.crs.price;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface PriceService {

    List<DomainPrice> getAll();

    void addPrice(AuthenticatedUser user, String code, String decription, BigDecimal price, int duration, Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct) throws ThirdDecimalPlaceException;

    void save(DomainPrice domainPrice, AuthenticatedUser user) throws ThirdDecimalPlaceException;

    DomainPrice get(String id) throws PriceNotFoundException;

    public LimitedSearchResult<DomainPrice> findAll(long offset, long limit, List<SortCriterion> sortBy);
}
