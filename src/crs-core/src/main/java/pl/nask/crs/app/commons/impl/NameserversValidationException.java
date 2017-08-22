package pl.nask.crs.app.commons.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.invoicing.service.impl.MultilinedStringBuilder;

public class NameserversValidationException extends Exception {
	@XmlTransient
	private List<Exception> exceptions;
	
	public 	NameserversValidationException(){}
	
	public NameserversValidationException(Map<String, List<String>> validationErrors, List<Exception> causedBy) {
		super(prepareMessage(validationErrors));	
		this.exceptions = causedBy;
	}

	private static String prepareMessage(Map<String, List<String>> validationErrors) {
		List<String> list = new LinkedList<String>();
		for (Map.Entry<String, List<String>> e: validationErrors.entrySet()) {
			String msg = CollectionUtils.toString(e.getValue(), true, ", ");
			list.add(e.getKey() + " : " + msg);
		}
		return MultilinedStringBuilder.buildMultilinedFrom(list, ", ");
	}

	// name used on purpose: can't use a getter since jax-ws/jax-b ignores @XmlTransient in exceptions.
	public Exception retrieveFirstCause() {
		if (exceptions == null) {
			return null;
		} else {
			return exceptions.get(0);
		}
	}

}
