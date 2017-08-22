package pl.nask.crs.accounts.services.impl;

import pl.nask.crs.commons.exporter.ExportException;

public class AccountExportException extends ExportException {

	public AccountExportException(String msg, Throwable e) {
		super(msg, e);
	}
}
