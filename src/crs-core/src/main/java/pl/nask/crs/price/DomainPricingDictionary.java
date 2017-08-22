package pl.nask.crs.price;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.price.dao.DomainPricingDAO;

/**
 * Represents domain pricing in a particular moment of time. 
 * The instance of this class will retrieve all prices at the creation time and keep them so no changes in the domain pricing made in the mean time will be visible.
 * A new instance must be created in order to get the current prices.
 * 
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPricingDictionary implements Dictionary<String, DomainPrice> {

    /**
     * The date from which (inclusively) the product price applies
     */
    private Date forDate;
	private List<DomainPrice> priceList;
	private Map<String, DomainPrice> priceMap;

    public DomainPricingDictionary(DomainPricingDAO domainPricingDAO) {
    	this (domainPricingDAO, new Date());
    }

    public DomainPricingDictionary(DomainPricingDAO domainPricingDAO, Date forDate) {
        Validator.assertNotNull(domainPricingDAO, "domain pricing DAO");
        Validator.assertNotNull(forDate, "for Date param");
        this.priceList = domainPricingDAO.getDomainPriceList(forDate);
        this.priceMap = buildMap(priceList);
        this.forDate = forDate;
    }

    private Map<String, DomainPrice> buildMap(List<DomainPrice> priceList) {
    	Map<String, DomainPrice> res = new HashMap<String, DomainPrice>();
    	for (DomainPrice p: priceList) {
    		res.put(p.getId(), p);
    	}
		return res ;
	}

	@Override
    public DomainPrice getEntry(String productCode) {
        Validator.assertNotNull(productCode, "category param");
        return priceMap.get(productCode);
    }

    @Override
    public List<DomainPrice> getEntries() {
        return new ArrayList<DomainPrice>(priceList);
    }
        
    public Date getDate() {
		return forDate;
	}
}
