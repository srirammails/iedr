package pl.nask.crs.invoicing.service.impl;

import java.util.List;

import pl.nask.crs.commons.utils.Validator;

public final class MultilinedStringBuilder {
	private	MultilinedStringBuilder() {
		// utility class
	}
	
	public static String buildMultilinedFrom(String text, String separator) {
		if (Validator.isEmpty(text))
			return "";
		
		String[] lines = text.split(separator);
		StringBuilder builder = new StringBuilder();
		for (String line: lines) {
			if (!Validator.isEmpty(line)) {
				builder.append(line.trim()).append("\n");
			}
		}
		
		return builder.substring(0, builder.length() - 1);
	}
	
	public static String buildMultilinedFrom(List<String> text, String separator) {
		if (Validator.isEmpty(text))
			return "";
				
		StringBuilder builder = new StringBuilder();
		for (String line: text) {
			if (!Validator.isEmpty(line)) {
				builder.append(line.trim()).append("\n");
			}
		}
		
		return builder.substring(0, builder.length() - 1);
	}
}
