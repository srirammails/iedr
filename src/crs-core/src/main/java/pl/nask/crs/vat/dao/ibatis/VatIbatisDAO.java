package pl.nask.crs.vat.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;
import pl.nask.crs.vat.Vat;
import pl.nask.crs.vat.dao.VatDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class VatIbatisDAO extends SqlMapClientLoggingDaoSupport implements VatDAO {

    /**
     * Returns vat rate that currently applies to the category for given date
     * @param category vat category
     * @param forDate the date from which (inclusively) the vat rate applies to the category.
     * @return
     */
    @Override
    public Vat getCurrentVat(String category, Date forDate) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("category", category);
        params.put("forDate", forDate);
        return performQueryForObject("vat.getCurrentVat", params);
    }

    @Override
    public List<Vat> getCurrentAndPreviousVatList(Date forDate) {
        return performQueryForList("vat.getCurrentAndPreviousVatList", forDate);
    }

    @Override
    public Vat getVatForCategoryAndFrom(String category, Date from) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("category", category);
        params.put("from", from);
        return performQueryForObject("vat.getVatsForCategoryAndFrom", params);
    }

    @Override
    public int create(Vat vat) {
        return this.<Integer>performInsert("vat.create", vat);
    }

    @Override
    public void invalidate(int vatId) {
        performUpdate("vat.invalidate", vatId);
    }

    @Override
    public Vat get(int vatId) {
        return performQueryForObject("vat.getVatById", vatId);
    }

    @Override
    public Vat getCurrentVatExceptVatId(String category, Date forDate, int vatId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("category", category);
        params.put("forDate", forDate);
        params.put("vatId", vatId);
        return performQueryForObject("vat.getCurrentVatExceptVatId", params);
    }

    @Override
    public void flushCache() {
        getSqlMapClient().flushDataCache();
    }

    @Override
    public List<Vat> getAllValid() {
        return performQueryForList("vat.getAllValid");
    }

    @Override
    public List<Vat> getCurrentAndFutureVatList(Date forDate) {
        return performQueryForList("vat.getCurrentAndFutureVatList", forDate);
    }

    @Override
    public List<String> getCategories() {
        return performQueryForList("vat.getVatCategory");
    }
}
