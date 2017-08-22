package pl.nask.crs.defaults.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.defaults.EmailInvoiceFormat;

import java.sql.SQLException;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class EmailInvoiceFormatHandler implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;
        if (parameter instanceof EmailInvoiceFormat) {
            EmailInvoiceFormat format = (EmailInvoiceFormat) parameter;
            if (format.equals(EmailInvoiceFormat.XML)) {
                value = EmailInvoiceFormat.XML.getFormat();
            } else if (format.equals(EmailInvoiceFormat.PDF)) {
                value = EmailInvoiceFormat.PDF.getFormat();
            } else if (format.equals(EmailInvoiceFormat.BOTH)) {
                value = EmailInvoiceFormat.BOTH.getFormat();
            } else if (format.equals(EmailInvoiceFormat.NONE)) {
                value = EmailInvoiceFormat.NONE.getFormat();
            }
        }
        setter.setString(value);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String format = resultGetter.getString();
        if (EmailInvoiceFormat.XML.getFormat().equalsIgnoreCase(format)) {
            return EmailInvoiceFormat.XML;
        } else if (EmailInvoiceFormat.PDF.getFormat().equalsIgnoreCase(format)) {
            return EmailInvoiceFormat.PDF;
        } else if (EmailInvoiceFormat.BOTH.getFormat().equalsIgnoreCase(format)) {
            return EmailInvoiceFormat.BOTH;
        } else if (EmailInvoiceFormat.NONE.getFormat().equalsIgnoreCase(format)) {
            return EmailInvoiceFormat.NONE;
        } else {
            return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
