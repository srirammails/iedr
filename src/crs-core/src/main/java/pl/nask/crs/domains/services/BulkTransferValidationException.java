package pl.nask.crs.domains.services;

import java.util.List;

public class BulkTransferValidationException extends Exception {

	private List<String> validationErrors;

	public BulkTransferValidationException(List<String> errors) {
		this.validationErrors = errors;
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}
}
