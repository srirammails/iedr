package pl.nask.crs.vat;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.vat.dao.VatDAO;

import java.util.*;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

public class VatDictionary implements Dictionary<String, Vat> {

    private VatDAO vatDAO;
    private Date forDate;
    private List<Vat> entries;

    /**
     * @param vatDAO vat DAO
     * @param forDate the date from which (inclusively) the vat rate applies to the category
     */
    public VatDictionary(VatDAO vatDAO, Date forDate) {
        Validator.assertNotNull(vatDAO, "vatDAO param");
        Validator.assertNotNull(forDate, "forDate param");
        this.vatDAO = vatDAO;
        this.forDate = forDate;
        entries = prepareEntries();
    }

    public Vat getEntry(String category) {
        Validator.assertNotNull(category, "vat category param");
        return vatDAO.getCurrentVat(category, forDate);
    }

    public List<Vat> getEntries() {
        return entries;
    }

    private List<Vat> prepareEntries() {
        List<Vat> allVats = vatDAO.getCurrentAndPreviousVatList(forDate);
        Map<String, Vat> tmp = new HashMap<String, Vat>();
        for (Vat vat : allVats) {
            if (tmp.containsKey(vat.getCategory().toLowerCase())) {
                tmp.put(vat.getCategory().toLowerCase(), getLatestVat(tmp.get(vat.getCategory().toLowerCase()), vat));
            } else {
                tmp.put(vat.getCategory().toLowerCase(), vat);
            }
        }
        return Collections.unmodifiableList(new ArrayList<Vat>(tmp.values()));
    }

    private Vat getLatestVat(Vat vatFromMap, Vat vat) {
        if (vat.getFromDate().after(vatFromMap.getFromDate())) {
            return vat;
        } else {
            return vatFromMap;
        }
    }
}
