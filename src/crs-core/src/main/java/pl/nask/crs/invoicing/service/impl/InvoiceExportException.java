package pl.nask.crs.invoicing.service.impl;

import pl.nask.crs.commons.exporter.ExportException;


public class InvoiceExportException extends ExportException {

	public InvoiceExportException(String msg, Throwable e) {
		super (msg, e);
	}

}
