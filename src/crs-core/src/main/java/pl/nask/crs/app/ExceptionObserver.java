package pl.nask.crs.app;

import java.util.ArrayList;
import java.util.List;

public class ExceptionObserver {
	
	private List<Exception> exceptions = new ArrayList<Exception>();

	public void notice (Exception e) {
		exceptions.add(e);
	}
	
	public List<Exception> getExceptions() {
		return exceptions;
	}
}
