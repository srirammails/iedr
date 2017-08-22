package pl.nask.crs.vat;

import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.vat.exceptions.NextValidVatNotFoundException;
import pl.nask.crs.vat.exceptions.VatFromDuplicationException;
import pl.nask.crs.vat.exceptions.VatNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface VatService {

    int addVatRate(String category, Date from, double vatRate, String username) throws VatFromDuplicationException, ThirdDecimalPlaceException;

    void invalidate(int vatId) throws VatNotFoundException, NextValidVatNotFoundException;

    List<Vat> getValid();

    List<String> getCategories();
}
