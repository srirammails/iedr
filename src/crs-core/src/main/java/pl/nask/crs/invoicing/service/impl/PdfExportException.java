package pl.nask.crs.invoicing.service.impl;

import pl.nask.crs.commons.exporter.ExportException;

public class PdfExportException extends ExportException {

	public PdfExportException() {
		super();
	}

	public PdfExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfExportException(String message) {
		super(message);
	}

	public PdfExportException(Throwable cause) {
		super(cause);
	}

}
