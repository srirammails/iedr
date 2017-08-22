package pl.nask.crs.invoicing.service;

import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.impl.InvoiceExportException;
import pl.nask.crs.payment.Invoice;

public interface InvoiceExporter {

	/**
	 * Transforms (exports) given invoice object. Should throw an exception if the export fails. 
	 * 
	 * @param invoice
	 * @throws InvoiceExportException 
	 * @throws ExportException 
	 */
	void export(Invoice invoice) throws ExportException;
}
