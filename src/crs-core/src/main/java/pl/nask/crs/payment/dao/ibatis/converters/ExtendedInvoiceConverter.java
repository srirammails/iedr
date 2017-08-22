package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.ExtendedInvoice;
import pl.nask.crs.payment.dao.ibatis.objects.InternalExtendedInvoice;
import pl.nask.crs.vat.PriceWithVat;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ExtendedInvoiceConverter extends AbstractConverter<InternalExtendedInvoice, ExtendedInvoice> {

    @Override
    protected ExtendedInvoice _to(InternalExtendedInvoice internalExtendedInvoice) {
        PriceWithVat priceWithVat = new PriceWithVat(internalExtendedInvoice.getNetAmount(), internalExtendedInvoice.getVatAmount());
        return new ExtendedInvoice(internalExtendedInvoice.getDomainName(),
                internalExtendedInvoice.getInvoiceNumber(),
                internalExtendedInvoice.getBillingNicHandle(),
                internalExtendedInvoice.getBillingNicHandleName(),
                internalExtendedInvoice.getPaymentMethod(),
                internalExtendedInvoice.getCustomerType(),
                internalExtendedInvoice.getSettledDate(),
                internalExtendedInvoice.getInvoiceDate(),
                internalExtendedInvoice.getCreationDate(),
                internalExtendedInvoice.getDurationMonths(),
                internalExtendedInvoice.getRenewalDate(),
                priceWithVat,
                internalExtendedInvoice.getStartDate(),
                internalExtendedInvoice.getOrderId());
    }

    @Override
    protected InternalExtendedInvoice _from(ExtendedInvoice extendedInvoice) {
        return null;//skipped since we read this value only
    }
}