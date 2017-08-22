package pl.nask.crs.documents.exception;

/**
 * @author Piotr Tkaczyk
 */

public class NoSuchDirectoryException extends DocumentGeneralException {

    public NoSuchDirectoryException() {
    }

    public NoSuchDirectoryException(String s) {
        super(s);
    }

    public NoSuchDirectoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoSuchDirectoryException(Throwable throwable) {
        super(throwable);
    }
}
