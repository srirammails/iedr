package pl.nask.crs.documents.exception;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentFileMovingException extends DocumentGeneralException {

    public DocumentFileMovingException() {
    }

    public DocumentFileMovingException(String s) {
        super(s);
    }

    public DocumentFileMovingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DocumentFileMovingException(Throwable throwable) {
        super(throwable);
    }
}
