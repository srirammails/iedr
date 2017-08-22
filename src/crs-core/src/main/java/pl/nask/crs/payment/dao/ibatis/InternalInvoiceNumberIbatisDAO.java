package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalInvoiceNumberIbatisDAO extends SqlMapClientLoggingDaoSupport {

    public Date getMinInvoiceYear() {
        return performQueryForObject("invoicing.getMinInvoiceYear", null);
    }

    public Integer getLastInvoiceNumber(Date forYear) {
        return performQueryForObject("invoicing.getLastInvoiceNumber", forYear);
    }

    public void initLastInvoiceNumber(Date forYear, int lastInvoiceNumber) {
        Calendar c = Calendar.getInstance();
        c.setTime(forYear);
        int year = c.get(Calendar.YEAR);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("year", year);
        params.put("lastInvoiceNumber", lastInvoiceNumber);
        performInsert("invoicing.initLastInvoiceNumber", params);
    }

    public void setNextInvoiceNumber(Date forYear, int nextInvoiceNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("year", forYear);
        params.put("nextInvoiceNumber", nextInvoiceNumber);
        performUpdate("invoicing.updateLast",params);
    }

}
