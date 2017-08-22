package pl.nask.crs.iedrapi;

import java.util.ArrayList;
import java.util.List;

public class ValidationCallback {
	private List<String> errors = new ArrayList<String>();

	public boolean hasSchemaErrors() {
		return !errors.isEmpty();
	}
	
	public List<String> getErrorMessages() {
		return errors;
	}
	
	public void addErrorMessage(String message) {
		errors.add(message);
	}
}
