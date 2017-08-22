package pl.nask.crs.documents.exception;

/**
 * @author Piotr Tkaczyk
 */
public class NoSuchDocumentException extends DocumentGeneralException {

    public NoSuchDocumentException() {
    }

    public NoSuchDocumentException(String s) {
        super(s);
    }

    public NoSuchDocumentException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoSuchDocumentException(Throwable throwable) {
        super(throwable);
    }
}
