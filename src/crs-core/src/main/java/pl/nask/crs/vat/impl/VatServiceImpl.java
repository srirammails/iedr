package pl.nask.crs.vat.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.price.DomainPrice;
import pl.nask.crs.price.DomainPricingDictionary;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.VatService;
import pl.nask.crs.vat.VatValidatorHelper;
import pl.nask.crs.vat.dao.VatDAO;
import pl.nask.crs.vat.email.VatTableModificationEmailParameters;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatServiceImpl implements VatService {

    private final static Logger LOG = Logger.getLogger(VatServiceImpl.class);
    private VatDAO vatDAO;
    private DomainPricingDAO pricingDAO;
    private EmailTemplateSender emailTemplateSender;

    public VatServiceImpl(VatDAO vatDAO, DomainPricingDAO priceDAO, EmailTemplateSender emailTemplateSender) {
        this.vatDAO = vatDAO;
        this.pricingDAO = priceDAO;
        this.emailTemplateSender = emailTemplateSender;
    }

    @Override
    public int addVatRate(String category, Date from, double vatRate, String username) throws VatFromDuplicationException, ThirdDecimalPlaceException {
        validateFrom(category, from);
        Vat vat = Vat.newInstance(category, from, vatRate);
        validateDecimalPlace(vat);
        int id = vatDAO.create(vat);
        try {
            EmailParameters params = new VatTableModificationEmailParameters(vat, username);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.VAT_TABLE_UPDATE.getId(), params);
        } catch (Exception e) {
            LOG.warn("Problem with vat update email occurred.", e);
        }
        return id;
    }

    private void validateFrom(String category, Date from) throws VatFromDuplicationException{
        Vat vat = vatDAO.getVatForCategoryAndFrom(category, from);
        if (vat != null) {
            throw new VatFromDuplicationException();
        }
    }

    private void validateDecimalPlace(Vat vatRate) throws ThirdDecimalPlaceException {
    	List<Vat> list = vatDAO.getAllValid();
    	list.add(vatRate);
    	
        List<DomainPrice> prices = new DomainPricingDictionary(pricingDAO).getEntries();
        for (DomainPrice domainPrice : prices) {
            VatValidatorHelper.validatePrice(new ArrayList(list), domainPrice.getPrice(), domainPrice.getValidFrom(), domainPrice.getValidTo());
        }
    }

    @Override
    public void invalidate(int vatId) throws VatNotFoundException, NextValidVatNotFoundException{
        validateNextRateExists(vatId);
        vatDAO.invalidate(vatId);
    }

    private void validateNextRateExists(int vatId) throws VatNotFoundException, NextValidVatNotFoundException {
        Vat vat = vatDAO.get(vatId);
        if (vat == null) {
            throw new VatNotFoundException();
        }
        Vat nextVat = vatDAO.getCurrentVatExceptVatId(vat.getCategory(), new Date(), vatId);
        if (nextVat == null) {
            throw new NextValidVatNotFoundException();
        }
    }

    @Override
    public List<Vat> getValid() {
        return vatDAO.getAllValid();
    }

    @Override
    public List<String> getCategories() {
        return vatDAO.getCategories();
    }
}
