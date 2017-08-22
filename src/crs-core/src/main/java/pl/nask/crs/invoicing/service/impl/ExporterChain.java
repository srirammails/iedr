package pl.nask.crs.invoicing.service.impl;

import java.util.List;

import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.invoicing.service.InvoiceExporter;
import pl.nask.crs.payment.Invoice;

public class ExporterChain implements InvoiceExporter {
	private final List<InvoiceExporter> exporters;
	
	public ExporterChain(List<InvoiceExporter> exporters) {
		super();
		this.exporters = exporters;
	}

	@Override
	public void export(Invoice invoice) throws ExportException {
		for (InvoiceExporter e: exporters) {
			e.export(invoice);
		}
	}
}
