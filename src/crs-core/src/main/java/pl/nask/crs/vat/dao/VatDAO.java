package pl.nask.crs.vat.dao;

import pl.nask.crs.vat.Vat;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface VatDAO {

    Vat getCurrentVat(String category, Date forDate);

    List<Vat> getCurrentAndPreviousVatList(Date forDate);


    int create(Vat vat);

    void invalidate(int vatId);

    Vat get(int vatId);

    Vat getVatForCategoryAndFrom(String category, Date from);

    Vat getCurrentVatExceptVatId(String category, Date forDate, int vatId);

    void flushCache();

    List<Vat> getAllValid();

    List<Vat> getCurrentAndFutureVatList(Date forDate);

    List<String> getCategories();
}
