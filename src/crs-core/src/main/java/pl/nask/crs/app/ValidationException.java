package pl.nask.crs.app;

/**
 * @author: Marcin Tkaczyk
 */
public abstract class ValidationException extends Exception {

	protected ValidationException() {}
	
	protected ValidationException(Throwable e) {
		super(e);
	}

    protected ValidationException(String s) {
        super(s);
    }

    @Override
    public abstract String getMessage();
}
